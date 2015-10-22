package com.mychess.mychess;

import java.io.IOException;
import java.net.Socket;


class SocketServidor {

    private static  Socket socket;


    public static void conectar(){

        try {
            socket = new Socket("servermychess.tk",40000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Socket getSocket() {
        return socket;
    }
}
