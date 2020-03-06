package br.com.zup.zupclientservices.ui;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class ClientResource extends RepresentationModel<ClientResource> {

    private long id;

    private String name;

    private String cpf;

    private LocalDate birthDate;

    private String postalCode;

    private int number;

    private String compĺement;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCompĺement() {
        return compĺement;
    }

    public void setCompĺement(String compĺement) {
        this.compĺement = compĺement;
    }
}
