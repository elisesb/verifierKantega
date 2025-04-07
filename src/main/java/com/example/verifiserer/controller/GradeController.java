package com.example.verifiserer.controller;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.service.GetDiplomaDataService;
import com.example.verifiserer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class GradeController {

    private final GradeService gradeService;


    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;

    }

    @GetMapping("/grades")
    public ResponseEntity<Map<Vitnemal, List<Karakter>>> getAllGradesMapped() {
        try {
            Map<Vitnemal, List<Karakter>> gradesMap = gradeService.getAllGradesMappedByDiploma();
            return ResponseEntity.ok(gradesMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/karaktersum")
    public ResponseEntity<List<Double>> getAllSumDiplomas() {
        try {
            List<Double> sums = gradeService.getAllSumDiplomas();
            return ResponseEntity.ok(sums);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}

/*@RestController
public class GradeController {

    private final GradeService gradeService;


    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;

    }

    @GetMapping("/grades/{diplomaId}")
    public List<Karakter> getGradesByDiplomaId(@PathVariable Long diplomaId) {
        return gradeService.getGradesByDiplomaId(diplomaId);
    }
    @GetMapping("/karaktersum/{id}")
    public ResponseEntity<Double> getSumDiploma(@PathVariable Long id) {
        try {
            double sum = gradeService.getsumDiploma(id);
            return ResponseEntity.ok(sum);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}*/
