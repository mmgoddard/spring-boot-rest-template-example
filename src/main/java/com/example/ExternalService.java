package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalService.class);
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    public String getPatientRecordFromBp(ConnectionParameters connPara) throws Exception {

        LOGGER.debug("GET Data | URL: '{}'", connPara.getUrl());

        restTemplate = restTemplateFactory.create(connPara.getUsername(), connPara.getPassword());
        String result = restTemplate.getForObject(connPara.getUrl(), String.class);

        LOGGER.debug("GET Data | Response '{}'", result);
        return result;
    }

    public ResponseEntity<String> postPatientConsultationToBp(String jsonString, ConnectionParameters connPara) {

        LOGGER.trace("POST Data | data: '{}' | connection: '{}'", jsonString, connPara.getUrl());
        restTemplate = restTemplateFactory.create(connPara.getUsername(), connPara.getPassword());

        LOGGER.trace("Executing request to " + connPara.getUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(connPara.getUrl(), entity, String.class);

        LOGGER.trace("POST Data | Response | Status Code '{}' | Response Body '{}'", response.getStatusCode(), response.getBody());
        return response;
    }
}
