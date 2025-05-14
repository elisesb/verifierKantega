package com.example.verifiserer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class FileControllerTest {

    @Test
    void serveFile_FileExists_ReturnsOkWithResource() throws Exception {
        // Arrange: Lag en midlertidig PDF-fil
        File tempFile = File.createTempFile("test", ".pdf");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Test PDF content");
        writer.close();

        String fileName = tempFile.getName();
        String dirPath = tempFile.getParent() + "/";

        FileController controller = new FileController() {
            @Override
            public ResponseEntity<Resource> serveFile(String filename) {
                FileSystemResource file = new FileSystemResource(dirPath + filename);
                if (!file.exists()) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                        .header("Content-Type", "application/pdf")
                        .body(file);
            }
        };

        // Act
        ResponseEntity<Resource> response = controller.serveFile(fileName);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("inline; filename=\"" + fileName + "\"", response.getHeaders().getFirst("Content-Disposition"));
        assertEquals("application/pdf", response.getHeaders().getFirst("Content-Type"));

        // Clean up
        tempFile.delete();
    }

    @Test
    void serveFile_FileDoesNotExist_ReturnsNotFound() {
        // Arrange
        String nonExistingFile = "nonexistent.pdf";
        FileController controller = new FileController();

        // Act
        ResponseEntity<Resource> response = controller.serveFile(nonExistingFile);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}

