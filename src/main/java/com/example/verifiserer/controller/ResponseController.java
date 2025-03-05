package com.example.verifiserer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verifisere")
public class ResponseController {

    String test;

    @PostMapping("/callback")
    public String callBack(@RequestBody String requestBody){
        test = requestBody;
        System.out.println("Request body" + requestBody);
        return "Callback successfully";
    }

    @GetMapping("/testerCallbacken")
    public String getTestValue() {
        return test;
    }

    @GetMapping("/forkorttest")
    public String getForkortelse(String test){
        int startIndex = test.indexOf("ey");
        int endIndex = test.indexOf("&state");

        if (startIndex != -1 && endIndex != -1) {
            return test.substring(startIndex, endIndex);
        }

        return null; // Eller håndter feilen på en annen måte
    }

}
