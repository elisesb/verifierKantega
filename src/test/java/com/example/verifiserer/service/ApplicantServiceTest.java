package com.example.verifiserer.service;

import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.model.Cv;
import com.example.verifiserer.repository.ApplicantRepository;
import com.example.verifiserer.repository.CvRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private CvRepository cvRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ApplicantService applicantService;

    @Test
    void testSaveApplicant_withValidData_shouldSaveApplicantAndCv() throws IOException {
        // Arrange
        String name = "Elise";
        String email = "elise@example.com";
        String phone = "12345678";
        String fileName = "cv_elise.pdf";

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        // Act
        Applicant result = applicantService.saveApplicant(name, email, phone, multipartFile);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(phone, result.getPhone());

        verify(applicantRepository).save(any(Applicant.class));
        verify(cvRepository).save(any(Cv.class));
        verify(multipartFile).transferTo(any(File.class));
    }

    @Test
    void testDeleteOldCvRecords_shouldDeleteIfFileNotExists() {
        // Arrange
        Cv cv = new Cv();
        cv.setCvPath("some/nonexistent/path.pdf");

        when(cvRepository.findAll()).thenReturn(List.of(cv));


        // Act
        applicantService.deleteOldCvRecords();

        // Assert
        verify(cvRepository).delete(cv);
    }
}
