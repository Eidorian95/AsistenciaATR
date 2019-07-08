package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MateriasActivity extends AppCompatActivity {
    private TextView tvAlumno;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MateriasRecyclerAdapter adapter;
    private Users usuario;
    private static String MATERIAS = "MATERIAS";
    private static String USUARIO = "USUARIO";
    private static String ID_MATERIA = "ID_MATERIA";
    private static String NOM_MATERIA = "NOM_MATERIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);
        tvAlumno = findViewById(R.id.tvAlumno);
        recyclerView = findViewById(R.id.recyclerMaterias);

        //PRUEBA DE COMO MANDAR OBJETOS DE UNA ACTIVIDAD A OTRA
        //HAY QUE HACER QUE LOS IBJETS IMPLEMENTEN PARCELEABLE


            usuario = getIntent().getParcelableExtra(USUARIO);
            tvAlumno.setText(usuario.getNombre().toUpperCase()+" "+usuario.getApellido().toUpperCase());

            if (getIntent().hasExtra(MATERIAS)){
                usuario.setMaterias(getIntent().getParcelableArrayListExtra(MATERIAS));

                setRecyclerView();
            }else{
                Toast.makeText(getApplicationContext(), "@string/error_materias", Toast.LENGTH_SHORT).show();
            }
    }

    private void setRecyclerView(){
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new MateriasRecyclerAdapter(usuario.getMaterias());

        //llamo al listener del adaptador
        //se puede dejar asi o pasarlo al lambda

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"ID: "+ usuario.getMaterias().get(recyclerView.getChildAdapterPosition(v)).getId()+", NOMBRE: "+usuario.getMaterias().get(recyclerView.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AlumnoActivity.class);
                intent.putExtra(ID_MATERIA, usuario.getMaterias().get(recyclerView.getChildAdapterPosition(v)).getId());
                intent.putExtra(NOM_MATERIA, usuario.getMaterias().get(recyclerView.getChildAdapterPosition(v)).getNombre());
                startActivity(intent);
            }
        });

    }
}
