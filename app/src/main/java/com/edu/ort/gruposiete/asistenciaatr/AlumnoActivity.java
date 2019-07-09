package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AlumnoActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private int id_materia;
    private String nom_materia;
    private static String LASTNAME = "LASTNAME";
    private static String ID_MATERIA = "ID_MATERIA";
    private static String NOM_MATERIA = "NOM_MATERIA";
    Button btScannearQr;
    TextView tvAsistenciaAlumno;
    TextView tvNomMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        setViews();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        nom_materia = getIntent().getStringExtra(NOM_MATERIA);
        id_materia =  Integer.parseInt(getIntent().getStringExtra(ID_MATERIA));
        tvNomMateria.setText(nom_materia.toUpperCase());

        getIntent().getStringExtra(LASTNAME);
        btScannearQr.setOnClickListener(v ->{
            iniciarScanner();
        });

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
        tvAsistenciaAlumno = findViewById(R.id.tvAsistenciaAlumno);
        tvNomMateria = findViewById(R.id.tvMateria);
    }

    private void setAsistenciaOk(int idMateria, String fecha){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Users user = postSnapshot.getValue(Users.class);
                    if(user.getTipo()==false && user.getMaterias().get(0).getId() == idMateria){

                        DatabaseReference fechaRef = postSnapshot.getRef().child(fecha);
                        fechaRef.setValue(user.getNombre());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
