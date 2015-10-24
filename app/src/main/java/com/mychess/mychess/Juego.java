package com.mychess.mychess;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Juego extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageView casillas[][] = new ImageView[8][8];
    ImageView origen;
    ImageView destino;
    TextView tiempo;

    Tiempo tiempoMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializarCasillas();
        setOnclickListener();
        setDefaultColor();

        tiempo = (TextView) findViewById(R.id.textView18);
        tiempoMovimiento = new Tiempo();
        tiempoMovimiento.iniciar();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(Juego.this);
                Speech speech = new Speech();
                speechRecognizer.setRecognitionListener(speech);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechRecognizer.startListening(intent);
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
        return true;
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inicializarCasillas() {

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

    }


    private void setDefaultColor() {
        boolean dark = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (dark)
                    casillas[i][j].setBackgroundColor(Color.parseColor("#7986CB"));
                else
                    casillas[i][j].setBackgroundColor(Color.parseColor("#C5CAE9"));
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
        try {
            int cOrigen = columnas.indexOf(coordenadas.charAt(0));
            int cDestino = columnas.indexOf(coordenadas.charAt(2));
            int fOrigen = filas.indexOf(coordenadas.charAt(1));
            int fDestino = filas.indexOf(coordenadas.charAt(3));
           /*prueba de que las coordenadas están bien*/
            casillas[cDestino][fDestino].setImageDrawable(casillas[cOrigen][fOrigen].getDrawable());
            casillas[cOrigen][fOrigen].setImageDrawable(null);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    private void procesarResultados(ArrayList<String> listaCoordenadas) {
        String coordenadas;
        for (int i = 0; i < listaCoordenadas.size(); ++i) {
            coordenadas = listaCoordenadas.get(i).replace(" ", "").toLowerCase();
            if (coordenadas.length() == 4) {
                if (validarCoordenadas(coordenadas)) {

                    break;
                }

            }

        }

    }

    @Override
    public void onClick(View v) {
        int position[] = getPosition(v.getId());
        if (position[0] != -1) {//si el valor es negativo indica que el click no se  realizo en una casilla
            if (origen == null) {
                origen = casillas[position[0]][position[1]];
            } else {
                if (casillas[position[0]][position[1]] != origen) {
                    casillas[position[0]][position[1]].setImageDrawable(origen.getDrawable());
                    origen.setImageDrawable(null);
                    tiempoMovimiento.reiniciar();
                    origen = null;
                }
            }
        }


    }


    class Speech implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> listaPalabras = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            procesarResultados(listaPalabras);


        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

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
    class RecibirMovimientos extends AsyncTask<Void,String,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
    }
}
