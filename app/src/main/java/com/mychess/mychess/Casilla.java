package com.mychess.mychess;

import android.content.Context;
import android.widget.ImageView;


public class Casilla extends ImageView {

    private short bando;

    public Casilla(Context context) {
        super(context);
    }

    public short getBando() {
        return bando;
    }

    public void setBando(short bando) {
        this.bando = bando;
    }
}
