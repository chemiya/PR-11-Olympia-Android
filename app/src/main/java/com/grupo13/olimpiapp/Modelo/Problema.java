package com.grupo13.olimpiapp.Modelo;

public class Problema {
    private int situacion;
    private int numero;
    private String titulo;
    private String descripcion;
    private String opcionA;
    private String opcionB;
    private String opcionC;

    public Problema(int situacion, int numero, String titulo, String descripcion, String opcionA, String opcionB, String opcionC) {
        this.situacion = situacion;
        this.numero = numero;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
    }

    public Problema(){

    }

    public int getSituacion() {
        return situacion;
    }

    public void setSituacion(int situacion) {
        this.situacion = situacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }
}
