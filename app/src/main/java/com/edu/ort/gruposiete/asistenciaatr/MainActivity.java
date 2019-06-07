package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btLogin;
    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = (Button) findViewById(R.id.btLogin);
        user = (EditText) findViewById(R.id.edUsuario);
        pass = (EditText) findViewById(R.id.edPassword);

        createListView();

        pasarLogin(user.getText().toString(), pass.getText().toString());

    }


    private void createListView() {
        listView= (ListView) findViewById(R.id.lista);
        Adaptador adaptador = new Adaptador(this, getArrayItems());
        listView.setAdapter(adaptador);
    }

    private ArrayList<Usuario> getArrayItems() {
        return XmlParser.Usuarios(this);

    }


    private void pasarLogin(String user, String pass){
        if(buscarUsuario(user,pass)){
            Intent intent = new Intent(this, MateriasActivity.class );
            startActivity(intent);
        }else{
            Toast.makeText(this, "ERROR DE LOGIN VUELVA A INTENTAR", Toast.LENGTH_SHORT);
        }
    }


    private boolean buscarUsuario(String user, String pass){
        int i =0;
        boolean encontrado = false;
        boolean  exito = false;
        while(i < getArrayItems().size() || encontrado){

            if(getArrayItems().get(i).getUser().equals(user) && getArrayItems().get(i).getPass().equals(pass)){
                exito = true;
            }else{
                i++;
            }
        }

        return exito;
    }
}
