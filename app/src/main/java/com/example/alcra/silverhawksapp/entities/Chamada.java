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
    private int totalAtletas;
    private int totalPresencas;
    private int totalJustificativas;
    private int totalFaltas;

    public Chamada(String date, String local, Tipo tipo) {
        this.date = date;
        this.local = local;
        this.tipo = tipo;
    }

    public Chamada() {
    }

    public String getChamadaId() {
        return chamadaId;
    }

    public void setChamadaId(String chamadaId) {
        this.chamadaId = chamadaId;
    }

    public int getTotalAtletas() {
        return totalAtletas;
    }

    public void setTotalAtletas(int totalAtletas) {
        this.totalAtletas = totalAtletas;
    }

    public int getTotalPresencas() {
        return totalPresencas;
    }

    public void setTotalPresencas(int totalPresencas) {
        this.totalPresencas = totalPresencas;
    }

    public int getTotalJustificativas() {
        return totalJustificativas;
    }

    public void setTotalJustificativas(int totalJustificativas) {
        this.totalJustificativas = totalJustificativas;
    }

    public int getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(int totalFaltas) {
        this.totalFaltas = totalFaltas;
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

    public int Porcent(int parcial, int total){
        return (parcial*100)/total;
    }
}
