package com.edu.ort.gruposiete.asistenciaatr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

public class MateriasActivity extends AppCompatActivity {
    private TextView tvAlumno;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MateriasRecyclerAdapter adapter;
    private Users usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);
        //PRUEBA DE COMO MANDAR OBJETOS DE UNA ACTIVIDAD A OTRA
        //HAY QUE HACER QUE LOS IBJETS IMPLEMENTEN PARCELEABLE
        tvAlumno = findViewById(R.id.tvAlumno);
        recyclerView = findViewById(R.id.recyclerMaterias);

        usuario = getIntent().getParcelableExtra("USUARIO");
        usuario.setMaterias(getIntent().getParcelableArrayListExtra("MATERIAS"));

        tvAlumno.setText(usuario.getNombre().toUpperCase()+" "+usuario.getApellido().toUpperCase());

        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new MateriasRecyclerAdapter(usuario.getMaterias(), (view, position) -> {
            Toast.makeText(this, "ID: "+ usuario.getMaterias().get(position).getId()+", NOMBRE: "+usuario.getMaterias().get(position).getNombre(),Toast.LENGTH_SHORT).show();
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
