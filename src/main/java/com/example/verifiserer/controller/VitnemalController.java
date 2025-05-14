package com.example.verifiserer.controller;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.service.DiplomaSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/verifisere")
public class VitnemalController {

    private final DiplomaSortService diplomaSortService;
    public String respons;
    private boolean callbackReceived = false;


    @Autowired
    public VitnemalController(DiplomaSortService diplomaSortService) {

        this.diplomaSortService = diplomaSortService;


    }

    @PostMapping("/callback")
    public String callBack(@RequestBody String requestBody){
        callbackReceived = true;
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
            return e.getMessage();
        }
        return "Callback mottatt";

    }

    @GetMapping("/callbackStatus")
    public boolean isCallbackReceived() {
        return callbackReceived;
    }


    @GetMapping("/grades/{diplomaId}")
    public List<Karakter> getGradesByDiplomaId(@PathVariable Long diplomaId) {
        return diplomaSortService.getGradesByDiplomaId(diplomaId);
    }


    @GetMapping("/vitnemal/{id}")
    public ResponseEntity<Vitnemal> getVitnemal(@PathVariable Long id) {
        Vitnemal vitnemal = diplomaSortService.getVitnemalById(id);
        if (vitnemal != null) {
            return ResponseEntity.ok(vitnemal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
