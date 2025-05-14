package com.example.verifiserer.service;

import com.example.verifiserer.model.Karakter;
import com.example.verifiserer.model.Vitnemal;
import com.example.verifiserer.repository.KarakterRepository;
import com.example.verifiserer.repository.VitnemalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiplomaSortServiceTest {


    @Mock
    private VitnemalRepository vitnemalRepository;

    @Mock
    private KarakterRepository karakterRepository;

    @InjectMocks
    private DiplomaSortService diplomaSortService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetVitnemalById_Found() {
        Vitnemal vitnemal = new Vitnemal("Elise", "12345678901", true, "IT", "Bachelor", 180);
        vitnemal.setId(1L);
        when(vitnemalRepository.findById(1L)).thenReturn(Optional.of(vitnemal));

        Vitnemal result = diplomaSortService.getVitnemalById(1L);
        assertNotNull(result);
        assertEquals("Elise", result.getNavn());
    }

    @Test
    public void testGetVitnemalById_NotFound() {
        when(vitnemalRepository.findById(2L)).thenReturn(Optional.empty());
        Vitnemal result = diplomaSortService.getVitnemalById(2L);
        assertNull(result);
    }

    @Test
    public void testGetGradesByDiplomaId() {
        Karakter k1 = new Karakter(1L, "Matte", "MATH101", "A", 10, 2023);
        Karakter k2 = new Karakter(2L, "Engelsk", "ENG101", "B", 10, 2023);
        when(karakterRepository.findAll()).thenReturn(List.of(k1, k2));

        List<Karakter> result = diplomaSortService.getGradesByDiplomaId(1L);
        assertEquals(1, result.size());
        assertEquals("Matte", result.get(0).getFag());
    }

    @Test
    public void testHentPersonInfo() {
        String json = """
        {
            "navn": "Elise",
            "fodselsnummer": "12345678901",
            "fullfort": true,
            "utdanningsnavn": "Anvendt datateknologi",
            "grad": "Bachelor",
            "sum": 180
        }
        """;

        Map<String, Object> result = diplomaSortService.hentPersonInfo(json);
        assertEquals("Elise", result.get("navn"));
        assertEquals("Bachelor", result.get("grad"));
    }

    @Test
    public void testHentKarakterer() {
        String json = """
        {
          "karakterer": [
            {
              "fag": "Matte",
              "emnekode": "MATH101",
              "karakter": "A",
              "poeng": 10,
              "arstall": 2024
            }
          ]
        }
        """;

        List<Map<String, Object>> result = diplomaSortService.hentKarakterer(json);
        assertEquals(1, result.size());
        assertEquals("Matte", result.get(0).get("fag"));
        assertEquals("A", result.get(0).get("karakter"));
    }

    @Test
    public void testSavePersonligData() {
        Map<String, Object> data = new HashMap<>();
        data.put("navn", "Elise");
        data.put("fodselsnummer", "12345678901");
        data.put("fullfort", true);
        data.put("utdanningsnavn", "Data");
        data.put("grad", "Bachelor");
        data.put("sum", 180);

        Vitnemal vitnemal = new Vitnemal("Elise", "12345678901", true, "Data", "Bachelor", 180);
        vitnemal.setId(5L);

        when(vitnemalRepository.save(any())).thenAnswer(invocation -> {
            Vitnemal v = invocation.getArgument(0);
            v.setId(5L);
            return v;
        });

        Long id = diplomaSortService.savePersonligData(data);
        assertEquals(5L, id);
    }

    @Test
    public void testSaveKarakterData() {
        List<Map<String, Object>> karakterListe = new ArrayList<>();
        Map<String, Object> k = new HashMap<>();
        k.put("fag", "Matte");
        k.put("emnekode", "MATH101");
        k.put("karakter", "A");
        k.put("poeng", 10);
        k.put("arstall", 2024);
        karakterListe.add(k);

        String response = diplomaSortService.saveKarakterData(karakterListe, 1L);
        assertEquals("ok", response);
        verify(karakterRepository, times(1)).save(any());
    }

}

