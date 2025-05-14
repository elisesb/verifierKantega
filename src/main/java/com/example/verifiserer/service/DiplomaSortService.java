package com.example.verifiserer.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.KarakterRepository;
import com.example.verifiserer.repository.VitnemalRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Denne klassen er en tjenestekomponent som håndterer logikk relatert til behandling av vitnemål og karakterer.
 * Den inkluderer funksjonalitet for å hente, prosessere og lagre vitnemål og karakterdata, samt dekode JWT-tokens og
 * hente informasjon fra JSON-strukturer.

 * Funksjonalitet:
 * - Henter vitnemål basert på ID.
 * - Henter karakterer tilknyttet et spesifikt vitnemål.
 * - Dekoder JWT-tokens for å hente nyttelast og prosessere vitnemålsdata.
 * - Henter og strukturerer personlig informasjon og karakterer fra JSON-data.
 * - Lagrer personlig data og karakterer i databasen.
 */
@Service
public class DiplomaSortService {

    private final ResponseService responseService;
    private final VitnemalRepository vitnemalRepository;
    private final KarakterRepository karakterRepository;


    @Autowired
    public DiplomaSortService(ResponseService responseService, VitnemalRepository vitnemalRepository, KarakterRepository karakterRepository) {
        this.responseService = responseService;
        this.vitnemalRepository = vitnemalRepository;
        this.karakterRepository = karakterRepository;
    }


    public Vitnemal getVitnemalById(Long id) {
        Optional<Vitnemal> vitnemal = vitnemalRepository.findById(id);
        return vitnemal.orElse(null);
    }

    public List<Karakter> getGradesByDiplomaId(Long diplomaId) {
        List<Karakter> myKarakterList = new ArrayList<>();
        for (Karakter karakter : karakterRepository.findAll()) {
            if (karakter.getVitnemalId().equals(diplomaId)) {
                myKarakterList.add(karakter);

            }
        }
        return myKarakterList;
    }

    public String getStringDiploma(String callback) {
        String token = responseService.extractToken(responseService.getTokenPayload(callback));
        try {

            JsonNode credentialSubject = getTokenPayload2(token).path("vc").path("credentialSubject");

            if (credentialSubject.isMissingNode()) {

                return ("Missing 'credentialSubject' field in payload");
            }
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(credentialSubject);
        } catch (Exception e) {
            return ("Error extracting credentialSubject: " + e.getMessage());
        }

    }
    public JsonNode getTokenPayload2(String token) throws Exception {
        DecodedJWT decodedJWT = JWT.decode(token);
        String payload = decodedJWT.getPayload();
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(decodedPayload);
    }

    public  Map<String, Object> hentPersonInfo(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(jsonString, new TypeReference<>() {});

            Map<String, Object> personInfo = new LinkedHashMap<>();
            personInfo.put("navn", data.get("navn"));
            personInfo.put("fodselsnummer", data.get("fodselsnummer"));
            personInfo.put("fullfort", data.get("fullfort"));
            personInfo.put("utdanningsnavn", data.get("utdanningsnavn"));
            personInfo.put("grad", data.get("grad"));
            personInfo.put("sum", data.get("sum"));

            return personInfo;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    public List<Map<String, Object>> hentKarakterer(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(jsonString, new TypeReference<>() {});

            List<Map<String, Object>> karakterListe = new ArrayList<>();
            List<Map<String, Object>> karakterer = (List<Map<String, Object>>) data.get("karakterer");

            for (Map<String, Object> karakter : karakterer) {
                Map<String, Object> karakterInfo = new LinkedHashMap<>();
                karakterInfo.put("fag", karakter.get("fag"));
                karakterInfo.put("emnekode", karakter.get("emnekode"));
                karakterInfo.put("karakter", karakter.get("karakter"));
                karakterInfo.put("poeng", karakter.get("poeng"));
                karakterInfo.put("arstall", karakter.get("arstall"));
                karakterListe.add(karakterInfo);
            }

            return karakterListe;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Long savePersonligData(Map<String, Object> personligListe){
        String navn = String.valueOf(personligListe.get("navn"));
        String fodselsnummer = String.valueOf(personligListe.get("fodselsnummer"));
        String fullfort = String.valueOf(personligListe.get("fullfort"));
        String utdanningsnavn = String.valueOf(personligListe.get("utdanningsnavn"));
        String grad = String.valueOf(personligListe.get("grad"));
        String sum = String.valueOf(personligListe.get("sum"));

        Vitnemal vitnemal = new Vitnemal(navn, fodselsnummer, Boolean.parseBoolean(fullfort), utdanningsnavn, grad, Integer.parseInt(sum));
        vitnemalRepository.save(vitnemal);


        return vitnemal.getId();


    }

    public String saveKarakterData(List<Map<String, Object>> karakterListe, Long id) {
        for (Map<String, Object> karakter : karakterListe) {
            String emnekode = String.valueOf(karakter.get("emnekode"));
            String karakterVitnemal = String.valueOf(karakter.get("karakter"));
            String arstall = String.valueOf(karakter.get("arstall"));
            String poeng = String.valueOf(karakter.get("poeng"));
            String fag = String.valueOf(karakter.get("fag"));
            Karakter karakter1 = new Karakter(id, fag, emnekode, karakterVitnemal, Integer.parseInt(poeng), Integer.parseInt(arstall));
            karakterRepository.save(karakter1);

        }
        return "ok";
    }

}
