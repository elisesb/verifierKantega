package com.example.verifiserer.controller;

import com.example.verifiserer.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
        return "Successfully";
    }

    @GetMapping("/testerCallbacken")
    public String getTestValue() {
        return test;
    }

    @GetMapping("/token")
    public String getToken() {
        return responseService.getJustToken(test);
    }

    @GetMapping("/token1")
    public String getToken1(){
        return responseService.getToken1(test);
    }

    @GetMapping("/token2")
    public String getToken2(){
        return responseService.getToken2(test);
    }

    @GetMapping("/tokenHead")
    public String getTokenHead() {
        try {
            return responseService.getTokenHeader(test);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/tokenPayload")
    public String getTokenPayloaden() {
        try {
            return responseService.getTokenPayload(test);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/extractedToken")
    public String getExtractedToken(){
        try {
            return responseService.extractToken(responseService.getTokenPayload(test));
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/extracedToken2")
    public String getExtractedToken2(){

        String token = responseService.extractToken(responseService.getTokenPayload(test));
        try {
            return responseService.getTokenPayload2(token);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/extracedSd")
    public List<String> getExtractedSd(){

        String JSON  = responseService.getTokenPayload2(responseService.extractToken(responseService.getTokenPayload(test)));
        try {
            return ResponseService.extractSd(JSON);
        } catch (Exception e) {
            return Collections.singletonList("Feil ved henting av token header: \"" + e);
        }
    }

    @GetMapping("/test")
    public String testEnEd() {
        try {
            return responseService.encrypt();
        } catch (NullPointerException e) {
            return "Feil: responseService eller input er null!";
        } catch (SecurityException e) {
            return "Feil: Krypteringsfeil - sikkerhetsproblem!";
        } catch (Exception e) {
            return "Feil: Uventet feil oppstod under kryptering: " + e.getMessage();
        }
    }

}
