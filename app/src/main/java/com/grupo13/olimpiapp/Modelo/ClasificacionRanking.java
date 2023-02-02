package com.grupo13.olimpiapp.Modelo;

public class ClasificacionRanking {
    int posicion;
    int puntuacion;
    String nombre;

    public ClasificacionRanking(){

    }
    public ClasificacionRanking(int posicion, int puntuacion, String nombre) {
        this.posicion = posicion;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
