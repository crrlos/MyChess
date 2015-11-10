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
        Tablero tab = new Tablero(casillas,nombreColumnas,numeroFila,this);
        jugadaLocal =  tab.inicializarCasillasBlanco();
        setOnclickListener();
        setDefaultColor();
        juegoIniciado = true;
        /**-------------------------**/
        nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        nombreUsuario.setText(new Usuario(getApplicationContext()).getUsuario());
        /*--------------------------*/
        /********** inicializacion del tiempo *******/
        tiempo = (TextView) findViewById(R.id.textView18);
        tiempoMovimiento = new Tiempo();

        /* inicializcion del nuevo juego*/
        chess = new Chess();
        chess.newGame();
        /*---------------------*/

        new SocketServidor().conectar();
        new RecibirInvitacion().execute();




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
            out.writeInt(3);
            out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
            out.writeUTF(coordendas);
            
            //tiempoMovimiento.reiniciar();
        } catch (IOException e) {
            Toast.makeText(Juego.this, "no hay conexion", Toast.LENGTH_SHORT).show();
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


        protected Boolean doInBackground(Void... params) {

            boolean continuar = true;
            while (continuar) {
                try {
                    Thread.sleep(250);

                    DataInputStream in = new DataInputStream(SocketServidor.getSocket().getInputStream());
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
           // tiempoMovimiento.iniciar();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Juego.this, "recibir iniciado", Toast.LENGTH_SHORT).show();
        }
    }

    class RecibirInvitacion extends AsyncTask<Void, String, Void> {
        DataInputStream in;
        boolean continuar = true;

        @Override
        protected Void doInBackground(Void... params) {


            while (continuar) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                    publishProgress(in.readUTF());
                    continuar = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final String... values) {
            super.onProgressUpdate(values);


            DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    enviarRespuesta(1,values[0]);
                    new RecibirMovimientos().execute();
                }
            };
            DialogInterface.OnClickListener listenerCanclar = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    enviarRespuesta(0,values[0]);
                   new RecibirInvitacion().execute();
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
        private void enviarRespuesta(int respuesta,String jugador){
            try {
                DataOutputStream out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
                out.writeInt(2);
                out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
                out.writeInt(respuesta);
                out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
                out.writeUTF(jugador);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
