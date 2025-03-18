package com.example.verifiserer.controller;


import com.example.verifiserer.dto.ApplicantDTO;
import com.example.verifiserer.repository.ApplicantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ApplicantDTO>> getAllApplicants() {
        List<ApplicantDTO> applicants = applicantRepository.findAll()
                .stream()
                .map(applicant -> new ApplicantDTO(
                        applicant.getName(),
                        applicant.getEmail(),
                        applicant.getPhone(),
                        applicant.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(applicants);
    }


}
