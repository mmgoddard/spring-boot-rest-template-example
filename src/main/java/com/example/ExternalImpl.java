package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExternalImpl {

    @Autowired
    private ExternalService externalService;

    public String getData() throws Exception {
        String url = "url";
        String username = "username";
        String password = "password";

        ConnectionParameters connectionParameters = new ConnectionParameters(url, username, password);

        return externalService.getPatientRecordFromBp(connectionParameters);
    }

    public String postData() throws Exception {
        String postUrl = "url";
        String username = "username";
        String password = "password";

        ConnectionParameters connectionParameters = new ConnectionParameters(postUrl, username, password);
        ResponseEntity<String> response = externalService.postPatientConsultationToBp("jsonString", connectionParameters);

        return response.getStatusCode().toString();
    }
}
