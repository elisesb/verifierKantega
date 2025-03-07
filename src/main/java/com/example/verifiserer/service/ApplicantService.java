package com.example.verifiserer.service;
import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.model.Cv;
import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final CvRepository cvRepository;  // Repository for CV data
    private final DiplomaRepository diplomaRepository;  // Repository for Diploma data
    private final InstitutionRepository institutionRepository;  // Repository for Institution data
    private final KarakterRepository karakterRepository;


    public ApplicantService(ApplicantRepository applicantRepository, CvRepository cvRepository, DiplomaRepository diplomaRepository, InstitutionRepository institutionRepository, KarakterRepository karakterRepository) {
        this.applicantRepository = applicantRepository;
        this.diplomaRepository = diplomaRepository;
        this.institutionRepository = institutionRepository;
        this.cvRepository = cvRepository;
        this.karakterRepository = karakterRepository;

    }

    public Applicant saveApplicant(String fullName, String email, String phone, MultipartFile cv) throws IOException {
        // Handle the CV file if it's not empty
        String cvPath = null;
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
            cv.transferTo(file);  // Save the file to the specified path

            // Save the CV data into the CV table
            Cv cvEntity = new Cv();
            cvEntity.setCvPath(cvPath);

        /*// Handle QR code data (assuming you're saving the QR code data in the Diploma table)
        if (qrCodeData != null && !qrCodeData.isEmpty()) {
            Diploma diploma = new Diploma();
            diploma.setQrCodeData(qrCodeData);  // Assuming QR code is stored in diploma
            diplomaRepository.save(diploma); */

            // Create and save the applicant entity
            applicant = new Applicant();
            applicant.setName(fullName);
            applicant.setEmail(email);
            applicant.setPhone(phone);
            applicant.setCvPath(cvPath);  // Store the CV file path
            applicantRepository.save(applicant);
            // Set applicant_id in the CV entity
            cvEntity.setApplicant(applicant);
            // Save the CV entity with the reference to the applicant
            cvRepository.save(cvEntity);

        }
        return applicant;
    }
    public void deleteOldCvRecords() {
        // Get all CV records from the database
        List<Cv> cvList = cvRepository.findAll();

        // Loop through each CV record
        for (Cv cv : cvList) {
            File file = new File(cv.getCvPath()); // Get the file from the CV path

            // Check if the file exists
            if (!file.exists()) {
                // If the file doesn't exist, delete the record from the database
                cvRepository.delete(cv);
                System.out.println("Deleted CV with path: " + cv.getCvPath());
            } else {
                System.out.println("File still exists: " + cv.getCvPath());
            }
        }
    }


}

