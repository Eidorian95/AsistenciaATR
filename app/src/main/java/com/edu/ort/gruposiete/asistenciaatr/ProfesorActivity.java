package com.edu.ort.gruposiete.asistenciaatr;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Map;

public class ProfesorActivity extends AppCompatActivity {

    private static String MATERIAS = "MATERIAS";
    private static String USUARIO = "USUARIO";
    private Users profesor;
    private DatabaseReference myRef;


    EditText edFecha;
    Button btGen;
    ImageView ivQrGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        setViews();

        profesor = getIntent().getParcelableExtra(USUARIO);
        profesor.setMaterias(getIntent().getParcelableArrayListExtra(MATERIAS));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        btGen.setOnClickListener(v->{

            String fecha = edFecha.getText().toString().trim();

            if(!fecha.equals("")){
                    generateQr(fecha+"-"+profesor.getMaterias().get(0).getId());
                    setNuevaFecha(fecha);
                }else{
                    Toast.makeText(this, "Ingrese la fecha para generar el codigo QR", Toast.LENGTH_SHORT).show();
            }

        });

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
