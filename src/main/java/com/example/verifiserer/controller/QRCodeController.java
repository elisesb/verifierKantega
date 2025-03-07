package com.example.verifiserer.controller;

import com.example.verifiserer.service.QRCodeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api")
public class QRCodeController {
// slettes hvis ikke den brukes
    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping("/generate-qr-code")
    public String generateQRCode() {
        try {
            return qrCodeService.sendPostRequestAndGenerateQRCode();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating QR code";
        }
    }
}

