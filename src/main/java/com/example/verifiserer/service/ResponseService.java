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

        JsonNode sdNode = rootNode.path("iss").path("vc").path("credentialSubject");


        if (sdNode.isArray()) {
            for (JsonNode sd : sdNode) {
                sdList.add(sd.asText());
            }
        }

        return sdList;
    }

    public String getNameFromToken(String token) {
        try {
            String payload = getTokenPayload2(token); // Henter payload
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);

            // Navigerer til vc -> credentialSubject -> navn
            return jsonNode.path("vc")
                    //.path("credentialSubject")
                    //.path("navn")
                    .asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Feil ved parsing av token";
        }
    }







}
