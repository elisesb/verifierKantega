package com.example.verifiserer.controller;

import com.example.verifiserer.service.DiplomaSortService;
import com.example.verifiserer.service.ResponseService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/credentials")
public class CredentialController {
    private final ResponseService responseService;
    private final DiplomaSortService diplomaSortService;

    public CredentialController(ResponseService responseService, DiplomaSortService diplomaSortService) {
        this.responseService = responseService;
        this.diplomaSortService = diplomaSortService;
    }

}





