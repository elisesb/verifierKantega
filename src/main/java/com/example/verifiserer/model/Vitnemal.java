package com.example.verifiserer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vitnemal")
public class Vitnemal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String navn;

    @Column(nullable = false, unique = true)
    private String fodselsnummer;

    @Column(nullable = false)
    private boolean fullfort;

    @Column(nullable = false)
    private String utdanningsnavn;

    @Column(nullable = false)
    private String grad;

    @Column(nullable = false)
    private int sum;

    // Konstruktører
    public Vitnemal() {}

    public Vitnemal(String navn, String fodselsnummer, boolean fullfort, String utdanningsnavn, String grad, int sum) {
        this.navn = navn;
        this.fodselsnummer = fodselsnummer;
        this.fullfort = fullfort;
        this.utdanningsnavn = utdanningsnavn;
        this.grad = grad;
        this.sum = sum;
    }

    // Getters og Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getFodselsnummer() {
        return fodselsnummer;
    }

    public void setFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
    }

    public boolean isFullfort() {
        return fullfort;
    }

    public void setFullfort(boolean fullfort) {
        this.fullfort = fullfort;
    }

    public String getUtdanningsnavn() {
        return utdanningsnavn;
    }

    public void setUtdanningsnavn(String utdanningsnavn) {
        this.utdanningsnavn = utdanningsnavn;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
