package com.example.verifiserer.controller;

import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.repository.ApplicantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FormController {
    private final ApplicantRepository applicantRepository;

    public FormController(ApplicantRepository applicantRepository){
        this.applicantRepository = applicantRepository;
    }

    @PostMapping("/submit-application")
    public ResponseEntity<?> submitApplication(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone) //,
            /*@RequestParam("cv") MultipartFile cv)*/{


        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Alle feltene er obligatoriske"));
        }

        try {
            /*if (cv != null && !cv.isEmpty()) {
                String fileName = cv.getOriginalFilename();
                cv.transferTo(new java.io.File("/path/to/save/" + fileName));
            }*/
            Applicant applicant = new Applicant();
            applicant.setName(fullName);
            applicant.setEmail(email);
            applicant.setPhone(phone);

            // Lagre søkeren i databasen
            applicantRepository.save(applicant);

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
