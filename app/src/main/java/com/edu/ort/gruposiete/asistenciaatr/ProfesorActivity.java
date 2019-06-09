package com.edu.ort.gruposiete.asistenciaatr;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfesorActivity extends AppCompatActivity {

    EditText edFecha;
    Button btGen;
    ImageView ivQrGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        setViews();

            btGen.setOnClickListener(v->{

                String fecha = edFecha.getText().toString().trim();

                if(!fecha.equals("")){
                    generateQr(fecha);
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

        try{
            BitMatrix bitMatrix = mfW.encode(fecha, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQrGen.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }
    }
}
