package com.example.verifiserer.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

@Component

public class NgrokConfig {

    @Value("${ngrok.authtoken}")
    private String ngrokAuthToken;

    public String getNgrokAuthToken() {
        return ngrokAuthToken;
    }
}

