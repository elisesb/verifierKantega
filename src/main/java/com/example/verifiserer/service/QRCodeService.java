package com.example.verifiserer.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


//Fjeres?
@Service
public class QRCodeService {

    private static final OkHttpClient client = new OkHttpClient();

    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public String sendPostRequestAndGenerateQRCode() throws IOException, WriterException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"requestByReference\": true,\n  \"presentationDefinitionId\": \"string\",\n  \"transactionData\": {}\n}");

        Request request = new Request.Builder()
                .url("https://demo-api.igrant.io/v3/config/digital-wallet/openid/sdjwt/verification/send")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer <TOKEN>")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                String qrCodeData = extractQRCodeData(responseBody);
                generateQRCodeImage(qrCodeData, 350, 350, "qr_code_image.png");
                return "QR code generated successfully!";
            } else {
                return "Request failed: " + response.message();
            }
        }
    }

    private String extractQRCodeData(String responseBody) {
        return responseBody;
    }
}
