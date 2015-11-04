package com.mychess.mychess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    EditText usuario;
    EditText clave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        usuario = (EditText) view.findViewById(R.id.loginUsuario);
        clave = (EditText) view.findViewById(R.id.loginClave);
        Button entrar = (Button) view.findViewById(R.id.button);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().length() == 0) {
                    Toast.makeText(getContext(), "usuario vacío", Toast.LENGTH_SHORT).show();
                } else if (clave.getText().length() == 0) {
                    Toast.makeText(getContext(), "clave vacía", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}