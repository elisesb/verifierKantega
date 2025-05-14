package com.example.verifiserer.controller;


import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.service.DiplomaSortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VitnemalControllerTest {

    @InjectMocks
    private VitnemalController vitnemalController;

    @Mock
    private DiplomaSortService diplomaSortService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vitnemalController).build();
    }

    @Test
    void testCallback() throws Exception {
        // Testdata for JSON som kommer inn i hentKarakterer-metoden
        String jsonString = "{ \"karakterer\": [ { \"fag\": \"Math\", \"emnekode\": \"MAT101\", \"karakter\": \"A\", \"poeng\": 5, \"arstall\": 2021 }, { \"fag\": \"Science\", \"emnekode\": \"SCI101\", \"karakter\": \"B\", \"poeng\": 4, \"arstall\": 2021 } ] }";

        // Mocking tjenestemetoden hentKarakterer
        when(diplomaSortService.hentKarakterer(jsonString)).thenReturn(
                List.of(
                        Map.of("fag", "Math", "emnekode", "MAT101", "karakter", "A", "poeng", 5, "arstall", 2021),
                        Map.of("fag", "Science", "emnekode", "SCI101", "karakter", "B", "poeng", 4, "arstall", 2021)
                )
        );

        // Kalle callback-metoden i controlleren
        String response = vitnemalController.callBack(jsonString);

        // Bekreft at callback ble kalt
        assertEquals("Callback mottatt", response);

        // Du kan også verifisere at metoden hentKarakterer ble kalt riktig
        assertNotNull(response);
    }

    /*@Test
    void testCallback() throws Exception {
        // Mock data for callback test
        String requestBody = "{\"test\": \"data\"}";

        // Mocking the services to simulate callback behavior
        when(diplomaSortService.getStringDiploma(requestBody)).thenReturn("dummyDiplomaString");
        when(diplomaSortService.hentKarakterer("dummyDiplomaString")).thenReturn(
                List.of(
                        new Karakter(1L, "Math", "MAT101", "A", 5, 2021),
                        new Karakter(1L, "Science", "SCI101", "B", 4, 2021)
                )
        );
        when(diplomaSortService.hentPersonInfo("dummyDiplomaString")).thenReturn(
                Map.of("name", "John Doe", "fodselsnummer", "123456789")
        );
        when(diplomaSortService.savePersonligData(anyMap())).thenReturn(1L);
        when(diplomaSortService.saveKarakterData(anyList(), anyLong())).thenReturn("Karakter data saved");

        // Sending POST request to callback
        mockMvc.perform(post("/api/verifisere/callback")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Callback mottatt"));

        // Verify if the services were called
        verify(diplomaSortService, times(1)).getStringDiploma(requestBody);
        verify(diplomaSortService, times(1)).hentKarakterer("dummyDiplomaString");
        verify(diplomaSortService, times(1)).hentPersonInfo("dummyDiplomaString");
        verify(diplomaSortService, times(1)).savePersonligData(anyMap());
        verify(diplomaSortService, times(1)).saveKarakterData(anyList(), anyLong());
    }*/

    @Test
    void testCallbackStatus() throws Exception {
        // First check when the callback is not received yet
        mockMvc.perform(get("/api/verifisere/callbackStatus"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        // Simulate a callback received
        vitnemalController.callBack("{\"test\": \"data\"}");

        // Then check after the callback is received
        mockMvc.perform(get("/api/verifisere/callbackStatus"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetGradesByDiplomaId() throws Exception {
        // Mocking the grades data
        Long diplomaId = 1L;
        List<Karakter> karakterList = List.of(
                new Karakter(1L, "Math", "MAT101", "A", 5, 2021),
                new Karakter(1L, "Science", "SCI101", "B", 4, 2021)
        );
        when(diplomaSortService.getGradesByDiplomaId(diplomaId)).thenReturn(karakterList);

        // Sending GET request and verifying the response
        mockMvc.perform(get("/api/verifisere/grades/{diplomaId}", diplomaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fag").value("Math"))
                .andExpect(jsonPath("$[0].karakter").value("A"))
                .andExpect(jsonPath("$[1].fag").value("Science"))
                .andExpect(jsonPath("$[1].karakter").value("B"));
    }

    @Test
    void testGetVitnemal() throws Exception {
        Long id = 1L;
        Vitnemal vitnemal = new Vitnemal("John Doe", "123456789", true, "Bachelor", "BSc", 120);
        when(diplomaSortService.getVitnemalById(id)).thenReturn(vitnemal);

        mockMvc.perform(get("/api/verifisere/vitnemal/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.navn").value("John Doe"))
                .andExpect(jsonPath("$.fodselsnummer").value("123456789"))
                .andExpect(jsonPath("$.fullfort").value(true))
                .andExpect(jsonPath("$.utdanningsnavn").value("Bachelor"))
                .andExpect(jsonPath("$.grad").value("BSc"))
                .andExpect(jsonPath("$.sum").value(120));
    }
}
