package com.example.verifiserer.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitcoinj.core.Base58;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ResponseService {



    public String getJustToken(String text){
        int start = text.indexOf("vp_token=") + 9;
        int end = text.indexOf("&", start);

        return (end == -1) ? text.substring(start) : text.substring(start, end);

    }

    //kan fjernest
    public String getToken1(String text){
        String token = getJustToken(text);
        String[] tokens = token.split("\\.ey");

        return tokens[0];
    }

    //Kan fjernest
    public String getToken2(String text){
        String token = getJustToken(text);
        String[] tokens = token.split("\\.ey");

        return "ey" + tokens[1];
    }

    //kan fjernest, kanskje får vi bruk for denne?
    public String getTokenHeader(String text){
        String token = getJustToken(text);

        DecodedJWT decodedJWT = JWT.decode(token);
        return  decodedJWT.getHeader();

    }

    public String getTokenPayload(String text){
        String token = getJustToken(text);

        DecodedJWT decodedJWT = JWT.decode(token);
        String payload = decodedJWT.getPayload();
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    public String getTokenPayload2(String token){

        DecodedJWT decodedJWT = JWT.decode(token);
        String payload = decodedJWT.getPayload();
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }


    public String extractToken(String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);


        String token = jsonObject.getJSONObject("vp")
                .getJSONArray("verifiableCredential")
                .getString(0);

        return token;
    }


    public static List<String> extractSd(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        List<String> sdList = new ArrayList<>();

        JsonNode sdNode = rootNode.path("vc").path("credentialSubject").path("_sd");


        if (sdNode.isArray()) {
            for (JsonNode sd : sdNode) {
                sdList.add(sd.asText());
            }
        }

        return sdList;
    }


    public String encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 256-bit kryptering
        SecretKey secretKey = keyGenerator.generateKey();

        // Originale data (f.eks. din _sd-liste)
        String plaintext = "[\"TxWXz65yixT9VGSha3r5Ppd5mWqNDeiSypI8CWYGdck\", \"zUJfFVAoacx4yVbGj8QGCw4KxSC36-QeZ7ZDitYqcP0\"]";

        // Krypter data
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12]; // Initialiseringsvektor (må være unik for hver melding)
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Nøkkel: " + encodedKey);
        return encryptedText;


    }




}
