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

    

}
