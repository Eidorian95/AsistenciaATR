package com.edu.ort.gruposiete.asistenciaatr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MateriasRecyclerAdapter extends RecyclerView.Adapter<MateriasRecyclerAdapter.ViewHolderMaterias> implements View.OnClickListener{

    private ArrayList<Materia> materias;
    private View.OnClickListener listener;

    public MateriasRecyclerAdapter(ArrayList<Materia> materias){
        this.materias =  materias;
    }

    @NonNull
    @Override
    public ViewHolderMaterias onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.materias_template,viewGroup,false);
        view.setOnClickListener(this);
        return new ViewHolderMaterias(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMaterias viewHolderMaterias, int i) {
       Materia materia = materias.get(i);

       viewHolderMaterias.nombreMateria.setText(materia.getNombre().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }


    public class ViewHolderMaterias extends RecyclerView.ViewHolder{

        TextView nombreMateria;

        public ViewHolderMaterias(@NonNull View itemView) {
            super(itemView);
            nombreMateria = itemView.findViewById(R.id.tvMateriaNombre);
        }

    }
}
