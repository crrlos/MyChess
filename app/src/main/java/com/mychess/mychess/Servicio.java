package com.mychess.mychess;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Servicio extends Service {

    private final IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        Servicio getService(){
            return Servicio.this;
        }
    }

    public Servicio() {
    }

    @Override
    public IBinder onBind(Intent intent) {

      return mBinder;
    }

   public void enviarMovimiento(final String movimiento){
       Toast.makeText(getApplicationContext(), "aqui voy", Toast.LENGTH_SHORT).show();

       new AsyncTask<Void,String,Boolean>(){

           @Override
           protected Boolean doInBackground(Void... params) {

               OutputStream toServer = null;
               try {
                   toServer = SocketServidor.getSocket().getOutputStream();
                   DataOutputStream out = new DataOutputStream(toServer);
                   publishProgress("e2e4");
                   out.writeUTF("e2e4");
                   return true;
               } catch (IOException e) {
                   publishProgress(e.getMessage());
                   return false;
               }




           }

           @Override
           protected void onProgressUpdate(String... values) {
               super.onProgressUpdate(values);
               Toast.makeText(getApplicationContext(),values[0], Toast.LENGTH_SHORT).show();
           }


       }.execute();
   }
}
