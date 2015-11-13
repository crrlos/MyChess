package com.mychess.mychess;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Amigos extends AppCompatActivity {
    Button invitar;
    EditText amigo;
    ProgressDialog progressDialog;
    Handler handler;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        invitar = (Button) findViewById(R.id.btnInvitar);
        amigo = (EditText) findViewById(R.id.txtAmigo);
        spinner = (Spinner) findViewById(R.id.spinner);
        final String[] bandos = {"Blancas","Negras"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Amigos.this,android.R.layout.simple_list_item_1,bandos);
        spinner.setAdapter(adapter);

        handler = new Handler();

        invitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invitarAmigo()) {
                    iniciarDialogo();
                    EsperarRespuesta respuesta = new EsperarRespuesta();
                    respuesta.start();


                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_amigos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.agregar: {
                Intent intent = new Intent(Amigos.this, AgregarAmigos.class);
                startActivity(intent);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean invitarAmigo() {
        try {
            DataOutputStream out = new DataOutputStream(SocketServidor.getSocket().getOutputStream());
            out.writeInt(1);
            out.writeUTF(amigo.getText().toString());
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    class EsperarRespuesta  extends Thread {
        boolean continuar = true;

        @Override
        public void run() {
            super.run();
            while (continuar) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (ThreadsData.ESPERAR_RESPUESTA) {
                    ThreadsData.ESPERAR_RESPUESTA = false;
                    handler.post(run);

                }
            }
        }

        Runnable run = new Runnable() {
            @Override
            public void run() {
                if (ThreadsData.RESPUESTA == 1) {
                    progressDialog.cancel();
                    Intent intent = new Intent(Amigos.this, Juego.class);
                    intent.putExtra("juegoIniciado", true);
                    startActivity(intent);
                } else {
                    progressDialog.cancel();
                    Toast.makeText(Amigos.this, "invitación rechazada", Toast.LENGTH_SHORT).show();
                }
            }
        };


    }

    private void iniciarDialogo(){
        progressDialog = new ProgressDialog(Amigos.this);
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setTitle("Esperando respuesta");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

}
