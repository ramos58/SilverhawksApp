package com.example.alcra.silverhawksapp.entities;

/**
 * Created by alcra on 15/05/2018.
 */

public class Users{


    protected String nome;
    protected String posicao;
    protected String numero;

    public Users(){

    }

    public Users(String nome, String numero, String posicao){
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getPosicao(){
        return posicao;
    }

    public void setPosicao(String posicao){
        this.posicao = posicao;
    }
}
