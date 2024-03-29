package com.edu.ort.gruposiete.asistenciaatr.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.ort.gruposiete.asistenciaatr.R;

import java.util.ArrayList;

public class AlumnoAsisteciaAdapter  extends RecyclerView.Adapter<AlumnoAsisteciaAdapter.ViewHolderAsistenciasAlu>{


    private ArrayList<String> asistencias;

    public AlumnoAsisteciaAdapter(ArrayList<String> asistencias){
        this.asistencias = asistencias;
    }

    @NonNull
    @Override
    public ViewHolderAsistenciasAlu onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alumnos_asistencias_template,viewGroup,false);

        return new ViewHolderAsistenciasAlu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAsistenciasAlu viewHolderAsistenciasAlu, int i) {
        String asis = asistencias.get(i);

        viewHolderAsistenciasAlu.fechaAsistencia.setText(asis);
    }

    @Override
    public int getItemCount() {
        return asistencias.size();
    }


    public class ViewHolderAsistenciasAlu extends RecyclerView.ViewHolder {

        private TextView fechaAsistencia;

        public ViewHolderAsistenciasAlu(@NonNull View itemView) {
            super(itemView);
            fechaAsistencia = itemView.findViewById(R.id.asistenciaFecha);

        }
    }
}
