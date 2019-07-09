package com.edu.ort.gruposiete.asistenciaatr;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Users implements Parcelable {

    private int id;
    private ArrayList<Materia> materias;
    private String user;
    private String pass;
    private String nombre;
    private String apellido;
    private boolean tipo;

    public Users() {

    }

    public Users(String user, int id, ArrayList<Materia> materias, String pass, String nombre, String apellido, boolean tipo) {
        this.apellido = apellido;
        this.id = id;
        this.materias = materias;
        this.nombre = nombre;
        this.pass = pass;
        this.tipo = tipo;
        this.user = user;

    }

    protected Users(Parcel in) {
        id = Integer.parseInt(in.readString());
        user = in.readString();
        pass = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        tipo = in.readByte() != 0;
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public boolean getTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(String.valueOf(id));
        dest.writeString(user);
        dest.writeString(pass);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeByte((byte) (tipo ? 1 : 0));
    }
}
