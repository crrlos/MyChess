package com.mychess.mychess;

/**
 * Created by Carlos on 14/10/2015.
 */
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class RegistroFragment extends Fragment {
ProgressDialog progressDialog;
    EditText usuario;
    EditText email;
    EditText clave;
    EditText clave2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.registro_fragment, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setTitle("Registrando");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // Toast.makeText(getContext(), "cancelado", Toast.LENGTH_SHORT).show();

            }
        });

        usuario = (EditText) v.findViewById(R.id.regUsuario);
        email = (EditText) v.findViewById(R.id.regEmail);
        clave = (EditText) v.findViewById(R.id.regClave);
        clave2 = (EditText) v.findViewById(R.id.regClave2);

        Button btnRegistro = (Button) v.findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String[] params = {usuario.getText().toString(),email.getText().toString(),clave.getText().toString()};
                Registro registro = new Registro();
                registro.execute(params);
            }
        });
        return v;
    }
    class Registro extends AsyncTask<String,String,Void> {

        @Override
        protected Void doInBackground(String... params) {
            final String NAMESPACE = "http://servicios/";
            final String URL = "http://servermychess.tk:8080/RegistroLogin/RegistroLogin";
            final String METHOD_NAME = "registro";
            final String SOAP_ACTION = "http://Servicios/registro";

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("usuario",params[0]);
            request.addProperty("email",params[1]);
            request.addProperty("clave",params[2]);
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
            switch (values[0]){
                case "1":{
                    new Usuario(getContext()).setUsuario(usuario.getText().toString());
                    Intent intent = new Intent(getContext(),Juego.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
                case "2":{
                    progressDialog.cancel();
                    Toast.makeText(getContext(), "El usuario ya está en uso", Toast.LENGTH_SHORT).show();
                }
                break;
                case "3":{
                    progressDialog.cancel();
                    Toast.makeText(getContext(), "El email ya está registrado", Toast.LENGTH_SHORT).show();
                }
                break;
                    
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
    }
}