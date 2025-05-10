package com.example.verifiserer.controller;

import com.example.verifiserer.dto.ApplicantDTO;
import com.example.verifiserer.repository.ApplicantRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api")
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }


    @GetMapping("/applicants")
    public ResponseEntity<List<ApplicantDTO>> getAllApplicants() {
        List<ApplicantDTO> applicants = applicantRepository.findAll()
                .stream()
                .map(applicant -> {
                    String fileName = new File(applicant.getCvPath()).getName();
                    return new ApplicantDTO(
                            applicant.getName(),
                            applicant.getEmail(),
                            applicant.getPhone(),
                            applicant.getCreatedAt(),
                            fileName
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(applicants);
    }
}
