package com.example.alcra.silverhawksapp.entities;

/**
 * Created by alcra on 27/05/2018.
 */

public class Presenca{

    public static final String COLLECTION_PRESENCA = "presenca";
    public static final int P = 1;
    public static final int J = 2;
    public static final int F = 3;
    String name;
    String date;
    int tipo;

    public Presenca(String name, String date, int tipo) {
        this.name = name;
        this.date = date;
        this.tipo = tipo;
    }

    public Presenca() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
