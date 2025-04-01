package com.example.verifiserer.controller;

import com.example.verifiserer.dto.VitnemalResponseDTO;
import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.KarakterRepository;
import com.example.verifiserer.repository.VitnemalRepository;
import com.example.verifiserer.service.DiplomaSortService;
import com.example.verifiserer.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/verifisere")
public class ResponseController {
    //ønsker å teste om dataen blir slettet


    private final ResponseService responseService;
    private final DiplomaSortService diplomaSortService;
    private final VitnemalRepository vitnemalRepository;
    private final KarakterRepository karakterRepository;
    public String respons;


    @Autowired
    public ResponseController(ResponseService responseService, DiplomaSortService diplomaSortService, VitnemalRepository vitnemalRepository, KarakterRepository karakterRepository) {
        this.responseService = responseService;
        this.diplomaSortService = diplomaSortService;
        this.vitnemalRepository = vitnemalRepository;
        this.karakterRepository = karakterRepository;
    }

    @PostMapping("/callback")
    public String callBack(@RequestBody String requestBody){
        try {
            respons = requestBody;
            List<Map<String, Object>> karakterData =
                    diplomaSortService.hentKarakterer(diplomaSortService.getStringDiploma(requestBody));

            Map<String, Object> personligData =
                    diplomaSortService.hentPersonInfo(diplomaSortService.getStringDiploma(requestBody));
            Long personId= diplomaSortService.savePersonligData(personligData);

            String karakterResultat = diplomaSortService.saveKarakterData(karakterData, personId);

            System.out.println("Karakterer: " + karakterResultat + "\nPersonlig data: " + personId);


        } catch (Exception e) {
            e.printStackTrace();

        }
        return "Callback mottatt";

    }
    @GetMapping("/fromDatabase/{id}")
    public ResponseEntity<VitnemalResponseDTO> fromDatabase(@PathVariable Long id) {
        Optional<Vitnemal> vitnemalperson = vitnemalRepository.findById(id);

        if (vitnemalperson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        List<Karakter> personligKarakterliste = karakterRepository.findAll()
                .stream()
                .filter(karakter -> id.equals(karakter.getVitnemalId()))
                .collect(Collectors.toList());
        VitnemalResponseDTO response = new VitnemalResponseDTO(vitnemalperson.get(), personligKarakterliste);

        return ResponseEntity.ok(response);
    }




    @GetMapping("/testerCallbacken")
    public String getTestValue() {
        return respons;
    }

    @GetMapping("/token")
    public String getToken() {
        return responseService.getJustToken(respons);
    }

    @GetMapping("/token1")
    public String getToken1(){
        return responseService.getToken1(respons);
    }

    @GetMapping("/token2")
    public String getToken2(){
        return responseService.getToken2(respons);
    }

    @GetMapping("/tokenHead")
    public String getTokenHead() {
        try {
            return responseService.getTokenHeader(respons);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/tokenPayload")
    public String getTokenPayloaden() {
        try {
            return responseService.getTokenPayload(respons);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/extractedToken")
    public String getExtractedToken(){
        try {
            return responseService.extractToken(responseService.getTokenPayload(respons));
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

    @GetMapping("/extracedToken2")
    public String getExtractedToken2() {

        String token = responseService.extractToken(responseService.getTokenPayload(respons));
        try {
            return responseService.getTokenPayload2(token);
        } catch (Exception e) {
            return "Feil ved henting av token header: \"" + e;
        }
    }

}
