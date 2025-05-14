package com.example.verifiserer.controller;

import com.example.verifiserer.dto.ApplicantDTO;
import com.example.verifiserer.model.Applicant;
import com.example.verifiserer.repository.ApplicantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicantControllerTest {

    @Test
    void getAllApplicants_ShouldMapEntitiesToDTOsCorrectly() {
        // Arrange
        ApplicantRepository mockRepo = mock(ApplicantRepository.class);
        ApplicantController controller = new ApplicantController(mockRepo);

        Applicant applicant = new Applicant();
        applicant.setName("Elise");
        applicant.setEmail("elise@example.com");
        applicant.setPhone("12345678");
        applicant.setCvPath("C:/cvFolder/elise_cv.pdf");

        when(mockRepo.findAll()).thenReturn(List.of(applicant));

        // Act
        ResponseEntity<List<ApplicantDTO>> response = controller.getAllApplicants();
        List<ApplicantDTO> result = response.getBody();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ApplicantDTO dto = result.get(0);
        assertEquals("Elise", dto.getName());
        assertEquals("elise@example.com", dto.getEmail());
        assertEquals("12345678", dto.getPhone());
        assertEquals("elise_cv.pdf", dto.getCvPath());
    }
}
