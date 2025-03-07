package com.example.verifiserer.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    // This endpoint serves the file from the C:/cvForKantega/ directory
    @GetMapping("/cv/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        // Specify the directory where files are stored
        String directoryPath = "C:/cvForKantega/";

        // Create a file path based on the directory and filename
        FileSystemResource file = new FileSystemResource(directoryPath + filename);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set the Content-Disposition header to indicate a downloadable file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(file);
    }
}
