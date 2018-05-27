package com.example.alcra.silverhawksapp.entities;

/**
 * Created by alcra on 27/05/2018.
 */

public class Presenca{

    public static final int P = 0;
    public static final int J = 1;
    public static final int F = 2;
    String nome;
    int tipo;

    public Presenca(String nome, int tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
