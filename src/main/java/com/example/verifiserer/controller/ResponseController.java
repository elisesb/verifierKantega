package com.example.verifiserer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verifisere")
public class ResponseController {

    @PostMapping("/callback")
    public String callBack(@RequestBody String requestBody){
        System.out.println("Request body" + requestBody);
        return "Callback successfully";

    }


}
