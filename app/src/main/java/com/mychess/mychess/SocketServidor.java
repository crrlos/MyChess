package com.mychess.mychess;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;


class SocketServidor {

    private static  Socket socket;


    public  void conectar(){

       Conectar conectar  = new Conectar();
        conectar.execute();

    }

    public  Socket getSocket() {
        return socket;
    }

    class Conectar extends AsyncTask<Void,Integer,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                socket = new Socket("servermychess.tk",40000);

            }catch(Exception ex){

            }
            return null;
        }
    }
}
