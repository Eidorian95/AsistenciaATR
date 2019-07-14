package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private EditText user;
    private EditText pass;
    private static String MATERIAS = "MATERIAS";
    private static String USUARIO = "USUARIO";
    private  ArrayList<Users> usersItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = (Button) findViewById(R.id.btLogin);
        user = (EditText) findViewById(R.id.edUsuario);
        pass = (EditText) findViewById(R.id.edPassword);

        btLogin.setEnabled(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersItems = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Users u = ds.getValue(Users.class);
                    usersItems.add(u);

                }
                btLogin.setEnabled(true);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btLogin.setOnClickListener(v -> {
            pasarLogin(usersItems, user.getText().toString(), pass.getText().toString());
        });
    }


    private void pasarLogin(ArrayList<Users>lista, String user, String pass){
        Users usuario = buscarUsuario(lista,user,pass);
        if(usuario != null){
            if(!usuario.getTipo()){
                Intent intent = new Intent(this, ProfesorActivity.class );
                intent.putExtra(USUARIO, usuario);
                intent.putParcelableArrayListExtra(MATERIAS, usuario.getMaterias());
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, MateriasActivity.class );
                intent.putExtra(USUARIO, usuario);
                intent.putParcelableArrayListExtra(MATERIAS, usuario.getMaterias());
                startActivity(intent);
                finish();
            }

        }else{
            Toast.makeText(this, "USUARIO O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show();
        }
    }


    private Users buscarUsuario(ArrayList<Users> lista,String user, String pass){

        int i =0;
        Users usuarioBuscado = null;

        while(i < lista.size() && usuarioBuscado == null){
            if(lista.get(i).getUser().equals(user) && lista.get(i).getPass().equals(pass)){
                usuarioBuscado = lista.get(i);
            }
                i++;
        }
        return usuarioBuscado;
    }
}
