package com.edu.ort.gruposiete.asistenciaatr;


import java.util.ArrayList;

public class Users {

    private String id;
    private ArrayList<Materia> materias;
    private String user;
    private String pass;
    private String nombre;
    private String apellido;
    private boolean tipo;

    public Users() {

    }

    public Users(String user, ArrayList<Materia>materias, String pass, String nombre, String apellido, boolean tipo) {
        this.apellido = apellido;
        this.materias = materias;
        this.nombre = nombre;
        this.pass = pass;
        this.tipo = tipo;
        this.user = user;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
