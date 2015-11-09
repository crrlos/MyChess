package com.mychess.mychess;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mychess.mychess.Chess.Chess;
import com.mychess.mychess.Chess.Ubicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.Inflater;


public class Juego extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Servicio mService;
    boolean mBound = false;

    ImageView casillas[][] = new ImageView[8][8];
    ImageView origen;
    ImageView destino;
    TextView tiempo;
    Tiempo tiempoMovimiento;

    TextView nombreColumnas[] = new TextView[8];
    TextView numeroFila[] = new TextView[8];

    int cOrigen;
    int cDestino;
    int fOrigen;
    int fDestino;

    boolean enroqueBlanco = true;
    boolean enroqueNegro = true;

    boolean jugadaLocal;// sirve para difenciar entre una jugada local y una remota
    boolean juegoIniciado;
    Chess chess;
    TextView nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializarCasillasBlanco();
        setOnclickListener();
        setDefaultColor();
        juegoIniciado = false;
        /**-------------------------**/
        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        nombreUsuario.setText(new Usuario(getApplicationContext()).getUsuario());
        /*--------------------------*/
        /********** inicializacion del tiemo *******/
        tiempo = (TextView) findViewById(R.id.textView18);
        tiempoMovimiento = new Tiempo();

        /* inicializcion del nuevo juego*/
        chess = new Chess();
        chess.newGame();
        /*---------------------*/

        new SocketServidor().conectar();
        new RecibirInvitacion().execute();
        RecibirMovimientos recibirMovimientos = new RecibirMovimientos();
        recibirMovimientos.execute();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!juegoIniciado)
                    Toast.makeText(Juego.this, "No ha iniciado un juego todavía", Toast.LENGTH_SHORT).show();
                else if (jugadaLocal) {
                    SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(Juego.this);
                    Speech speech = new Speech(Juego.this);
                    speechRecognizer.setRecognitionListener(speech);
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    speechRecognizer.startListening(intent);
                } else
                    Toast.makeText(Juego.this, "No es su turno", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, Servicio.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Servicio.LocalBinder binder = (Servicio.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.juego, menu);
        if (juegoIniciado)
            return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jugar) {
            // Handle the camera action
        } else if (id == R.id.amigos) {
            Intent intent = new Intent(Juego.this, Amigos.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Usuario(getApplicationContext()).clear();

                    Intent intent = new Intent(Juego.this, Acceso.class);
                    startActivity(intent);
                    finish();
                }
            };
            DialogInterface.OnClickListener listenerCanclar = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Mensaje");
            builder.setMessage("¿Está seguro que desea salir?");
            builder.setPositiveButton("Cerrar Sesión", listenerOk);
            builder.setNegativeButton("Cancelar", listenerCanclar);
            builder.create();
            builder.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inicializarCampos(int n) {
        nombreColumnas[0] = (TextView) findViewById(R.id.columnaa);
        nombreColumnas[1] = (TextView) findViewById(R.id.columnab);
        nombreColumnas[2] = (TextView) findViewById(R.id.columnac);
        nombreColumnas[3] = (TextView) findViewById(R.id.columnad);
        nombreColumnas[4] = (TextView) findViewById(R.id.columnae);
        nombreColumnas[5] = (TextView) findViewById(R.id.columnaf);
        nombreColumnas[6] = (TextView) findViewById(R.id.columnag);
        nombreColumnas[7] = (TextView) findViewById(R.id.columnah);

        numeroFila[0] = (TextView) findViewById(R.id.fila1);
        numeroFila[1] = (TextView) findViewById(R.id.fila2);
        numeroFila[2] = (TextView) findViewById(R.id.fila3);
        numeroFila[3] = (TextView) findViewById(R.id.fila4);
        numeroFila[4] = (TextView) findViewById(R.id.fila5);
        numeroFila[5] = (TextView) findViewById(R.id.fila6);
        numeroFila[6] = (TextView) findViewById(R.id.fila7);
        numeroFila[7] = (TextView) findViewById(R.id.fila8);

        nombreColumnas[0].setText("a");
        nombreColumnas[1].setText("b");
        nombreColumnas[2].setText("c");
        nombreColumnas[3].setText("d");
        nombreColumnas[4].setText("e");
        nombreColumnas[5].setText("f");
        nombreColumnas[6].setText("g");
        nombreColumnas[7].setText("h");

        numeroFila[0].setText("1");
        numeroFila[1].setText("2");
        numeroFila[2].setText("3");
        numeroFila[3].setText("4");
        numeroFila[4].setText("5");
        numeroFila[5].setText("6");
        numeroFila[6].setText("7");
        numeroFila[7].setText("8");

        if (n == 2) {
            nombreColumnas[0].setText("h");
            nombreColumnas[1].setText("g");
            nombreColumnas[2].setText("f");
            nombreColumnas[3].setText("e");
            nombreColumnas[4].setText("d");
            nombreColumnas[5].setText("c");
            nombreColumnas[6].setText("b");
            nombreColumnas[7].setText("a");

            numeroFila[0].setText("8");
            numeroFila[1].setText("7");
            numeroFila[2].setText("6");
            numeroFila[3].setText("5");
            numeroFila[4].setText("4");
            numeroFila[5].setText("3");
            numeroFila[6].setText("2");
            numeroFila[7].setText("1");
        }


    }

    private void inicializarCasillasBlanco() {

        casillas[0][0] = (ImageView) findViewById(R.id.a8);
        casillas[0][1] = (ImageView) findViewById(R.id.a7);
        casillas[0][2] = (ImageView) findViewById(R.id.a6);
        casillas[0][3] = (ImageView) findViewById(R.id.a5);
        casillas[0][4] = (ImageView) findViewById(R.id.a4);
        casillas[0][5] = (ImageView) findViewById(R.id.a3);
        casillas[0][6] = (ImageView) findViewById(R.id.a2);
        casillas[0][7] = (ImageView) findViewById(R.id.a1);


        casillas[1][0] = (ImageView) findViewById(R.id.b8);
        casillas[1][1] = (ImageView) findViewById(R.id.b7);
        casillas[1][2] = (ImageView) findViewById(R.id.b6);
        casillas[1][3] = (ImageView) findViewById(R.id.b5);
        casillas[1][4] = (ImageView) findViewById(R.id.b4);
        casillas[1][5] = (ImageView) findViewById(R.id.b3);
        casillas[1][6] = (ImageView) findViewById(R.id.b2);
        casillas[1][7] = (ImageView) findViewById(R.id.b1);

        casillas[2][0] = (ImageView) findViewById(R.id.c8);
        casillas[2][1] = (ImageView) findViewById(R.id.c7);
        casillas[2][2] = (ImageView) findViewById(R.id.c6);
        casillas[2][3] = (ImageView) findViewById(R.id.c5);
        casillas[2][4] = (ImageView) findViewById(R.id.c4);
        casillas[2][5] = (ImageView) findViewById(R.id.c3);
        casillas[2][6] = (ImageView) findViewById(R.id.c2);
        casillas[2][7] = (ImageView) findViewById(R.id.c1);

        casillas[3][0] = (ImageView) findViewById(R.id.d8);
        casillas[3][1] = (ImageView) findViewById(R.id.d7);
        casillas[3][2] = (ImageView) findViewById(R.id.d6);
        casillas[3][3] = (ImageView) findViewById(R.id.d5);
        casillas[3][4] = (ImageView) findViewById(R.id.d4);
        casillas[3][5] = (ImageView) findViewById(R.id.d3);
        casillas[3][6] = (ImageView) findViewById(R.id.d2);
        casillas[3][7] = (ImageView) findViewById(R.id.d1);

        casillas[4][0] = (ImageView) findViewById(R.id.e8);
        casillas[4][1] = (ImageView) findViewById(R.id.e7);
        casillas[4][2] = (ImageView) findViewById(R.id.e6);
        casillas[4][3] = (ImageView) findViewById(R.id.e5);
        casillas[4][4] = (ImageView) findViewById(R.id.e4);
        casillas[4][5] = (ImageView) findViewById(R.id.e3);
        casillas[4][6] = (ImageView) findViewById(R.id.e2);
        casillas[4][7] = (ImageView) findViewById(R.id.e1);

        casillas[5][0] = (ImageView) findViewById(R.id.f8);
        casillas[5][1] = (ImageView) findViewById(R.id.f7);
        casillas[5][2] = (ImageView) findViewById(R.id.f6);
        casillas[5][3] = (ImageView) findViewById(R.id.f5);
        casillas[5][4] = (ImageView) findViewById(R.id.f4);
        casillas[5][5] = (ImageView) findViewById(R.id.f3);
        casillas[5][6] = (ImageView) findViewById(R.id.f2);
        casillas[5][7] = (ImageView) findViewById(R.id.f1);

        casillas[6][0] = (ImageView) findViewById(R.id.g8);
        casillas[6][1] = (ImageView) findViewById(R.id.g7);
        casillas[6][2] = (ImageView) findViewById(R.id.g6);
        casillas[6][3] = (ImageView) findViewById(R.id.g5);
        casillas[6][4] = (ImageView) findViewById(R.id.g4);
        casillas[6][5] = (ImageView) findViewById(R.id.g3);
        casillas[6][6] = (ImageView) findViewById(R.id.g2);
        casillas[6][7] = (ImageView) findViewById(R.id.g1);

        casillas[7][0] = (ImageView) findViewById(R.id.h8);
        casillas[7][1] = (ImageView) findViewById(R.id.h7);
        casillas[7][2] = (ImageView) findViewById(R.id.h6);
        casillas[7][3] = (ImageView) findViewById(R.id.h5);
        casillas[7][4] = (ImageView) findViewById(R.id.h4);
        casillas[7][5] = (ImageView) findViewById(R.id.h3);
        casillas[7][6] = (ImageView) findViewById(R.id.h2);
        casillas[7][7] = (ImageView) findViewById(R.id.h1);
        colocarPiezas(1);
        inicializarCampos(1);
        jugadaLocal = true;
    }

    private void inicializarCasillasNegro() {

        casillas[0][0] = (ImageView) findViewById(R.id.h1);
        casillas[0][1] = (ImageView) findViewById(R.id.h2);
        casillas[0][2] = (ImageView) findViewById(R.id.h3);
        casillas[0][3] = (ImageView) findViewById(R.id.h4);
        casillas[0][4] = (ImageView) findViewById(R.id.h5);
        casillas[0][5] = (ImageView) findViewById(R.id.h6);
        casillas[0][6] = (ImageView) findViewById(R.id.h7);
        casillas[0][7] = (ImageView) findViewById(R.id.h8);


        casillas[1][0] = (ImageView) findViewById(R.id.g1);
        casillas[1][1] = (ImageView) findViewById(R.id.g2);
        casillas[1][2] = (ImageView) findViewById(R.id.g3);
        casillas[1][3] = (ImageView) findViewById(R.id.g4);
        casillas[1][4] = (ImageView) findViewById(R.id.g5);
        casillas[1][5] = (ImageView) findViewById(R.id.g6);
        casillas[1][6] = (ImageView) findViewById(R.id.g7);
        casillas[1][7] = (ImageView) findViewById(R.id.g8);

        casillas[2][0] = (ImageView) findViewById(R.id.f1);
        casillas[2][1] = (ImageView) findViewById(R.id.f2);
        casillas[2][2] = (ImageView) findViewById(R.id.f3);
        casillas[2][3] = (ImageView) findViewById(R.id.f4);
        casillas[2][4] = (ImageView) findViewById(R.id.f5);
        casillas[2][5] = (ImageView) findViewById(R.id.f6);
        casillas[2][6] = (ImageView) findViewById(R.id.f7);
        casillas[2][7] = (ImageView) findViewById(R.id.f8);

        casillas[3][0] = (ImageView) findViewById(R.id.e1);
        casillas[3][1] = (ImageView) findViewById(R.id.e2);
        casillas[3][2] = (ImageView) findViewById(R.id.e3);
        casillas[3][3] = (ImageView) findViewById(R.id.e4);
        casillas[3][4] = (ImageView) findViewById(R.id.e5);
        casillas[3][5] = (ImageView) findViewById(R.id.e6);
        casillas[3][6] = (ImageView) findViewById(R.id.e7);
        casillas[3][7] = (ImageView) findViewById(R.id.e8);

        casillas[4][0] = (ImageView) findViewById(R.id.d1);
        casillas[4][1] = (ImageView) findViewById(R.id.d2);
        casillas[4][2] = (ImageView) findViewById(R.id.d3);
        casillas[4][3] = (ImageView) findViewById(R.id.d4);
        casillas[4][4] = (ImageView) findViewById(R.id.d5);
        casillas[4][5] = (ImageView) findViewById(R.id.d6);
        casillas[4][6] = (ImageView) findViewById(R.id.d7);
        casillas[4][7] = (ImageView) findViewById(R.id.d8);

        casillas[5][0] = (ImageView) findViewById(R.id.c1);
        casillas[5][1] = (ImageView) findViewById(R.id.c2);
        casillas[5][2] = (ImageView) findViewById(R.id.c3);
        casillas[5][3] = (ImageView) findViewById(R.id.c4);
        casillas[5][4] = (ImageView) findViewById(R.id.c5);
        casillas[5][5] = (ImageView) findViewById(R.id.c6);
        casillas[5][6] = (ImageView) findViewById(R.id.c7);
        casillas[5][7] = (ImageView) findViewById(R.id.c8);

        casillas[6][0] = (ImageView) findViewById(R.id.b1);
        casillas[6][1] = (ImageView) findViewById(R.id.b2);
        casillas[6][2] = (ImageView) findViewById(R.id.b3);
        casillas[6][3] = (ImageView) findViewById(R.id.b4);
        casillas[6][4] = (ImageView) findViewById(R.id.b5);
        casillas[6][5] = (ImageView) findViewById(R.id.b6);
        casillas[6][6] = (ImageView) findViewById(R.id.b7);
        casillas[6][7] = (ImageView) findViewById(R.id.b8);

        casillas[7][0] = (ImageView) findViewById(R.id.a1);
        casillas[7][1] = (ImageView) findViewById(R.id.a2);
        casillas[7][2] = (ImageView) findViewById(R.id.a3);
        casillas[7][3] = (ImageView) findViewById(R.id.a4);
        casillas[7][4] = (ImageView) findViewById(R.id.a5);
        casillas[7][5] = (ImageView) findViewById(R.id.a6);
        casillas[7][6] = (ImageView) findViewById(R.id.a7);
        casillas[7][7] = (ImageView) findViewById(R.id.a8);
        colocarPiezas(1);
        inicializarCampos(2);
        jugadaLocal = false;
    }


    private void setDefaultColor() {
        boolean dark = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (dark)
                    casillas[i][j].setBackgroundResource(R.color.casillablanca);
                else
                    casillas[i][j].setBackgroundResource(R.color.casillnegra);
                dark = !dark;
            }
            dark = !dark;
        }
    }

    private void colocarPiezas(int bando) {


        casillas[0][7].setImageResource(bando == 1 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);
        casillas[7][7].setImageResource(bando == 1 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);

        casillas[0][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[1][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[2][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[3][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[4][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[5][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[6][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[7][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);

        casillas[1][7].setImageResource(bando == 1 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);
        casillas[6][7].setImageResource(bando == 1 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);

        casillas[2][7].setImageResource(bando == 1 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);
        casillas[5][7].setImageResource(bando == 1 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);

        casillas[3][7].setImageResource(bando == 1 ? R.mipmap.alpha_wq : R.mipmap.alpha_bq);
        casillas[4][7].setImageResource(bando == 1 ? R.mipmap.alpha_wk : R.mipmap.alpha_bk);


        casillas[7][0].setImageResource(bando == 2 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);
        casillas[0][0].setImageResource(bando == 2 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);

        casillas[0][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[1][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[2][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[3][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[4][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[5][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[6][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[7][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);

        casillas[1][0].setImageResource(bando == 2 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);
        casillas[6][0].setImageResource(bando == 2 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);

        casillas[2][0].setImageResource(bando == 2 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);
        casillas[5][0].setImageResource(bando == 2 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);

        casillas[3][0].setImageResource(bando == 2 ? R.mipmap.alpha_wq : R.mipmap.alpha_bq);
        casillas[4][0].setImageResource(bando == 2 ? R.mipmap.alpha_wk : R.mipmap.alpha_bk);


    }

    private void setOnclickListener() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                casillas[i][j].setOnClickListener(this);

            }

        }
    }

    private int[] getPosition(int id) {

        int position[] = {-1, -1};//inicialización del arreglo con valores negativos para indicar que el click no se realizó en una casilla

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (id == casillas[i][j].getId()) {
                    position[0] = i;
                    position[1] = j;
                    return position;//si el click fue en una casilla se retorna la posicion
                }
            }

        }
        return position;//si el click no fue en una casilla se devuelven valores negativos en la posicion
    }

    private boolean validarCoordenadas(String coordenadas) {
        final String columnas = "abcdefgh";
        final String filas = "87654321";

        cOrigen = columnas.indexOf(coordenadas.charAt(0));
        cDestino = columnas.indexOf(coordenadas.charAt(2));
        fOrigen = filas.indexOf(coordenadas.charAt(1));
        fDestino = filas.indexOf(coordenadas.charAt(3));

        if (cOrigen == -1 || cDestino == -1 || fOrigen == -1 || fDestino == -1)
            return false;

        return true;


    }

    public void procesarResultados(ArrayList<String> listaCoordenadas) {
        String coordenadas;
        for (int i = 0; i < listaCoordenadas.size(); ++i) {
            coordenadas = listaCoordenadas.get(i).replace(" ", "").toLowerCase();
            if (coordenadas.length() == 4) {
                if (validarCoordenadas(coordenadas)) {

                    validarMovimiento(coordenadas);
                    break;

                }

            }

        }

    }

    private void enviarMovimiento(String coordendas) {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
            out.writeUTF(coordendas);
            tiempoMovimiento.reiniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void validarMovimiento(String coordenadas) {
        switch (chess.mover(coordenadas.substring(0, 2), coordenadas.substring(2, 4))) {

            case 2:
                Toast.makeText(Juego.this, "movimiento no valido", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                moverPieza(1, coordenadas);
                break;
            case 4:
                Toast.makeText(Juego.this, "movimiento no valido", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                moverPieza(2, coordenadas);
                break;

        }
    }

    private void moverPieza(int n, String coordenada) {
        if (jugadaLocal) {
            enviarMovimiento(crearCoordenada());
            if (!enroque()) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
            }

        } else {
            validarCoordenadas(coordenada);
            if (!enroque()) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
            }

        }

        jugadaLocal = !jugadaLocal;


        if (n == 1) {
            Toast.makeText(Juego.this, "Jaque Mate", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean enroque() {

        if (enroqueBlanco)
            if (cOrigen == 4 && fOrigen == 7 && cDestino == 6 && fDestino == 7) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
                casillas[5][7].setImageDrawable(casillas[7][7].getDrawable());
                casillas[7][7].setImageDrawable(null);
                enroqueBlanco = !enroqueBlanco;
                return true;
            } else if (cOrigen == 4 && fOrigen == 7 && cDestino == 2 && fDestino == 7) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
                casillas[3][7].setImageDrawable(casillas[0][7].getDrawable());
                casillas[0][7].setImageDrawable(null);
                enroqueBlanco = !enroqueBlanco;
                return true;

            }
        if (enroqueNegro)
            if (cOrigen == 4 && fOrigen == 0 && cDestino == 6 && fDestino == 0) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
                casillas[5][0].setImageDrawable(casillas[7][0].getDrawable());
                casillas[7][0].setImageDrawable(null);
                enroqueNegro = !enroqueNegro;
                return true;
            } else if (cOrigen == 4 && fOrigen == 0 && cDestino == 2 && fDestino == 0) {
                casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
                casillas[cOrigen][fOrigen].setImageDrawable(null);
                casillas[3][0].setImageDrawable(casillas[0][0].getDrawable());
                casillas[0][0].setImageDrawable(null);
                enroqueNegro = !enroqueNegro;
                return true;

            }
        return false;
    }

    private String crearCoordenada() {
        String coordenada = "";
        final String columnas = "abcdefgh";
        final String filas = "87654321";
        coordenada += columnas.charAt(cOrigen);
        coordenada += filas.charAt(fOrigen);
        coordenada += columnas.charAt(cDestino);
        coordenada += filas.charAt(fDestino);


        return coordenada;
    }

    @Override
    public void onClick(View v) {
        if (!juegoIniciado)
            Toast.makeText(Juego.this, "No ha iniciado un juego todavía", Toast.LENGTH_SHORT).show();
        else if (true) {
            int position[] = getPosition(v.getId());
            if (position[0] != -1) {//si el valor es negativo indica que el click no se  realizo en una casilla
                if (origen == null) {
                    origen = casillas[position[0]][position[1]];
                    cOrigen = position[0];
                    fOrigen = position[1];
                    casillas[cOrigen][fOrigen].setBackgroundResource(R.color.origen);
                    for (Ubicacion u : chess.mostrarMovimientos(fOrigen, cOrigen)) {
                        casillas[u.getCol()][u.getFila()].setBackgroundResource(R.drawable.seleccion);
                    }
                } else {
                    cDestino = position[0];
                    fDestino = position[1];
                    destino = casillas[cDestino][fDestino];
                    if (!(destino == origen)) {
                        validarMovimiento(crearCoordenada());
                        setDefaultColor();
                    } else
                        setDefaultColor();
                    origen = null;
                }
            }
        } else {
            Toast.makeText(Juego.this, "No es su turno", Toast.LENGTH_SHORT).show();
        }


    }



    class Tiempo {
        CountDown countDown;

        public void iniciar() {
            countDown = new CountDown(60000, 1000);
            countDown.start();
        }

        public void detener() {
            countDown.cancel();

        }

        public void reiniciar() {
            tiempo.setText("60");
            tiempo.setTextColor(Color.WHITE);
            countDown.cancel();
        }


        class CountDown extends CountDownTimer {

            public CountDown(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }


            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                tiempo.setText(String.valueOf(time));
                if (time == 15)
                    tiempo.setTextColor(Color.RED);

            }

            @Override
            public void onFinish() {

            }
        }


    }

    class RecibirMovimientos extends AsyncTask<Void, String, Boolean> {
        Socket socket;

        protected Boolean doInBackground(Void... params) {
            socket = SocketServidor.getSocket();
            boolean continuar = true;
            while (continuar) {
                try {
                    Thread.sleep(250);
                    InputStream fromServer = SocketServidor.getSocket().getInputStream();
                    DataInputStream in = new DataInputStream(fromServer);
                    publishProgress(in.readUTF());
                } catch (Exception ex) {
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            validarMovimiento(values[0]);
            Toast.makeText(Juego.this, values[0], Toast.LENGTH_SHORT).show();
            tiempoMovimiento.iniciar();

        }
    }

    class RecibirInvitacion extends AsyncTask<Void, String, Void> {
        DataInputStream in;


        @Override
        protected Void doInBackground(Void... params) {

            boolean continuar = true;
            while (continuar) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                    publishProgress(in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            };
            DialogInterface.OnClickListener listenerCanclar = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(Juego.this);
            /*builder.setTitle("Alerta");
            builder.setMessage(values[0]+" Te invita a jugar");*/
            View v = getLayoutInflater().inflate(R.layout.invitacion,null);
            TextView retador = (TextView) v.findViewById(R.id.retador);
            retador.setText(values[0]);
            builder.setView(v);
            builder.setPositiveButton("Aceptar", listenerOk);
            builder.setNegativeButton("Rechazar", listenerCanclar);
            builder.create();
            builder.show();

        }

    }

}
