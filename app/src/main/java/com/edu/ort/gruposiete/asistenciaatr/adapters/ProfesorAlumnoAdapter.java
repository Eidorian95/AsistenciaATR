package com.edu.ort.gruposiete.asistenciaatr.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.ort.gruposiete.asistenciaatr.R;
import com.edu.ort.gruposiete.asistenciaatr.Users;

import java.util.ArrayList;

public class ProfesorAlumnoAdapter extends RecyclerView.Adapter<ProfesorAlumnoAdapter.ViewHolderAsistenciasAlu>{


    private ArrayList<Users> alumnos;

    public ProfesorAlumnoAdapter(ArrayList<Users> asistencias){
        this.alumnos = asistencias;
    }

    @NonNull
    @Override
    public ViewHolderAsistenciasAlu onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profesor_alumno_template,viewGroup,false);

        return new ViewHolderAsistenciasAlu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAsistenciasAlu viewHolderAsistenciasAlu, int i) {
        Users asis = alumnos.get(i);
        String nombreAMostrar = asis.getNombre() + " " + asis.getApellido();
        viewHolderAsistenciasAlu.fechaAsistencia.setText(nombreAMostrar);
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }


    public class ViewHolderAsistenciasAlu extends RecyclerView.ViewHolder {

        private TextView fechaAsistencia;

        public ViewHolderAsistenciasAlu(@NonNull View itemView) {
            super(itemView);
            fechaAsistencia = itemView.findViewById(R.id.asistenciaFecha);

        }
    }
}
