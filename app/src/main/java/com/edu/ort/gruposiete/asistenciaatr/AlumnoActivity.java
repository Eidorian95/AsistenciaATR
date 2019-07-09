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

        asistencias = getAsistenciasFirebase(alumno.getId(),id_materia);

        //setRecyclerView(asistencias);
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
//                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
//                tvAsistenciaAlumno.setText(result.getContents()+" - "+getIntent().getStringExtra("NAME")+ getIntent().getStringExtra("LASTNAME")+" - PRESENTE");

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

    private void setAsistenciaOk(int idMateria, String fecha){
        String key =  String.valueOf(alumno.getId());
        String keyMateriaId = String.valueOf(id_materia);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Users user = postSnapshot.getValue(Users.class);
                    if(user.getTipo()==false && user.getMaterias().get(0).getId() == idMateria){

                        Map<String, String> u = new HashMap<>();
                        u.put("id",String.valueOf(alumno.getId()));
                        u.put("alumno",alumno.getApellido());
                        myRef.child("0/materias/0/asistencias/"+fecha).child(key).setValue(u);

                        Map<String, String> a = new HashMap<>();
                        a.put("fecha",fecha);
                        myRef.child(alumno.getId()+"/materias/"+id_materia+"/asistencias").child(fecha).setValue(a);
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<Asistencia> getAsistenciasFirebase(int idAlu, int idMateria){

        ArrayList<Asistencia> asist = new ArrayList<>();
        DatabaseReference asistenciasRef = database.getReference("users").child(String.valueOf(idAlu)).child("materias").child(String.valueOf(idMateria)).child("asistencias");

        asistenciasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Asistencia a = ds.getValue(Asistencia.class);
                    asist.add(a);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return asist;
    }


    private void setRecyclerView(ArrayList<Asistencia> mAsistencias){

        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new AlumnoAsisteciaAdapter(mAsistencias);

        //llamo al listener del adaptador
        //se puede dejar asi o pasarlo al lambda

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
