package com.hantsch.rossana.mobike.models;

public class Bikes {

    private int id;
    private String modelo;
    private String marca;
    private String color;
    private String tipo;

    public Bikes(){}

    public Bikes(String marca, String modelo, String tipo, String color){

        this.modelo = modelo;
        this.marca = marca;
        this.tipo = tipo;
        this.color = color;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
