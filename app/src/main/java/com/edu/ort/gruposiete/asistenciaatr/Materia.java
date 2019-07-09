package com.edu.ort.gruposiete.asistenciaatr;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

class Materia implements Parcelable {

    private String nombre;
    private int id;
    private ArrayList<Asistencia> asistencias;

    public Materia() {
    }

    public Materia(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public Materia(String nombre, int id, ArrayList<Asistencia> asistencias) {
        this.nombre = nombre;
        this.id = id;
        this.asistencias = asistencias;
    }

    protected Materia(Parcel in) {
        nombre = in.readString();
        id = in.readInt();
    }

    public static final Creator<Materia> CREATOR = new Creator<Materia>() {
        @Override
        public Materia createFromParcel(Parcel in) {
            return new Materia(in);
        }

        @Override
        public Materia[] newArray(int size) {
            return new Materia[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(ArrayList<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(id);
    }
}
