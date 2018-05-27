package com.example.alcra.silverhawksapp.entities;

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
    private Tipo tipo;
    private Address address;
    private String number;
    private String posicao;
    private Boolean isActive;

    public Atleta() {
    }

    public String getNameComp() {
        return nameComp;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
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

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public enum Tipo{
        ATLETA(0),
        CT(1);

        int tipo;

        Tipo(int tipo) {
            this.tipo = tipo;
        }
    }


}
