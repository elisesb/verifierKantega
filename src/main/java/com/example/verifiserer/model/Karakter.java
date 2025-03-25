package com.example.verifiserer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "karakter")
public class Karakter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fag;

    @Column(nullable = false)
    private String emnekode;

    @Column(nullable = false)
    private String karakter;

    @Column(nullable = false)
    private int poeng;

    @Column(nullable = false)
    private int arstall;

    @ManyToOne
    @JoinColumn(name = "diploma_id")
    private Vitnemal vitnemal;

    // Konstruktør med argumenter
    public Karakter(String fag, String emnekode, String karakter, int poeng, int arstall) {
        this.fag = fag;
        this.emnekode = emnekode;
        this.karakter = karakter;
        this.poeng = poeng;
        this.arstall = arstall;
    }

    public Karakter() {}

    // Getters og Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFag() {
        return fag;
    }

    public void setFag(String fag) {
        this.fag = fag;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public String getKarakter() {
        return karakter;
    }

    public void setKarakter(String karakter) {
        this.karakter = karakter;
    }

    public int getPoeng() {
        return poeng;
    }

    public void setPoeng(int poeng) {
        this.poeng = poeng;
    }

    public int getArstall() {
        return arstall;
    }

    public void setArstall(int arstall) {
        this.arstall = arstall;
    }

    public Vitnemal getVitnemal() {
        return vitnemal;
    }

    public void setVitnemal(Vitnemal vitnemal) {
        this.vitnemal = vitnemal;
    }
}
