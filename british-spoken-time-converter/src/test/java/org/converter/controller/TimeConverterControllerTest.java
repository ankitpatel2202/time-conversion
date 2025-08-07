package org.converter.controller;

import org.converter.dto.TimeConversionRequest;
import org.converter.dto.TimeConversionResponse;
import org.converter.utils.FormatType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.main.allow-bean-definition-overriding=true"
})
class TimeConverterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void testConvertTimeBritishFormatSuccess() {
        TimeConversionRequest request = new TimeConversionRequest("12:00", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TimeConversionResponse> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                TimeConversionResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12:00", response.getBody().getOriginalTime());
        assertEquals("noon", response.getBody().getSpokenTime());
        assertNull(response.getBody().getError());
    }

    @Test
    void testConvertTimeBritishFormatMidnight() {
        TimeConversionRequest request = new TimeConversionRequest("00:00", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TimeConversionResponse> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                TimeConversionResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("00:00", response.getBody().getOriginalTime());
        assertEquals("midnight", response.getBody().getSpokenTime());
        assertNull(response.getBody().getError());
    }

    @Test
    void testConvertTimeBritishFormatHalfPast() {
        TimeConversionRequest request = new TimeConversionRequest("07:30", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TimeConversionResponse> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                TimeConversionResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("07:30", response.getBody().getOriginalTime());
        assertEquals("half past seven am", response.getBody().getSpokenTime());
        assertNull(response.getBody().getError());
    }

    
    @Test
    void testConvertTimeDefaultFormatBritish() {
        TimeConversionRequest request = new TimeConversionRequest("12:00"); // Default format should be BRITISH
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TimeConversionResponse> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                TimeConversionResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12:00", response.getBody().getOriginalTime());
        assertEquals("noon", response.getBody().getSpokenTime());
        assertNull(response.getBody().getError());
    }

    @Test
    void testConvertTimeInvalidTimeFormat() throws Exception {
        TimeConversionRequest request = new TimeConversionRequest("25:00", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        JsonNode errorJson = objectMapper.readTree(response.getBody());
        assertEquals(400, errorJson.get("status").asInt());
        assertEquals("Invalid time format", errorJson.get("error").asText());
        assertTrue(errorJson.get("details").asText().contains("25:00"));
    }

    @Test
    void testConvertTimeInvalidTimeFormatMinutes() throws Exception {
        TimeConversionRequest request = new TimeConversionRequest("12:60", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        JsonNode errorJson = objectMapper.readTree(response.getBody());
        assertEquals(400, errorJson.get("status").asInt());
        assertEquals("Invalid time format", errorJson.get("error").asText());
        assertTrue(errorJson.get("details").asText().contains("12:60"));
    }

    @Test
    void testConvertTimeInvalidTimeFormatString() throws Exception {
        TimeConversionRequest request = new TimeConversionRequest("invalid", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        JsonNode errorJson = objectMapper.readTree(response.getBody());
        assertEquals(400, errorJson.get("status").asInt());
        assertEquals("Invalid time format", errorJson.get("error").asText());
        assertTrue(errorJson.get("details").asText().contains("invalid"));
    }

    @Test
    void testConvertTimeEmptyTime() throws Exception {
        TimeConversionRequest request = new TimeConversionRequest("", FormatType.BRITISH);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TimeConversionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/api/v1/time/convert",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        JsonNode errorJson = objectMapper.readTree(response.getBody());
        assertEquals(400, errorJson.get("status").asInt());
        assertEquals("Invalid request body", errorJson.get("error").asText());
        assertTrue(errorJson.get("details").asText().contains("Time cannot be empty"));
    }
} 
