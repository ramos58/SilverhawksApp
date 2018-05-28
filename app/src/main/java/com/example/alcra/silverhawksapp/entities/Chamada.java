package com.example.alcra.silverhawksapp.entities;

/**
 * Created by alcra on 27/05/2018.
 */

public class Chamada {

    public static final String COLLECTION_CHAMADA  = "chamada";
    private String date;
    private String local;
    private Tipo tipo;
    private String chamadaId;

    public Chamada(String date, String local, Tipo tipo) {
        this.date = date;
        this.local = local;
        this.tipo = tipo;
    }

    public Chamada() {
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public enum Tipo{
        PRATICO(1),
        TEORICO(2);

        int tipo;

        Tipo(int tipo) {
            this.tipo = tipo;
        }
    }
}
