package com.app.quantitymeasurement;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuantityMeasurementAppApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/quantities";
    }

    private QuantityInputDTO input(double v1, String u1, String t1,
                                  double v2, String u2, String t2) {
        QuantityInputDTO dto = new QuantityInputDTO();
        dto.setThisQuantityDTO(new QuantityDTO(v1, u1, t1));
        dto.setThatQuantityDTO(new QuantityDTO(v2, u2, t2));
        return dto;
    }

    private HttpEntity<QuantityInputDTO> json(QuantityInputDTO body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // ✅ Correct MediaType
        return new HttpEntity<>(body, headers);
    }

    @Test
    @Order(1)
    void testConvert_CelsiusToFahrenheit() {

        QuantityInputDTO body = input(
                100, "CELSIUS", "TemperatureUnit",
                0, "FAHRENHEIT", "TemperatureUnit"
        );

        ResponseEntity<QuantityMeasurementDTO> res =
                restTemplate.exchange(
                        baseUrl() + "/convert",
                        HttpMethod.POST,
                        json(body),
                        QuantityMeasurementDTO.class
                );

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getResultValue()).isEqualTo(212.0);
    }

    @Test
    @Order(2)
    void testAdd_Length() {

        QuantityInputDTO body = input(
                1, "FEET", "LengthUnit",
                12, "INCHES", "LengthUnit"
        );

        ResponseEntity<QuantityMeasurementDTO> res =
                restTemplate.exchange(
                        baseUrl() + "/add",
                        HttpMethod.POST,
                        json(body),
                        QuantityMeasurementDTO.class
                );

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getResultValue()).isEqualTo(2.0);
    }
}