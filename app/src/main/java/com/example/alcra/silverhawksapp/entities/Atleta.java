package com.example.alcra.silverhawksapp.entities;

import java.util.List;

public class Atleta {

    public static final String COLLECTION_ATLETAS = "atletas";
    private String nameComp;
    private String firstName;
    private String lastName;
    private String rg;
    private String cpf;
    private String birthday;
    private String celPhone;
    private String email;
    private Address address;
    private String number;
    //private Posicao posicao;
    //private Unidade unidade;
    //private Boolean isActive;
    private String posicao;
    private String unidade;
    private String isActive;

    public Atleta() {
    }

    public String getNameComp() {
        return nameComp;
    }

    public void setNameComp(String nameComp) {
        this.nameComp = nameComp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

//    public Posicao getPosicao() {
//        return posicao;
//    }
//
//    public void setPosicao(Posicao posicao) {
//        this.posicao = posicao;
//    }
//
//    public Unidade getUnidade() {
//        return unidade;
//    }
//
//    public void setUnidade(Unidade unidade) {
//        this.unidade = unidade;
//    }
//
//    public Boolean getActive() {
//        return isActive;
//    }
//
//    public void setActive(Boolean active) {
//        isActive = active;
//    }
//
//    public enum Unidade{
//        ATAQUE(1),
//        DEFESA(2),
//        ST(3);
//
//        int unidade;
//
//        Unidade(int unidade) {
//            this.unidade = unidade;
//        }
//    }
//
//    public enum Posicao{
//        QB(1),
//        WR(2),
//        RB(3),
//        OL(4),
//        DL(5),
//        LB(6),
//        CB(7),
//        S(8),
//        P(9),
//        K(10),
//        LS(11),
//        H(12);
//
//        int posicao;
//
//        Posicao(int posicao) {
//            this.posicao = posicao;
//        }
//    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getActive() {
        return isActive;
    }

    public void setActive(String isActive) {
        this.isActive = isActive;
    }
}
