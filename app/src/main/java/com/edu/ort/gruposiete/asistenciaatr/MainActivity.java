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

        btLogin.setOnClickListener(v -> {
            pasarLogin(user.getText().toString(), pass.getText().toString());
        });


    }


    private ArrayList<Usuario> getArrayItems() {
        return XmlParser.Usuarios(this);

    }


    private void pasarLogin(String user, String pass){
        Usuario usuario = buscarUsuario(user,pass);
        if(usuario != null){
            if(usuario.getTipo().equals("profesor")){
                Intent intent = new Intent(this, ProfesorActivity.class );
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, MateriasActivity.class );
                startActivity(intent);
            }

        }else{
            Toast.makeText(this, "USUARIO O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show();
        }
    }


    private Usuario buscarUsuario(String user, String pass){
        int i =0;
        ArrayList<Usuario> usuarios = getArrayItems();
        Usuario usuario = null;

        while(i < usuarios.size() && usuario == null){
            if(usuarios.get(i).getUser().equals(user) && usuarios.get(i).getPass().equals(pass)){
                usuario = usuarios.get(i);
            }
                i++;
        }

        return usuario;
    }
}
