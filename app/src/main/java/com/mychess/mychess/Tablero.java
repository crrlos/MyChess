package com.mychess.mychess;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Carlos on 09/11/2015.
 */
public class Tablero {
    Juego j;
    ImageView casillas[][];
    TextView nombreColumnas[];
    TextView numeroFila[];


    public Tablero(ImageView[][] casillas,TextView nombreColumnas[],TextView numeroFila[],Juego j) {
        this.casillas = casillas;
        this.nombreColumnas =  nombreColumnas;
        this.numeroFila = numeroFila;
        this.j = j;

    }

    private boolean inicializarCasillasBlanco() {

        casillas[0][0] = (ImageView) j.findViewById(R.id.a8);
        casillas[0][1] = (ImageView) j.findViewById(R.id.a7);
        casillas[0][2] = (ImageView) j.findViewById(R.id.a6);
        casillas[0][3] = (ImageView) j.findViewById(R.id.a5);
        casillas[0][4] = (ImageView) j.findViewById(R.id.a4);
        casillas[0][5] = (ImageView) j.findViewById(R.id.a3);
        casillas[0][6] = (ImageView) j.findViewById(R.id.a2);
        casillas[0][7] = (ImageView) j.findViewById(R.id.a1);


        casillas[1][0] = (ImageView) j.findViewById(R.id.b8);
        casillas[1][1] = (ImageView) j.findViewById(R.id.b7);
        casillas[1][2] = (ImageView) j.findViewById(R.id.b6);
        casillas[1][3] = (ImageView) j.findViewById(R.id.b5);
        casillas[1][4] = (ImageView) j.findViewById(R.id.b4);
        casillas[1][5] = (ImageView) j.findViewById(R.id.b3);
        casillas[1][6] = (ImageView) j.findViewById(R.id.b2);
        casillas[1][7] = (ImageView) j.findViewById(R.id.b1);

        casillas[2][0] = (ImageView) j.findViewById(R.id.c8);
        casillas[2][1] = (ImageView) j.findViewById(R.id.c7);
        casillas[2][2] = (ImageView) j.findViewById(R.id.c6);
        casillas[2][3] = (ImageView) j.findViewById(R.id.c5);
        casillas[2][4] = (ImageView) j.findViewById(R.id.c4);
        casillas[2][5] = (ImageView) j.findViewById(R.id.c3);
        casillas[2][6] = (ImageView) j.findViewById(R.id.c2);
        casillas[2][7] = (ImageView) j.findViewById(R.id.c1);

        casillas[3][0] = (ImageView) j.findViewById(R.id.d8);
        casillas[3][1] = (ImageView) j.findViewById(R.id.d7);
        casillas[3][2] = (ImageView) j.findViewById(R.id.d6);
        casillas[3][3] = (ImageView) j.findViewById(R.id.d5);
        casillas[3][4] = (ImageView) j.findViewById(R.id.d4);
        casillas[3][5] = (ImageView) j.findViewById(R.id.d3);
        casillas[3][6] = (ImageView) j.findViewById(R.id.d2);
        casillas[3][7] = (ImageView) j.findViewById(R.id.d1);

        casillas[4][0] = (ImageView) j.findViewById(R.id.e8);
        casillas[4][1] = (ImageView) j.findViewById(R.id.e7);
        casillas[4][2] = (ImageView) j.findViewById(R.id.e6);
        casillas[4][3] = (ImageView) j.findViewById(R.id.e5);
        casillas[4][4] = (ImageView) j.findViewById(R.id.e4);
        casillas[4][5] = (ImageView) j.findViewById(R.id.e3);
        casillas[4][6] = (ImageView) j.findViewById(R.id.e2);
        casillas[4][7] = (ImageView) j.findViewById(R.id.e1);

        casillas[5][0] = (ImageView) j.findViewById(R.id.f8);
        casillas[5][1] = (ImageView) j.findViewById(R.id.f7);
        casillas[5][2] = (ImageView) j.findViewById(R.id.f6);
        casillas[5][3] = (ImageView) j.findViewById(R.id.f5);
        casillas[5][4] = (ImageView) j.findViewById(R.id.f4);
        casillas[5][5] = (ImageView) j.findViewById(R.id.f3);
        casillas[5][6] = (ImageView) j.findViewById(R.id.f2);
        casillas[5][7] = (ImageView) j.findViewById(R.id.f1);

        casillas[6][0] = (ImageView) j.findViewById(R.id.g8);
        casillas[6][1] = (ImageView) j.findViewById(R.id.g7);
        casillas[6][2] = (ImageView) j.findViewById(R.id.g6);
        casillas[6][3] = (ImageView) j.findViewById(R.id.g5);
        casillas[6][4] = (ImageView) j.findViewById(R.id.g4);
        casillas[6][5] = (ImageView) j.findViewById(R.id.g3);
        casillas[6][6] = (ImageView) j.findViewById(R.id.g2);
        casillas[6][7] = (ImageView) j.findViewById(R.id.g1);

        casillas[7][0] = (ImageView) j.findViewById(R.id.h8);
        casillas[7][1] = (ImageView) j.findViewById(R.id.h7);
        casillas[7][2] = (ImageView) j.findViewById(R.id.h6);
        casillas[7][3] = (ImageView) j.findViewById(R.id.h5);
        casillas[7][4] = (ImageView) j.findViewById(R.id.h4);
        casillas[7][5] = (ImageView) j.findViewById(R.id.h3);
        casillas[7][6] = (ImageView) j.findViewById(R.id.h2);
        casillas[7][7] = (ImageView) j.findViewById(R.id.h1);
        colocarPiezas(1);
        inicializarCampos(1);
        return true;
    }

    public boolean inicializarCasillasNegro() {

        casillas[0][0] = (ImageView) j.findViewById(R.id.h1);
        casillas[0][1] = (ImageView) j.findViewById(R.id.h2);
        casillas[0][2] = (ImageView) j.findViewById(R.id.h3);
        casillas[0][3] = (ImageView) j.findViewById(R.id.h4);
        casillas[0][4] = (ImageView) j.findViewById(R.id.h5);
        casillas[0][5] = (ImageView) j.findViewById(R.id.h6);
        casillas[0][6] = (ImageView) j.findViewById(R.id.h7);
        casillas[0][7] = (ImageView) j.findViewById(R.id.h8);


        casillas[1][0] = (ImageView) j.findViewById(R.id.g1);
        casillas[1][1] = (ImageView) j.findViewById(R.id.g2);
        casillas[1][2] = (ImageView) j.findViewById(R.id.g3);
        casillas[1][3] = (ImageView) j.findViewById(R.id.g4);
        casillas[1][4] = (ImageView) j.findViewById(R.id.g5);
        casillas[1][5] = (ImageView) j.findViewById(R.id.g6);
        casillas[1][6] = (ImageView) j.findViewById(R.id.g7);
        casillas[1][7] = (ImageView) j.findViewById(R.id.g8);

        casillas[2][0] = (ImageView) j.findViewById(R.id.f1);
        casillas[2][1] = (ImageView) j.findViewById(R.id.f2);
        casillas[2][2] = (ImageView) j.findViewById(R.id.f3);
        casillas[2][3] = (ImageView) j.findViewById(R.id.f4);
        casillas[2][4] = (ImageView) j.findViewById(R.id.f5);
        casillas[2][5] = (ImageView) j.findViewById(R.id.f6);
        casillas[2][6] = (ImageView) j.findViewById(R.id.f7);
        casillas[2][7] = (ImageView) j.findViewById(R.id.f8);

        casillas[3][0] = (ImageView) j.findViewById(R.id.e1);
        casillas[3][1] = (ImageView) j.findViewById(R.id.e2);
        casillas[3][2] = (ImageView) j.findViewById(R.id.e3);
        casillas[3][3] = (ImageView) j.findViewById(R.id.e4);
        casillas[3][4] = (ImageView) j.findViewById(R.id.e5);
        casillas[3][5] = (ImageView) j.findViewById(R.id.e6);
        casillas[3][6] = (ImageView) j.findViewById(R.id.e7);
        casillas[3][7] = (ImageView) j.findViewById(R.id.e8);

        casillas[4][0] = (ImageView) j.findViewById(R.id.d1);
        casillas[4][1] = (ImageView) j.findViewById(R.id.d2);
        casillas[4][2] = (ImageView) j.findViewById(R.id.d3);
        casillas[4][3] = (ImageView) j.findViewById(R.id.d4);
        casillas[4][4] = (ImageView) j.findViewById(R.id.d5);
        casillas[4][5] = (ImageView) j.findViewById(R.id.d6);
        casillas[4][6] = (ImageView) j.findViewById(R.id.d7);
        casillas[4][7] = (ImageView) j.findViewById(R.id.d8);

        casillas[5][0] = (ImageView) j.findViewById(R.id.c1);
        casillas[5][1] = (ImageView) j.findViewById(R.id.c2);
        casillas[5][2] = (ImageView) j.findViewById(R.id.c3);
        casillas[5][3] = (ImageView) j.findViewById(R.id.c4);
        casillas[5][4] = (ImageView) j.findViewById(R.id.c5);
        casillas[5][5] = (ImageView) j.findViewById(R.id.c6);
        casillas[5][6] = (ImageView) j.findViewById(R.id.c7);
        casillas[5][7] = (ImageView) j.findViewById(R.id.c8);

        casillas[6][0] = (ImageView) j.findViewById(R.id.b1);
        casillas[6][1] = (ImageView) j.findViewById(R.id.b2);
        casillas[6][2] = (ImageView) j.findViewById(R.id.b3);
        casillas[6][3] = (ImageView) j.findViewById(R.id.b4);
        casillas[6][4] = (ImageView) j.findViewById(R.id.b5);
        casillas[6][5] = (ImageView) j.findViewById(R.id.b6);
        casillas[6][6] = (ImageView) j.findViewById(R.id.b7);
        casillas[6][7] = (ImageView) j.findViewById(R.id.b8);

        casillas[7][0] = (ImageView) j.findViewById(R.id.a1);
        casillas[7][1] = (ImageView) j.findViewById(R.id.a2);
        casillas[7][2] = (ImageView) j.findViewById(R.id.a3);
        casillas[7][3] = (ImageView) j.findViewById(R.id.a4);
        casillas[7][4] = (ImageView) j.findViewById(R.id.a5);
        casillas[7][5] = (ImageView) j.findViewById(R.id.a6);
        casillas[7][6] = (ImageView) j.findViewById(R.id.a7);
        casillas[7][7] = (ImageView) j.findViewById(R.id.a8);
        colocarPiezas(1);
        inicializarCampos(2);
        return false;
    }

    private void colocarPiezas(int bando) {


        casillas[0][7].setImageResource(bando == 1 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);
        casillas[7][7].setImageResource(bando == 1 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);

        casillas[0][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[1][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[2][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[3][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[4][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[5][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[6][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[7][6].setImageResource(bando == 1 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);

        casillas[1][7].setImageResource(bando == 1 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);
        casillas[6][7].setImageResource(bando == 1 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);

        casillas[2][7].setImageResource(bando == 1 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);
        casillas[5][7].setImageResource(bando == 1 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);

        casillas[3][7].setImageResource(bando == 1 ? R.mipmap.alpha_wq : R.mipmap.alpha_bq);
        casillas[4][7].setImageResource(bando == 1 ? R.mipmap.alpha_wk : R.mipmap.alpha_bk);


        casillas[7][0].setImageResource(bando == 2 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);
        casillas[0][0].setImageResource(bando == 2 ? R.mipmap.alpha_wr : R.mipmap.alpha_br);

        casillas[0][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[1][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[2][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[3][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[4][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[5][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[6][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);
        casillas[7][1].setImageResource(bando == 2 ? R.mipmap.alpha_wp : R.mipmap.alpha_bp);

        casillas[1][0].setImageResource(bando == 2 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);
        casillas[6][0].setImageResource(bando == 2 ? R.mipmap.alpha_wn : R.mipmap.alpha_bn);

        casillas[2][0].setImageResource(bando == 2 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);
        casillas[5][0].setImageResource(bando == 2 ? R.mipmap.alpha_wb : R.mipmap.alpha_bb);

        casillas[3][0].setImageResource(bando == 2 ? R.mipmap.alpha_wq : R.mipmap.alpha_bq);
        casillas[4][0].setImageResource(bando == 2 ? R.mipmap.alpha_wk : R.mipmap.alpha_bk);


    }
    private void inicializarCampos(int n) {
        nombreColumnas[0] = (TextView) j.findViewById(R.id.columnaa);
        nombreColumnas[1] = (TextView) j.findViewById(R.id.columnab);
        nombreColumnas[2] = (TextView) j.findViewById(R.id.columnac);
        nombreColumnas[3] = (TextView) j.findViewById(R.id.columnad);
        nombreColumnas[4] = (TextView) j.findViewById(R.id.columnae);
        nombreColumnas[5] = (TextView) j.findViewById(R.id.columnaf);
        nombreColumnas[6] = (TextView) j.findViewById(R.id.columnag);
        nombreColumnas[7] = (TextView) j.findViewById(R.id.columnah);

        numeroFila[0] = (TextView) j.findViewById(R.id.fila1);
        numeroFila[1] = (TextView) j.findViewById(R.id.fila2);
        numeroFila[2] = (TextView) j.findViewById(R.id.fila3);
        numeroFila[3] = (TextView) j.findViewById(R.id.fila4);
        numeroFila[4] = (TextView) j.findViewById(R.id.fila5);
        numeroFila[5] = (TextView) j.findViewById(R.id.fila6);
        numeroFila[6] = (TextView) j.findViewById(R.id.fila7);
        numeroFila[7] = (TextView) j.findViewById(R.id.fila8);

        nombreColumnas[0].setText("a");
        nombreColumnas[1].setText("b");
        nombreColumnas[2].setText("c");
        nombreColumnas[3].setText("d");
        nombreColumnas[4].setText("e");
        nombreColumnas[5].setText("f");
        nombreColumnas[6].setText("g");
        nombreColumnas[7].setText("h");

        numeroFila[0].setText("1");
        numeroFila[1].setText("2");
        numeroFila[2].setText("3");
        numeroFila[3].setText("4");
        numeroFila[4].setText("5");
        numeroFila[5].setText("6");
        numeroFila[6].setText("7");
        numeroFila[7].setText("8");

        if (n == 2) {
            nombreColumnas[0].setText("h");
            nombreColumnas[1].setText("g");
            nombreColumnas[2].setText("f");
            nombreColumnas[3].setText("e");
            nombreColumnas[4].setText("d");
            nombreColumnas[5].setText("c");
            nombreColumnas[6].setText("b");
            nombreColumnas[7].setText("a");

            numeroFila[0].setText("8");
            numeroFila[1].setText("7");
            numeroFila[2].setText("6");
            numeroFila[3].setText("5");
            numeroFila[4].setText("4");
            numeroFila[5].setText("3");
            numeroFila[6].setText("2");
            numeroFila[7].setText("1");
        }


    }
}
