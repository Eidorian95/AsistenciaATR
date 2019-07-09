package com.edu.ort.gruposiete.asistenciaatr;

public class Asistencia {

    private String fecha;

    public Asistencia(String fecha) {
        this.fecha = fecha;
    }

    public Asistencia() {
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
