package com.example.verifiserer.service;

import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.VitnemalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetDiplomaDataService {


    private final VitnemalRepository vitnemalRepository;

    @Autowired
    public GetDiplomaDataService(VitnemalRepository vitnemalRepository) {
        this.vitnemalRepository = vitnemalRepository;
    }

    public Vitnemal getVitnemalById(Long id) {
        Optional<Vitnemal> vitnemal = vitnemalRepository.findById(id);
        return vitnemal.orElse(null);
    }

    public List<Integer> getAllSumDiplomas() {
        List<Vitnemal> vitnemaler = vitnemalRepository.findAll();
        return vitnemaler.stream()
                .map(Vitnemal::getSum)
                .collect(Collectors.toList());
    }

    public List<Vitnemal> getAllVitnemaler() {
        return vitnemalRepository.findAll();
    }
}
