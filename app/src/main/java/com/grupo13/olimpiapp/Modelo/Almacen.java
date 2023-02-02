package com.grupo13.olimpiapp.Modelo;

public class Almacen {
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(int trabajadores) {
        this.trabajadores = trabajadores;
    }

    public int getNotoriedad() {
        return notoriedad;
    }

    public void setNotoriedad(int notoriedad) {
        this.notoriedad = notoriedad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getSituacion() {
        return situacion;
    }

    public void setSituacion(int situacion) {
        this.situacion = situacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Almacen(int codigo, int presupuesto, int tiempo, int trabajadores, int notoriedad, String ciudad, int situacion, int puntuacion, int medicion_0, int medicion_1, int medicion_2,String dificultad) {
        this.codigo = codigo;
        this.presupuesto = presupuesto;
        this.tiempo = tiempo;
        this.trabajadores = trabajadores;
        this.notoriedad = notoriedad;
        this.ciudad = ciudad;
        this.situacion = situacion;
        this.puntuacion = puntuacion;
        this.medicion_0=medicion_0;
        this.medicion_1=medicion_1;
        this.medicion_2=medicion_2;
        this.dificultad=dificultad;

    }

    public int getMedicion_0() {
        return medicion_0;
    }

    public void setMedicion_0(int medicion_0) {
        this.medicion_0 = medicion_0;
    }

    public int getMedicion_1() {
        return medicion_1;
    }

    public void setMedicion_1(int medicion_1) {
        this.medicion_1 = medicion_1;
    }

    public int getMedicion_2() {
        return medicion_2;
    }

    public void setMedicion_2(int medicion_2) {
        this.medicion_2 = medicion_2;
    }

    public Almacen() {

    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    private int codigo;
    private int presupuesto;
    private int tiempo;
    private int trabajadores;
    private int notoriedad;
    private String ciudad;
    private int situacion;
    private int puntuacion;
    private String dificultad;

    private int medicion_0;
    private int medicion_1;
    private int medicion_2;
}
