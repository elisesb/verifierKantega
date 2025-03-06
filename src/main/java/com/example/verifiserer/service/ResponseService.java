package com.example.verifiserer.service;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public String getJustToken(String text){
        return text.split("vp_token=")[1];

    }


}
