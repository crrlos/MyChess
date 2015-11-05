package com.mychess.mychess;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


class SocketServidor {

    private static Socket socket;

    SocketServidor() {
        socket = new Socket();
    }


    public void conectar() {

        Conectar conectar = new Conectar();
        conectar.execute();

    }

    public static Socket getSocket() {
        return socket;
    }

    class Conectar extends AsyncTask<Void, Integer, Boolean> {
        DataOutputStream out;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                socket = new Socket("servermychess.tk", 40000);
                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(Usuario.getUsuario());

            } catch (Exception ex) {

            }
            return null;
        }
    }
}
