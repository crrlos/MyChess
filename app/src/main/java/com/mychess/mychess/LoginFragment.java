package com.mychess.mychess;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
                Login login = new Login();
                login.execute();
            }
        });


        return view;
    }
    class Login extends AsyncTask<Void,String,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://servicios/";
            final String URL = "http://servermychess.tk:8080/RegistroLogin/RegistroLogin";
            final String METHOD_NAME = "login";
            final String SOAP_ACTION = "http://Servicios/login";

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("usuario","crrlos");
            request.addProperty("clave","a");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transportSE = new HttpTransportSE(URL);
            try{
                transportSE.call(SOAP_ACTION,envelope);
                SoapPrimitive respuesta = (SoapPrimitive) envelope.getResponse();
                publishProgress(respuesta.toString());

            }catch(Exception ex){

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }
}