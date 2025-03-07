package com.example.verifiserer.controller;

import com.example.verifiserer.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verifisere")
public class ResponseController {

    String test;
    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService){
        this.responseService = responseService;
    }

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


    @GetMapping("/token")
    public String getToken() {
        return responseService.getJustToken(test);
    }




}
