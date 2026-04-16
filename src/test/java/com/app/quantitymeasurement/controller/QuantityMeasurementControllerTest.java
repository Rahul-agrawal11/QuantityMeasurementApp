package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    private QuantityInputDTO input;
    private QuantityMeasurementDTO result;

    @BeforeEach
    void setup() {
        input = new QuantityInputDTO();
        input.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"));
        input.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LengthUnit"));

        result = new QuantityMeasurementDTO();
        result.setOperation("COMPARE");
        result.setResultString("Equal");
        result.setError(false);
    }

    @Test
    void testCompare_Success() throws Exception {

        when(service.compare(any(), any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("COMPARE"))
                .andExpect(jsonPath("$.resultString").value("Equal"))
                .andExpect(jsonPath("$.error").value(false));
    }

    @Test
    void testAdd_Success() throws Exception {

        result.setOperation("ADD");
        result.setResultValue(2.0);
        result.setResultUnit("FEET");

        when(service.add(any(), any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"));
    }

    @Test
    void testGetHistory() throws Exception {

        when(service.getOperationHistory("COMPARE"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetCount() throws Exception {

        when(service.getOperationCount("COMPARE"))
                .thenReturn(0L);

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}