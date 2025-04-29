package com.example.editorwithpayload.service.impl;

import com.example.editorwithpayload.service.SupabaseStorageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SupabaseStorageServiceImpl implements SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String apiKey;

    @Value("${supabase.bucket.name}")
    private String bucketName;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean uploadFile(String filePath, String content) {
        try {
            String endpoint = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucketName, filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN); // or set based on extension if needed
            headers.set("apikey", apiKey);
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("x-upsert", "true"); // overwrite if file already exists

            HttpEntity<String> request = new HttpEntity<>(content, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
