package com.mychess.mychess;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Carlos on 05/11/2015.
 */
public class RecibirInvitacion extends AsyncTask<Void,String,Void> {
    DataInputStream in;
    Context context;

    RecibirInvitacion(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... params) {

        boolean continuar = true;
        while(continuar){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                in = new DataInputStream(SocketServidor.getSocket().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, "Usted ha sido retado por "+values[0], Toast.LENGTH_SHORT).show();

    }
}
