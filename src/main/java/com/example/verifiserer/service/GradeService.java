package com.example.verifiserer.service;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.KarakterRepository;
import com.example.verifiserer.repository.VitnemalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeService {

    private final KarakterRepository karakterRepository;
    private final VitnemalRepository vitnemalRepository;

    @Autowired
    public GradeService(KarakterRepository karakterRepository, VitnemalRepository vitnemalRepository) {
        this.karakterRepository = karakterRepository;
        this.vitnemalRepository = vitnemalRepository;
    }

    public List<Karakter> getGradesByDiplomaId(Long diplomaId) {
        List<Karakter> myKarakterList = new ArrayList<>();
        for (Karakter karakter : karakterRepository.findAll()) {
            if (karakter.getVitnemalId().equals(diplomaId)) {
                myKarakterList.add(karakter);

            }
        }
        return myKarakterList;
    }

    public Map<Vitnemal,List<Karakter>> getAllGradesMappedByDiploma(){
        Map<Vitnemal, List<Karakter>> gradesMap = new HashMap<>();
        List<Vitnemal> vitnemalList = vitnemalRepository.findAll();
        for (Vitnemal vitnemal : vitnemalList) {
            gradesMap.put(vitnemal, getGradesByDiplomaId(vitnemal.getId()));
        }
        return gradesMap;
    }


    public double getsumDiploma(Long diplomaId) {
        List<Karakter> myKarakterList = getGradesByDiplomaId(diplomaId);
        double sum = 0;
        int poeng = 0;
        for (Karakter karakter : myKarakterList) {
            if (karakter.getKarakter().equals("A")) {
                sum += 50;
                poeng = poeng + karakter.getPoeng();

            }
            if (karakter.getKarakter().equals("B")) {
                sum += 40;
                poeng = poeng + karakter.getPoeng();
            }
            if (karakter.getKarakter().equals("C")) {
                sum += 30;
                poeng = poeng + karakter.getPoeng();
            }
            if (karakter.getKarakter().equals("D")) {
                sum += 20;
                poeng = poeng + karakter.getPoeng();
            }
            if (karakter.getKarakter().equals("E")) {
                sum += 10;
                poeng = poeng + karakter.getPoeng();
            }



        }
        double karaktersum = sum / poeng;
        return Math.round(karaktersum * 100.0) / 100.0;
    }

    public List<Double> getAllSumDiplomas() {
        List<Vitnemal> vitnemaler = vitnemalRepository.findAll();
        List<Double> sums = new ArrayList<>();

        for (Vitnemal vitnemal : vitnemaler) {
            Long diplomaId = vitnemal.getId();
            List<Karakter> myKarakterList = getGradesByDiplomaId(diplomaId);
            double sum = 0;
            int poeng = 0;
            for (Karakter karakter : myKarakterList) {
                if ("A".equals(karakter.getKarakter())) {
                    sum += (5* karakter.getPoeng());
                    poeng = poeng + karakter.getPoeng();
                }
                if ("B".equals(karakter.getKarakter())) {
                    sum += (4* karakter.getPoeng());
                    poeng = poeng + karakter.getPoeng();
                }
                if ("C".equals(karakter.getKarakter())) {
                    sum += (3* karakter.getPoeng());
                    poeng = poeng + karakter.getPoeng();
                }
                if ("D".equals(karakter.getKarakter())) {
                    sum += (2* karakter.getPoeng());
                    poeng = poeng + karakter.getPoeng();
                }
                if ("E".equals(karakter.getKarakter())) {
                    sum += (karakter.getPoeng());
                    poeng = poeng + karakter.getPoeng();
                }

            }
            double karaktersum = poeng > 0 ? sum / poeng : 0;
            double avrundetSum = Math.round(karaktersum * 100.0) / 100.0;
            sums.add(avrundetSum);
        }

        return sums;
    }

}
