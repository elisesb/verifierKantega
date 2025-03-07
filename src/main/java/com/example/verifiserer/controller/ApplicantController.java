package com.example.verifiserer.controller;

import com.example.verifiserer.dto.ApplicantDTO;
import com.example.verifiserer.repository.ApplicantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;  // Import File class
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @GetMapping
    public ResponseEntity<List<ApplicantDTO>> getAllApplicants() {
        List<ApplicantDTO> applicants = applicantRepository.findAll()
                .stream()
                .map(applicant -> {
                    // Extract only the filename from the full file path
                    String fileName = new File(applicant.getCvPath()).getName();
                    return new ApplicantDTO(
                            applicant.getName(),
                            applicant.getEmail(),
                            applicant.getPhone(),
                            applicant.getCreatedAt(),
                            fileName  // Set the filename instead of the full path
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(applicants);
    }
}
