package com.grupo13.olimpiapp.Modelo;

public class Usuario {
    public String nombre;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}