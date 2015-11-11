package com.mychess.mychess;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Carlos on 10/11/2015.
 */
public class MainThread extends Thread{
    Context context;
    boolean continuar = true;
    DataInputStream in;


    @Override
    public void run() {
        super.run();
        while(continuar){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                int op = in.readInt();
                switch (op)
                {
                    case 1:{
                        ThreadsData.RECIBIR_INVITACION = true;
                        in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                        ThreadsData.INVITACION_USUARIO = in.readUTF();

                    }
                    break;
                    case 2:{
                        ThreadsData.ESPERAR_RESPUESTA  = true;
                        in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                        ThreadsData.RESPUESTA = in.readInt();

                    }
                    break;
                    case 3:{
                        ThreadsData.RECIBIR_MOVIMIENTO = true;
                        in = new DataInputStream(SocketServidor.getSocket().getInputStream());
                        ThreadsData.MOVIMIENTO = in.readUTF();
                    }
                    break;


                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



}
