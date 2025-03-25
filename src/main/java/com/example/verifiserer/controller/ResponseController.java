package com.example.verifiserer.controller;

import com.example.verifiserer.service.DiplomaSortService;
import com.example.verifiserer.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/verifisere")
public class ResponseController {
    private final DiplomaSortService diplomaSortService;

    String test = "state=b14a67de-2988-4fcd-a495-40d1a625b869&presentation_submission=%7B%22id%22%3A%2270DE27E5-E9B5-4FA3-AA08-6B00B30166F3%22%2C%22definition_id%22%3A%22c477c079-59d9-45ee-bfa0-9f67a3ce04c9%22%2C%22descriptor_map%22%3A%5B%7B%22path%22%3A%22%24%22%2C%22id%22%3A%2208c9f5e2-8fe7-4eb5-9ae5-537d9deb6944%22%2C%22format%22%3A%22%22%2C%22path_nested%22%3A%7B%22id%22%3A%2208c9f5e2-8fe7-4eb5-9ae5-537d9deb6944%22%2C%22path%22%3A%22%24.vp.verifiableCredential%5B0%5D%22%2C%22format%22%3A%22jwt_vc%22%7D%7D%5D%7D&vp_token=eyJqd2siOnsia3R5IjoiRUMiLCJjcnYiOiJQLTI1NiIsIngiOiJCUEdZaUJjazN1ZG5fbTVaeHdJR1RpaG5WRjRfdUtXZmJjQ05PbEtnWVVJIiwieSI6ImtLLW9jM1RnQTMxTnZnZHlpNkprRFpHeWc0RXZhLWY4bEc0aHhRX2M1ZnZFIn0sImFsZyI6IkVTMjU2Iiwia2lkIjoiZGlkOmtleTp6MmRtekQ4MWNnUHg4VmtpN0pidXVNbUZZcldQZ1lveXR5a1VaM2V5cWh0MWo5S2JvMXRBdDJoRTE3OWRnYVljWVlNdmFwa3V2UVhHOU41aXo2SGVXZkJaNVBHWjg0a3I0NjlRM3YyU1B6cExGejV3cGJzZ1VCUUpmNkVUU1dVMnNKcGZHWEQzNXNHQXI4WjdEczVkR3c1N1ZBZ0RUSHJxU1dtS2V5RGhzRFV6SDQ0bkhhI3oyZG16RDgxY2dQeDhWa2k3SmJ1dU1tRllyV1BnWW95dHlrVVozZXlxaHQxajlLYm8xdEF0MmhFMTc5ZGdhWWNZWU12YXBrdXZRWEc5TjVpejZIZVdmQlo1UEdaODRrcjQ2OVEzdjJTUHpwTEZ6NXdwYnNnVUJRSmY2RVRTV1Uyc0pwZkdYRDM1c0dBcjhaN0RzNWRHdzU3VkFnRFRIcnFTV21LZXlEaHNEVXpINDRuSGEiLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3NDIzMDg4MzksImlzcyI6ImRpZDprZXk6ejJkbXpEODFjZ1B4OFZraTdKYnV1TW1GWXJXUGdZb3l0eWtVWjNleXFodDFqOUtibzF0QXQyaEUxNzlkZ2FZY1lZTXZhcGt1dlFYRzlONWl6NkhlV2ZCWjVQR1o4NGtyNDY5UTN2MlNQenBMRno1d3Bic2dVQlFKZjZFVFNXVTJzSnBmR1hEMzVzR0FyOFo3RHM1ZEd3NTdWQWdEVEhycVNXbUtleURoc0RVekg0NG5IYSIsIm5iZiI6MTc0MjMwNTIzOSwic3ViIjoiZGlkOmtleTp6MmRtekQ4MWNnUHg4VmtpN0pidXVNbUZZcldQZ1lveXR5a1VaM2V5cWh0MWo5S2JvMXRBdDJoRTE3OWRnYVljWVlNdmFwa3V2UVhHOU41aXo2SGVXZkJaNVBHWjg0a3I0NjlRM3YyU1B6cExGejV3cGJzZ1VCUUpmNkVUU1dVMnNKcGZHWEQzNXNHQXI4WjdEczVkR3c1N1ZBZ0RUSHJxU1dtS2V5RGhzRFV6SDQ0bkhhIiwiYXVkIjoiaHR0cHM6Ly9wb2lyb3QuaWQiLCJub25jZSI6ImI5ZTI4M2E0LWEwMDAtNDFmNy05OTYxLWZlZmI5YjcyYmQ0YyIsImp0aSI6InVybjp1dWlkOkU4MzBFQTE3LUYyQzYtNDRGQi04QUZFLTdEMzU2RDI5ODVFNCIsInZwIjp7ImhvbGRlciI6ImRpZDprZXk6ejJkbXpEODFjZ1B4OFZraTdKYnV1TW1GWXJXUGdZb3l0eWtVWjNleXFodDFqOUtibzF0QXQyaEUxNzlkZ2FZY1lZTXZhcGt1dlFYRzlONWl6NkhlV2ZCWjVQR1o4NGtyNDY5UTN2MlNQenBMRno1d3Bic2dVQlFKZjZFVFNXVTJzSnBmR1hEMzVzR0FyOFo3RHM1ZEd3NTdWQWdEVEhycVNXbUtleURoc0RVekg0NG5IYSIsImlkIjoidXJuOnV1aWQ6RTgzMEVBMTctRjJDNi00NEZCLThBRkUtN0QzNTZEMjk4NUU0IiwidHlwZSI6WyJWZXJpZmlhYmxlUHJlc2VudGF0aW9uIl0sInZlcmlmaWFibGVDcmVkZW50aWFsIjpbImV5SnJhV1FpT2lJeVVFSk5iV1l4YzJWRWFERXphRFJQZFhoc2RGOVFWa0kxWlhFMWRWYzJhWFpyWTJGR2VHUlZObkZOSWl3aWRIbHdJam9pU2xkVUlpd2lZV3huSWpvaVJWTXlOVFlpTENKcWQyc2lPbnNpYTNSNUlqb2lSVU1pTENKamNuWWlPaUpRTFRJMU5pSXNJbmdpT2lKak1WbzVOVTluVTJGdE1DMTBNMkp4WmpOSWRtZG1kR2RxTVRKdE5XNUxPV05MWDFCTlZURTJaMlJaSWl3aWVTSTZJazlUZGxsTFpqSmtja2t4ZVhvelJ5MXliMU5SZFVoM1gydHNkR0V0WkRSd09IRk9Relp2WDJaSVpFVWlmWDAuZXlKcGMzTWlPaUpvZEhSd2N6b3ZMM0J5YjNSdmRIbHdaUzFzYjIxcGJtOHRhWE56ZFdWeUxtRjZkWEpsZDJWaWMybDBaWE11Ym1WMEwyOXdaVzVwWkM5a2NtRm1kRjh4TkNJc0luTjFZaUk2SW1ScFpEcHJaWGs2ZWpKa2JYcEVPREZqWjFCNE9GWnJhVGRLWW5WMVRXMUdXWEpYVUdkWmIzbDBlV3RWV2pObGVYRm9kREZxT1V0aWJ6RjBRWFF5YUVVeE56bGtaMkZaWTFsWlRYWmhjR3QxZGxGWVJ6bE9OV2w2TmtobFYyWkNXalZRUjFvNE5HdHlORFk1VVROMk1sTlFlbkJNUm5vMWQzQmljMmRWUWxGS1pqWkZWRk5YVlRKelNuQm1SMWhFTXpWelIwRnlPRm8zUkhNMVpFZDNOVGRXUVdkRVZFaHljVk5YYlV0bGVVUm9jMFJWZWtnME5HNUlZU0lzSW5aaklqcDdJblI1Y0dVaU9sc2lWbVZ5YVdacFlXSnNaVU55WldSbGJuUnBZV3dpTENKMmFYUnVaVzFoYkNKZExDSmpjbVZrWlc1MGFXRnNVM1ZpYW1WamRDSTZleUp1WVhadUlqb2lTbTlvYmlCRWIyVWlMQ0puY21Ga0lqb2lUV0Z6ZEdWeUlHbHVJRVJoZEdFZ1UyTnBaVzVqWlNJc0luVjBaR0Z1Ym1sdVozTnVZWFp1SWpvaVRsUk9WU0lzSW1adlpITmxiSE51ZFcxdFpYSWlPaUl4TWpNME5UWTNPRGt3TVNJc0luTjFiU0k2TVRVc0ltWjFiR3htYjNKMElqcDBjblZsTENKcllYSmhhM1JsY21WeUlqcGJleUpsYlc1bGEyOWtaU0k2SWxSRVZEUXhOek1pTENKcllYSmhhM1JsY2lJNklrRWlMQ0poY25OMFlXeHNJam95TURJMUxDSndiMlZ1WnlJNk5Td2labUZuSWpvaVRXRmphR2x1WlNCTVpXRnlibWx1WnlKOUxIc2laVzF1Wld0dlpHVWlPaUpVUkZRME1UY3pJaXdpYTJGeVlXdDBaWElpT2lKQ0lpd2lZWEp6ZEdGc2JDSTZNakF5Tnl3aWNHOWxibWNpT2pFd0xDSm1ZV2NpT2lKV2FYTjFZV3hwYzJWeWFXNW5JbjFkTENKcFpDSTZJbVJwWkRwclpYazZlakprYlhwRU9ERmpaMUI0T0ZacmFUZEtZblYxVFcxR1dYSlhVR2RaYjNsMGVXdFZXak5sZVhGb2RERnFPVXRpYnpGMFFYUXlhRVV4Tnpsa1oyRlpZMWxaVFhaaGNHdDFkbEZZUnpsT05XbDZOa2hsVjJaQ1dqVlFSMW80Tkd0eU5EWTVVVE4yTWxOUWVuQk1Sbm8xZDNCaWMyZFZRbEZLWmpaRlZGTlhWVEp6U25CbVIxaEVNelZ6UjBGeU9GbzNSSE0xWkVkM05UZFdRV2RFVkVoeWNWTlhiVXRsZVVSb2MwUlZla2cwTkc1SVlTSjlMQ0pwWkNJNkluVnlianAxZFdsa09tVTRZekV5TjJKbUxUZzRaV0l0TkdWa01DMDRZekE1TFRGbFl6ZG1ZekZpWmpZd015SXNJbWx6YzNWbGNpSTZleUpwWkNJNkltUnBaRHAzWldJNmNISnZkRzkwZVhCbExXeHZiV2x1YnkxcGMzTjFaWEl1WVhwMWNtVjNaV0p6YVhSbGN5NXVaWFE2WkdFd1ltTmhNV1FpTENKdVlXMWxJam9pUW1GamFHVnNiM0p3Y205emFtVnJkQ0o5TENKMllXeHBaRVp5YjIwaU9pSXlNREkxTFRBekxURTRWREV6T2pRd09qSTJMamswTXpVd09ESXpOMW9pTENKMllXeHBaRlZ1ZEdsc0lqb2lNakF5TlMwd05pMHhPRlF4TXpvME1Eb3lOaTQ1TkRNMU5UVTJNemRhSWl3aVkzSmxaR1Z1ZEdsaGJFSnlZVzVrYVc1bklqcDdJbUpoWTJ0bmNtOTFibVJEYjJ4dmNpSTZJaU14WkRNMVlqVWlMQ0pzYjJkdlZYSnNJam9pYUhSMGNITTZMeTlsZUdGdGNHeGxMbU52YlM5c2IyZHZMbkJ1WnlKOUxDSnVZVzFsSWpvaVZtbDBibVZ0WVd3aUxDSmtaWE5qY21sd2RHbHZiaUk2SW1ScFozUmhiSFpwZEc1bGJXRnNJbjBzSW1OdVppSTZleUpxZDJzaU9uc2lhM1I1SWpvaVJVTWlMQ0pqY25ZaU9pSlFMVEkxTmlJc0luZ2lPaUpqTVZvNU5VOW5VMkZ0TUMxME0ySnhaak5JZG1kbWRHZHFNVEp0Tlc1TE9XTkxYMUJOVlRFMloyUlpJaXdpZVNJNklrOVRkbGxMWmpKa2Nra3hlWG96UnkxeWIxTlJkVWgzWDJ0c2RHRXRaRFJ3T0hGT1F6WnZYMlpJWkVVaWZYMTkuQUcxLWRyRE9PWW5qSERmS2I0Q09WV21qSG95dlliLXMxMnBVZktmUzV2bkU5N3huU3BOOU5GTDFSS1hGXy1mV0NucUNGbGNyUWU2eG9JOTJ2bkdrRHciXSwiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiXX0sImlhdCI6MTc0MjMwNTIzOX0.rxvP7COp6ECLksVheTXxy7RQx34UPjdVRMffRrw1VKVrZKlUD2TsGcHkPbzxFDVNqWY8P65Og9r7iikEEuROYg";
    private final ResponseService responseService;


    @Autowired
    public ResponseController(ResponseService responseService, DiplomaSortService diplomaSortService) {
        this.responseService = responseService;
        this.diplomaSortService = diplomaSortService;
    }

    @PostMapping("/callback")
    public String callBack(@RequestBody String requestBody){
        return diplomaSortService.saveKarakterData(diplomaSortService.hentKarakterer(diplomaSortService.getStringDiploma(requestBody)));

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

    @GetMapping("/extracedName")
    public List<String> getExtractedName(){

        String JSON  = responseService.getTokenPayload2(responseService.extractToken(responseService.getTokenPayload(test)));
        try {
            return ResponseService.extractName(JSON);
        } catch (Exception e) {
            return Collections.singletonList("Feil ved henting av token header: \"" + e);
        }
    }

    

}
