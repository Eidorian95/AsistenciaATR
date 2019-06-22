package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Adaptador extends BaseAdapter {
    private ArrayList<Users> list;
    private Context context;

    public Adaptador(Context context, ArrayList<Users> arrayItems) {
        this.context=context;
        this.list=arrayItems;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_list,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        TextView apellido = (TextView) view.findViewById(R.id.apellido);
        Users item= (Users) getItem(i);
        nombre.setText(item.getNombre());
        apellido.setText(item.getNombre());
        return view;
    }
}
