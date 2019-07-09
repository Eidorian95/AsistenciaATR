package com.edu.ort.gruposiete.asistenciaatr;

class Asistencia {

    private boolean presente;
    private String fecha;

    public Asistencia() {
    }

    public Asistencia(boolean presente, String fecha) {
        this.presente = presente;
        this.fecha = fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
