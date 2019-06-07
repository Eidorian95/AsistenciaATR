package com.edu.ort.gruposiete.asistenciaatr;


public class Usuario {

    private String id;
    private String user;
    private String pass;
    private String nombre;
    private String apellido;
    private String tipo;

    public Usuario(String id, String user, String pass, String nombre, String apellido, String tipo) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
