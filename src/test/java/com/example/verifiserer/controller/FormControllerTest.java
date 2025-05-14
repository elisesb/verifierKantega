package com.example.verifiserer.controller;

import com.example.verifiserer.service.ApplicantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormControllerTest {

    @InjectMocks
    private FormController formController;

    @Mock
    private ApplicantService applicantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitApplication_Success() throws Exception {
        // Arrange
        String fullName = "Ola Nordmann";
        String email = "ola@example.com";
        String phone = "12345678";
        MockMultipartFile cv = new MockMultipartFile("cv", "cv.pdf", "application/pdf", "Fake CV content".getBytes());

        // Act
        ResponseEntity<?> response = formController.submitApplication(fullName, email, phone, cv);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Søknad mottatt", response.getBody());

        // Verify
        verify(applicantService, times(1)).saveApplicant(fullName, email, phone, cv);
    }

    @Test
    void testSubmitApplication_MissingFields() throws IOException {
        // Arrange
        MockMultipartFile cv = new MockMultipartFile("cv", "cv.pdf", "application/pdf", "Fake CV content".getBytes());

        // Act
        ResponseEntity<?> response = formController.submitApplication("", "test@example.com", "12345678", cv);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof FormController.ErrorResponse);
        assertEquals("Alle feltene er obligatoriske", ((FormController.ErrorResponse) response.getBody()).getMessage());

        verify(applicantService, never()).saveApplicant(anyString(), anyString(), anyString(), any(MultipartFile.class));verify(applicantService, never()).saveApplicant(anyString(), anyString(), anyString(), any(MultipartFile.class));
    }

    @Test
    void testSubmitApplication_InternalServerError() throws Exception {
        // Arrange
        String fullName = "Ola Nordmann";
        String email = "ola@example.com";
        String phone = "12345678";
        MockMultipartFile cv = new MockMultipartFile("cv", "cv.pdf", "application/pdf", "Fake CV content".getBytes());

        doThrow(new RuntimeException("DB error")).when(applicantService).saveApplicant(fullName, email, phone, cv);

        // Act
        ResponseEntity<?> response = formController.submitApplication(fullName, email, phone, cv);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof FormController.ErrorResponse);
        assertEquals("Noe gikk galt ved opplasting av CV", ((FormController.ErrorResponse) response.getBody()).getMessage());
    }
}

