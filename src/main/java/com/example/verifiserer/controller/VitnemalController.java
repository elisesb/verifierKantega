package com.example.verifiserer.controller;

import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.service.GetDiplomaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VitnemalController {

    private final GetDiplomaDataService getDiplomaDataService;

    @Autowired
    public VitnemalController(GetDiplomaDataService getDiplomaDataService) {
        this.getDiplomaDataService = getDiplomaDataService;
    }


    @GetMapping("/vitnemal")
    public ResponseEntity<List<Vitnemal>> getAllVitnemal() {
        List<Vitnemal> vitnemaler = getDiplomaDataService.getAllVitnemaler();
        return ResponseEntity.ok(vitnemaler);
    }

    @GetMapping("/poengsum")
    public ResponseEntity<List<Integer>> getAllSumDiplomas() {
        try {
            List<Integer> sums = getDiplomaDataService.getAllSumDiplomas();
            return ResponseEntity.ok(sums);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

/*@RestController
@RequestMapping("/api")
public class VitnemalController {

    private final GetDiplomaDataService getDiplomaDataService;

    @Autowired
    public VitnemalController(GetDiplomaDataService getDiplomaDataService) {
        this.getDiplomaDataService = getDiplomaDataService;
    }


    @GetMapping("/vitnemal")
    public ResponseEntity<Vitnemal> getVitnemal(@PathVariable Long id) {
        Vitnemal vitnemal = getDiplomaDataService.getVitnemalById(id);
        if (vitnemal != null) {
            return ResponseEntity.ok(vitnemal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sum")
    public ResponseEntity<Integer> getSumDiploma(@PathVariable Long id) {
        try {
            int sum = getDiplomaDataService.getsumDiploma(id);
            return ResponseEntity.ok(sum);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Hvis vitnemal ikke finnes
        }
    }
}*/
