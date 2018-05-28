package com.example.alcra.silverhawksapp.entities;

/**
 * Created by alcra on 27/05/2018.
 */

public class Presenca {

    public static final String COLLECTION_PRESENCA = "presenca";
    public static final int P = 1;
    public static final int J = 2;
    public static final int F = 3;
    String userId;
    String name;
    String date;
    String local;
    String mes;
    String idChamada;
    int tipo;

    public String getIdChamada() {
        return idChamada;
    }

    public void setIdChamada(String idChamada) {
        this.idChamada = idChamada;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
