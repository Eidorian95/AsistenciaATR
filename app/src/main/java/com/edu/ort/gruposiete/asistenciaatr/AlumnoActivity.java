package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ort.gruposiete.asistenciaatr.adapters.AlumnoAsisteciaAdapter;
import com.edu.ort.gruposiete.asistenciaatr.adapters.MateriasRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlumnoActivity extends AppCompatActivity {
    private static String LASTNAME = "LASTNAME";
    private static String ID_MATERIA = "ID_MATERIA";
    private static String NOM_MATERIA = "NOM_MATERIA";
    private static String USUARIO = "USUARIO";

    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private int id_materia;
    private String nom_materia;
    private Users alumno;
    private ArrayList<Asistencia> asistencias;
    private boolean asistio = false;


    private Button btScannearQr;
    private TextView tvNomMateria;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AlumnoAsisteciaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        setViews();

        //VILLERADA
        asistencias = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlumnoAsisteciaAdapter(asistencias);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        alumno = getIntent().getParcelableExtra(USUARIO);
        nom_materia = getIntent().getStringExtra(NOM_MATERIA);
        id_materia = getIntent().getIntExtra(ID_MATERIA,0);
        tvNomMateria.setText(nom_materia.toUpperCase());

        getIntent().getStringExtra(LASTNAME);
        btScannearQr.setOnClickListener(v ->{
            iniciarScanner();
        });


        setAsistenciaRecycler(alumno.getId(),id_materia);
    }

    private void iniciarScanner() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this,"Cancelaste el scanneo",Toast.LENGTH_SHORT).show();
            }else{
                setAsistenciaOk(id_materia,result.getContents());
            }

        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void setViews(){
        btScannearQr = findViewById(R.id.btScannerQr);
        tvNomMateria = findViewById(R.id.tvMateria);
        recyclerView = findViewById(R.id.asistenciasRecycler);
    }

    private void setAsistenciaOk(int idMateriaAlumno, String fecha){

        String[]split = fecha.split("-");
        String fecha_split = split[0];
        String id_split = split[1];

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Users profesor = postSnapshot.getValue(Users.class);
                    if(profesor.getTipo()==false){
                        if(id_split.equals(String.valueOf(profesor.getMaterias().get(0).getId()))){
                            if(id_split.equals(String.valueOf(idMateriaAlumno))){
                                Map<String, String> u = new HashMap<>();
                                u.put("id", String.valueOf(alumno.getId()));
                                u.put("alumno", alumno.getApellido());
                                myRef.child(profesor.getId() +"/materias/"+0+"/asistencias/"+fecha_split).child(String.valueOf(alumno.getId())).setValue(u);


                                Map<String, String> a = new HashMap<>();
                                a.put("fecha", fecha_split);
                                myRef.child(alumno.getId() + "/materias/"+idMateriaAlumno+"/asistencias").child(fecha_split).setValue(a);

                            }else{
                                Toast.makeText(getApplicationContext(),"No coinciden las materias",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }

                    setAsistenciaRecycler(alumno.getId(),idMateriaAlumno);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setAsistenciaRecycler(int idAlu, int idMateria){

        DatabaseReference asistenciasRef = database.getReference("users").child(String.valueOf(idAlu)).child("materias").child(String.valueOf(idMateria)).child("asistencias");
        asistenciasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                asistencias.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Asistencia a = ds.getValue(Asistencia.class);
                    asistencias.add(a);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
