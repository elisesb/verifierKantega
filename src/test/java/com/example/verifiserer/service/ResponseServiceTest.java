package com.example.verifiserer.service;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ResponseServiceTest {

    private ResponseService responseService;

    @BeforeEach
    void setUp() {
        responseService = new ResponseService();
    }

    @Test
    void testGetJustToken_WithAmpersand() {
        String input = "http://localhost:8080/callback?vp_token=testtoken123&state=xyz";
        String result = responseService.getJustToken(input);
        assertEquals("testtoken123", result);
    }

    @Test
    void testGetJustToken_WithoutAmpersand() {
        String input = "http://localhost:8080/callback?vp_token=testtoken123";
        String result = responseService.getJustToken(input);
        assertEquals("testtoken123", result);
    }

    @Test
    void testExtractToken() throws JSONException {
        String nestedToken = "myNestedJwtToken";
        JSONObject json = new JSONObject();
        JSONObject vp = new JSONObject();
        vp.put("verifiableCredential", new org.json.JSONArray().put(nestedToken));
        json.put("vp", vp);

        String result = responseService.extractToken(json.toString());
        assertEquals(nestedToken, result);
    }

    /*@Test
    void testGetTokenPayload_ValidBase64() {
        String simplePayload = "{\"info\":\"testdata\"}";
        String base64Payload = java.util.Base64.getUrlEncoder().withoutPadding()
                .encodeToString(simplePayload.getBytes());

        String fakeToken = "header." + base64Payload + ".signature";
        String input = "http://localhost:8080/callback?vp_token=" + fakeToken;

        String result = responseService.getTokenPayload(input);
        assertTrue(result.contains("testdata"));
    }*/

}

