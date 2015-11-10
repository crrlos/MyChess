package com.mychess.mychess;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Amigos extends AppCompatActivity {
    Button invitar;
    EditText amigo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        invitar = (Button) findViewById(R.id.btnInvitar);
        amigo = (EditText) findViewById(R.id.txtAmigo);

        invitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invitarAmigo()) {
                    EsperarRespuesta respuesta = new EsperarRespuesta();
                    respuesta.execute();

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

    class EsperarRespuesta extends AsyncTask<Void,Integer,Void>{
            boolean continuar = true;

        @Override
        protected Void doInBackground(Void... params) {
            while(continuar){
                try {
                    DataInputStream in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                    publishProgress(in.readInt());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == 1){
                progressDialog.cancel();
                Toast.makeText(Amigos.this, "invitación aceptada", Toast.LENGTH_SHORT).show();
            }else
            {
                progressDialog.cancel();
                Toast.makeText(Amigos.this, "invitación rechazada", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Amigos.this);
            progressDialog.setMessage("Por favor espere...");
            progressDialog.setTitle("Esperando respuesta");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

}
