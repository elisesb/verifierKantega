package com.example.verifiserer.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Denne klassen fungerer som en REST-kontroller for å håndtere forespørsler om nedlasting eller visning av filer.
 * Spesielt henter den filer fra katalogen "C:/cvForKantega/" basert på filnavnet som sendes i forespørselen.

 * Endepunktet "/cv/{filename}":
 * - Tar imot et filnavn som en path-variabel.
 * - Verifiserer om filen eksisterer i den spesifiserte katalogen.
 * - Returnerer filen som en HTTP-respons med passende HTTP-headere for nedlasting eller inline-visning.

 * Funksjonaliteten er nyttig for servering av PDF-filer, som for eksempel CV-er, og sikrer at bare eksisterende filer blir returnert.
 */

@RestController
public class FileController {

    @GetMapping("/cv/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        String directoryPath = "C:/cvForKantega/";


        FileSystemResource file = new FileSystemResource(directoryPath + filename);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(file);
    }
}
