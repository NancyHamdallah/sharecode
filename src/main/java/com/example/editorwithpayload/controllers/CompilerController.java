package com.example.editorwithpayload.controllers;

import com.example.editorwithpayload.dto.CodeExecutionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@RestController
public class CompilerController {

    private static final Map<String, String> LANGUAGE_VERSIONS = Map.of(
            "java",    "5",
            "cpp17",   "0",
            "nodejs",  "3",
            "csharp",  "3",
            "python3", "3"
    );




    @Value("${jdoodle.client.id}")
    private String clientId;

    @Value("${jdoodle.client.secret}")
    private String clientSecret;

    @PostMapping("/run")
    public ResponseEntity<String> runCode(@RequestBody CodeExecutionRequest request) {


        String versionIndex = LANGUAGE_VERSIONS.getOrDefault(request.getLanguage(), "0");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> payload = new HashMap<>();
        payload.put("clientId",     clientId);
        payload.put("clientSecret", clientSecret);
        payload.put("script",       request.getCode());
        payload.put("language",     request.getLanguage());
        payload.put("versionIndex", versionIndex);     // ← use the map here

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.jdoodle.com/v1/execute",
                entity,
                String.class
        );
        return ResponseEntity.ok(response.getBody());
    }
}

