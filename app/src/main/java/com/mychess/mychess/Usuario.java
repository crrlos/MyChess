package com.mychess.mychess;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Carlos on 05/11/2015.
 */
final public class Usuario {
    SharedPreferences preferences;
    Context context;

    Usuario(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("usuario", context.MODE_PRIVATE);
    }

    public boolean isEmpty() {
        if(preferences.getString("usuario",null) == null)
            return true;
        return false;
    }

    public String getUsuario() {
        return preferences.getString("usuario", null);
    }

    public void setUsuario(String usuario) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
