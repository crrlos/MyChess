package com.mychess.mychess;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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

    public String mensaje(){
        return "hola";
    }
}
