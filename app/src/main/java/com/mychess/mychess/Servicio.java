package com.mychess.mychess;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;

import java.io.DataOutputStream;
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

       new AsyncTask<Void,String,Integer>(){

           @Override
           protected Integer doInBackground(Void... params) {
               try{
                   OutputStream toServer = SocketServidor.getSocket().getOutputStream();
                   DataOutputStream out = new DataOutputStream(toServer);
                   out.writeUTF(movimiento);
               }catch (Exception ex){

               }
               return null;
           }
       }.execute();
   }
}
