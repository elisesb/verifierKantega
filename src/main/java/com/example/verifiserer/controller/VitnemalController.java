package com.example.verifiserer.controller;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.KarakterRepository;
import com.example.verifiserer.repository.VitnemalRepository;
import com.example.verifiserer.service.DiplomaSortService;
import com.example.verifiserer.service.GetDiplomaDataService;
import com.example.verifiserer.service.GradeService;
import com.example.verifiserer.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/verifisere")
public class VitnemalController {

    private final GetDiplomaDataService getDiplomaDataService;
    private final ResponseService responseService;
    private final DiplomaSortService diplomaSortService;
    private final VitnemalRepository vitnemalRepository;
    private final KarakterRepository karakterRepository;
    public String respons;
    private boolean callbackReceived = false;
    private final GradeService gradeService;

    @Autowired
    public VitnemalController(GetDiplomaDataService getDiplomaDataService, ResponseService responseService, DiplomaSortService diplomaSortService, VitnemalRepository vitnemalRepository, KarakterRepository karakterRepository, GradeService gradeService) {
        this.getDiplomaDataService = getDiplomaDataService;
        this.responseService = responseService;
        this.diplomaSortService = diplomaSortService;
        this.vitnemalRepository = vitnemalRepository;
        this.karakterRepository = karakterRepository;
        this.gradeService = gradeService;
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
            e.printStackTrace();

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
