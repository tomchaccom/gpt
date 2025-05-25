package com.example.GPT.Qdrant;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class QdrantService {

    @Value("${qdrant.api.key}")
    private String qdrantApiKey;

    private final WebClient webClient;

    public QdrantService(@Value("${qdrant.host:http://localhost:6333}") String qdrantHost) {
        this.webClient = WebClient.builder()
                .baseUrl(qdrantHost)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public List<SearchResult> searchSimilarCases(float[] vector) {
        Map<String, Object> body = new HashMap<>();
        body.put("vector", vector);
        body.put("top", 1); // 유사한 3개 판례 가져오기
        body.put("with_payload", true);


        return webClient.post()
                .uri("/collections/stalking_cases3/points/search")
                .header("api-key", qdrantApiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(QdrantSearchResponse.class)
                .blockOptional()
                .map(QdrantSearchResponse::getResult)
                .orElse(Collections.emptyList());
    }

    // 응답 구조 정의
    public static class QdrantSearchResponse {
        private List<SearchResult> result;

        public List<SearchResult> getResult() {
            return result;
        }

        public void setResult(List<SearchResult> result) {
            this.result = result;
        }
    }

    public static class SearchResult {
        private double score;
        private String id;
        private Map<String, String> payload;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Map<String, String> getPayload() {
            return payload;
        }

        public void setPayload(Map<String, String> payload) {
            this.payload = payload;
        }
    }
}
