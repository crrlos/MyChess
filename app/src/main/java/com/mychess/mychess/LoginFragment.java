package com.mychess.mychess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginFragment extends Fragment{
    EditText usuario;
    EditText clave;
    ProgressDialog progressDialog;
    Login login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        if(!(preferences.getString("usuario",null) == null)){
            Intent intent = new Intent(getContext(),Juego.class);
            startActivity(intent);
            getActivity().finish();

        }

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setTitle("Iniciando Sesión");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getContext(), "cancelado", Toast.LENGTH_SHORT).show();

            }
        });


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
                }else
                {
                    String[] datos = new String[2];
                    datos[0] = usuario.getText().toString();
                    datos[1] = clave.getText().toString();
                    login = new Login();
                    login.execute(datos);

                }

            }
        });


        return view;
    }

    class Login extends AsyncTask<String,String,Void>{

        @Override
        protected Void doInBackground(String... params) {
            final String NAMESPACE = "http://servicios/";
            final String URL = "http://servermychess.tk:8080/RegistroLogin/RegistroLogin";
            final String METHOD_NAME = "login";
            final String SOAP_ACTION = "http://Servicios/login";

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("usuario",params[0]);
            request.addProperty("clave",params[1]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transportSE = new HttpTransportSE(URL);
            try{
                transportSE.call(SOAP_ACTION,envelope);
                SoapPrimitive respuesta = (SoapPrimitive) envelope.getResponse();
                publishProgress(respuesta.toString());

            }catch(Exception ex){
                publishProgress(ex.getMessage());

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(values[0].equals("0")){
                SharedPreferences preferences = getActivity().getSharedPreferences("usuario",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("usuario", usuario.getText().toString());
                editor.commit();
                Intent intent = new Intent(getContext(),Juego.class);
                startActivity(intent);
                getActivity().finish();
            }else{
                Toast.makeText(getContext(), values[0], Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
    }
}