package com.example.verifiserer.service;
import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.model.Cv;
import com.example.verifiserer.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Denne klassen er en tjenestekomponent som håndterer logikken for behandling av søknader og CV-er.
 * Den integrerer med `ApplicantRepository` og `CvRepository` for å lagre og administrere søker- og CV-data.

 * Funksjonalitet:
 * - Lagrer søkerens informasjon og tilknyttede CV-filer på serveren.
 * - Oppretter nødvendige mapper for å lagre filene hvis de ikke finnes.
 * - Håndterer sletting av gamle CV-poster fra databasen hvis de tilknyttede filene ikke lenger eksisterer.

 * Bruker Spring's `@Service`-annotasjon for å indikere at det er en tjenestekomponent.
 */

@Service
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final CvRepository cvRepository;



    public ApplicantService(ApplicantRepository applicantRepository, CvRepository cvRepository) {
        this.applicantRepository = applicantRepository;

        this.cvRepository = cvRepository;


    }

    public Applicant saveApplicant(String fullName, String email, String phone, MultipartFile cv) throws IOException {
        String cvPath;
        Applicant applicant = null;
        if (cv != null && !cv.isEmpty()) {
            String fileName = cv.getOriginalFilename();


            String folderPath = "C:/cvForKantega/";
            File directory  = new File(folderPath);

            if(!directory.exists()){
                boolean dirCreated = directory.mkdir();

                if(dirCreated){
                    System.out.println("Directory created successfully" + folderPath);
                }else{
                    System.out.println("Failed to create directory");
            }
            }

            cvPath = folderPath + fileName;
            File file = new File(cvPath);
            cv.transferTo(file);


            Cv cvEntity = new Cv();
            cvEntity.setCvPath(cvPath);

            applicant = new Applicant();
            applicant.setName(fullName);
            applicant.setEmail(email);
            applicant.setPhone(phone);
            applicant.setCvPath(cvPath);
            applicantRepository.save(applicant);
            cvEntity.setApplicant(applicant);
            cvRepository.save(cvEntity);

        }
        return applicant;
    }
    public void deleteOldCvRecords() {
        List<Cv> cvList = cvRepository.findAll();

        for (Cv cv : cvList) {
            File file = new File(cv.getCvPath());


            if (!file.exists()) {
                cvRepository.delete(cv);
                System.out.println("Deleted CV with path: " + cv.getCvPath());
            } else {
                System.out.println("File still exists: " + cv.getCvPath());
            }
        }
    }
}

