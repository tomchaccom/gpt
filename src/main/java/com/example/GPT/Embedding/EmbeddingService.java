package com.example.GPT.Embedding;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmbeddingService {

    @Value("${openai.api.key}")
    private String OPENAI_API_KEY;

    private static final String API_URL = "https://api.openai.com/v1/embeddings";

    public List<Float> getEmbedding(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        OpenAiEmbeddingRequest requestBody = new OpenAiEmbeddingRequest("text-embedding-ada-002", List.of(text));
        HttpEntity<OpenAiEmbeddingRequest> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<OpenAiEmbeddingResponse> response = restTemplate.postForEntity(
                API_URL,
                request,
                OpenAiEmbeddingResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Double 리스트 → Float 리스트로 변환해서 반환
            return response.getBody().data.get(0).embedding
                    .stream()
                    .map(Double::floatValue)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("OpenAI API 호출 실패");
        }
    }
}

