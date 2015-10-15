package com.mychess.mychess;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class Juego extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    ImageView casillas[][] = new ImageView[8][8];
    ImageView origen;
    ImageView destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializarCasillas();
        setDefaultColor();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.juego, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void inicializarCasillas() {

        casillas[0][0] = (ImageView) findViewById(R.id.a1);
        casillas[0][1] = (ImageView) findViewById(R.id.a2);
        casillas[0][2] = (ImageView) findViewById(R.id.a3);
        casillas[0][3] = (ImageView) findViewById(R.id.a4);
        casillas[0][4] = (ImageView) findViewById(R.id.a5);
        casillas[0][5] = (ImageView) findViewById(R.id.a6);
        casillas[0][6] = (ImageView) findViewById(R.id.a7);
        casillas[0][7] = (ImageView) findViewById(R.id.a8);


        casillas[1][0] = (ImageView) findViewById(R.id.b1);
        casillas[1][1] = (ImageView) findViewById(R.id.b2);
        casillas[1][2] = (ImageView) findViewById(R.id.b3);
        casillas[1][3] = (ImageView) findViewById(R.id.b4);
        casillas[1][4] = (ImageView) findViewById(R.id.b5);
        casillas[1][5] = (ImageView) findViewById(R.id.b6);
        casillas[1][6] = (ImageView) findViewById(R.id.b7);
        casillas[1][7] = (ImageView) findViewById(R.id.b8);

        casillas[2][0] = (ImageView) findViewById(R.id.c1);
        casillas[2][1] = (ImageView) findViewById(R.id.c2);
        casillas[2][2] = (ImageView) findViewById(R.id.c3);
        casillas[2][3] = (ImageView) findViewById(R.id.c4);
        casillas[2][4] = (ImageView) findViewById(R.id.c5);
        casillas[2][5] = (ImageView) findViewById(R.id.c6);
        casillas[2][6] = (ImageView) findViewById(R.id.c7);
        casillas[2][7] = (ImageView) findViewById(R.id.c8);

        casillas[3][0] = (ImageView) findViewById(R.id.d1);
        casillas[3][1] = (ImageView) findViewById(R.id.d2);
        casillas[3][2] = (ImageView) findViewById(R.id.d3);
        casillas[3][3] = (ImageView) findViewById(R.id.d4);
        casillas[3][4] = (ImageView) findViewById(R.id.d5);
        casillas[3][5] = (ImageView) findViewById(R.id.d6);
        casillas[3][6] = (ImageView) findViewById(R.id.d7);
        casillas[3][7] = (ImageView) findViewById(R.id.d8);

        casillas[4][0] = (ImageView) findViewById(R.id.e1);
        casillas[4][1] = (ImageView) findViewById(R.id.e2);
        casillas[4][2] = (ImageView) findViewById(R.id.e3);
        casillas[4][3] = (ImageView) findViewById(R.id.e4);
        casillas[4][4] = (ImageView) findViewById(R.id.e5);
        casillas[4][5] = (ImageView) findViewById(R.id.e6);
        casillas[4][6] = (ImageView) findViewById(R.id.e7);
        casillas[4][7] = (ImageView) findViewById(R.id.e8);

        casillas[5][0] = (ImageView) findViewById(R.id.f1);
        casillas[5][1] = (ImageView) findViewById(R.id.f2);
        casillas[5][2] = (ImageView) findViewById(R.id.f3);
        casillas[5][3] = (ImageView) findViewById(R.id.f4);
        casillas[5][4] = (ImageView) findViewById(R.id.f5);
        casillas[5][5] = (ImageView) findViewById(R.id.f6);
        casillas[5][6] = (ImageView) findViewById(R.id.f7);
        casillas[5][7] = (ImageView) findViewById(R.id.f8);

        casillas[6][0] = (ImageView) findViewById(R.id.g1);
        casillas[6][1] = (ImageView) findViewById(R.id.g2);
        casillas[6][2] = (ImageView) findViewById(R.id.g3);
        casillas[6][3] = (ImageView) findViewById(R.id.g4);
        casillas[6][4] = (ImageView) findViewById(R.id.g5);
        casillas[6][5] = (ImageView) findViewById(R.id.g6);
        casillas[6][6] = (ImageView) findViewById(R.id.g7);
        casillas[6][7] = (ImageView) findViewById(R.id.g8);

        casillas[7][0] = (ImageView) findViewById(R.id.h1);
        casillas[7][1] = (ImageView) findViewById(R.id.h2);
        casillas[7][2] = (ImageView) findViewById(R.id.h3);
        casillas[7][3] = (ImageView) findViewById(R.id.h4);
        casillas[7][4] = (ImageView) findViewById(R.id.h5);
        casillas[7][5] = (ImageView) findViewById(R.id.h6);
        casillas[7][6] = (ImageView) findViewById(R.id.h7);
        casillas[7][7] = (ImageView) findViewById(R.id.h8);

    }


    private void setDefaultColor() {
        boolean dark = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (dark)
                    casillas[i][j].setBackgroundColor(Color.parseColor("#7986CB"));
                else
                    casillas[i][j].setBackgroundColor(Color.parseColor("#C5CAE9"));
                dark = !dark;
            }
            dark = !dark;
        }
    }

    private void setOnclickListener(){
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                casillas[i][j].setOnClickListener(this);

            }

        }
    }

    @Override
    public void onClick(View v) {

    }
}
