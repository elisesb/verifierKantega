package com.example.verifiserer.controller;

import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3002")
public class FormController {

    private final ApplicantService applicantService;

    // Constructor injection for ApplicantService
    @Autowired
    public FormController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/submit-application")
    public ResponseEntity<?> submitApplication(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("cv") MultipartFile cv) {

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Alle feltene er obligatoriske"));
        }

        try {
            // Delegate to the service for saving the applicant and handling the CV
            applicantService.saveApplicant(fullName, email, phone, cv);

            return ResponseEntity.ok("Søknad mottatt");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Noe gikk galt ved opplasting av CV"));
        }
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
