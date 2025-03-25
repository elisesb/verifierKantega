package com.example.verifiserer.dto;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;

import java.util.List;
import java.util.Optional;

public class VitnemalResponseDTO {
    private Vitnemal vitnemal;
    private List<Karakter> karakterer;

    public VitnemalResponseDTO(Vitnemal vitnemal, List<Karakter> karakterer) {
        this.vitnemal = vitnemal;
        this.karakterer = karakterer;
    }

    public Vitnemal getVitnemal() {
        return vitnemal;
    }

    public List<Karakter> getKarakterer() {
        return karakterer;
    }
}

