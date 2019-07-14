package com.edu.ort.gruposiete.asistenciaatr;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.ort.gruposiete.asistenciaatr.adapters.AlumnoAsisteciaAdapter;
import com.edu.ort.gruposiete.asistenciaatr.adapters.MateriasRecyclerAdapter;
import com.edu.ort.gruposiete.asistenciaatr.adapters.ProfesorAlumnoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfesorActivity extends AppCompatActivity {

    private static String MATERIAS = "MATERIAS";
    private static String USUARIO = "USUARIO";
    private Users profesor;
    private Users alumno;
    private DatabaseReference myRef;

    EditText edFecha;
    Button btGen;
    ImageView ivQrGen;
    Calendar calendario = Calendar.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MateriasRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        setViews();

        profesor = getIntent().getParcelableExtra(USUARIO);
        profesor.setMaterias(getIntent().getParcelableArrayListExtra(MATERIAS));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");
        recyclerView = findViewById(R.id.recyclerPresentes);

        DatePickerDialog.OnDateSetListener date = (view, anio, mes, dia) -> {
            calendario.set(Calendar.YEAR, anio);
            calendario.set(Calendar.MONTH, mes);
            calendario.set(Calendar.DAY_OF_MONTH, dia);
            String myFormat = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            edFecha.setText(sdf.format(calendario.getTime()));
        };

        edFecha.setOnClickListener(v -> new DatePickerDialog(ProfesorActivity.this, date, calendario
                .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show());

        btGen.setOnClickListener(v->{
            String fecha = edFecha.getText().toString().trim();

            if(fecha.length() > 0){
                if(fecha.contains("/")){
                    String[] split = fecha.split("/");
                    split[split.length-1] = "20" + split[split.length-1];
                    fecha = "";
                    for(String s : split){
                        fecha += s;
                    }
                }
                generateQr(fecha+"-"+profesor.getMaterias().get(0).getId());
                //setNuevaFecha(fecha);
                setRecyclerView(fecha);
            }else{
                Toast.makeText(this, "Ingrese la fecha para generar el codigo QR", Toast.LENGTH_SHORT).show();
            }

        });

        alumno = getIntent().getParcelableExtra(USUARIO);

    }

    private void setRecyclerView(String fechaActual){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Users> alumnos = new ArrayList<Users>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Users usuario = ds.getValue(Users.class);
                    ArrayList<Materia> materias = usuario.getMaterias();
                    if(materias != null && materias.size() > 0){
                        int pos = materias.indexOf(profesor.getMaterias().get(0));
                        if(pos != -1){
                            Materia materiaActual = materias.get(pos);
                            Map<String, String> asistencias = materiaActual.getAsistencias();
                            if(asistencias != null){
                                for(String keys : asistencias.keySet()){
                                    if(asistencias.get(keys).equals(fechaActual)){
                                        alumnos.add(usuario);
                                    }
                                }
                            }
                        }
                    }
                }
                createRecycler(alumnos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createRecycler(ArrayList<Users> alumnos){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ProfesorAlumnoAdapter(alumnos));
    }

    private void setViews(){
        edFecha = findViewById(R.id.edFecha);
        btGen = findViewById(R.id.btGen);
        ivQrGen = findViewById(R.id.ivQrGen);
    }

    private  void generateQr(String fecha){

        MultiFormatWriter mfW = new MultiFormatWriter();

        Map<String, String> u = new HashMap<>();
        u.put("id","123");
        try{
            BitMatrix bitMatrix = mfW.encode(fecha, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQrGen.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }
    }

    private void setNuevaFecha(String fecha){
        myRef.child("users").child(String.valueOf(profesor.getId())).child("materias").child("0").child("asistencias").child(fecha).push().setValue(fecha);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
