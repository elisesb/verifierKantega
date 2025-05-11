package com.example.verifiserer.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class ResponseService {



    public String getJustToken(String text){
        int start = text.indexOf("vp_token=") + 9;
        int end = text.indexOf("&", start);

        return (end == -1) ? text.substring(start) : text.substring(start, end);

    }


    public String getTokenPayload(String text){
        String token = getJustToken(text);

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





}
