package com.grupo13.olimpiapp.Modelo;

public class Decision {

    private int codigo;
    private String decision;
    private String descripcion;

    public Decision(int codigo, String decision, String descripcion) {
        this.codigo = codigo;
        this.decision = decision;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
