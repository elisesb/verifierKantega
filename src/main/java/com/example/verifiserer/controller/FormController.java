package com.example.verifiserer.controller;


import com.example.verifiserer.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Denne klassen fungerer som en REST-kontroller for å håndtere innsending av søknader via et API-endepunkt.
 * Den tar imot data fra et HTML-skjema eller en annen klientapplikasjon og lagrer søkerens informasjon ved å bruke ApplicantService.

 * Funksjonalitet:
 * - Endepunktet "/api/submit-application" lar klienten sende inn søknadsdata inkludert navn, e-post, telefonnummer og CV (som en fil).
 * - Validerer at ingen av de obligatoriske feltene er tomme.
 * - Håndterer både vellykkede opplastinger og feiltilfeller (f.eks. serverfeil).

 * Klassen inkluderer også en intern ErrorResponse-klasse for å returnere feilmeldinger i et standardisert format.
 */

@RestController
@RequestMapping("/api")
public class FormController {

    private final ApplicantService applicantService;

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
            return ResponseEntity.badRequest().body("Alle feltene er obligatoriske");
        }

        try {
            applicantService.saveApplicant(fullName, email, phone, cv);

            return ResponseEntity.ok("Søknad mottatt");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Noe gikk galt ved opplasting av CV -"+ e.getMessage());
        }
    }

}
