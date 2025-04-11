package com.example.GPT.Qdrant;

import com.example.GPT.Embedding.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    @PostMapping("/search")
    public ResponseEntity<List<QdrantSearchResult>> searchSimilarCases(@RequestBody QuestionRequest questionRequest) {
        // 1. 질문을 임베딩
        List<Float> embedding = embeddingService.getEmbedding(questionRequest.getQuestion());

        // 2. List<Float> → float[] 변환
        float[] embeddingArray = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            embeddingArray[i] = embedding.get(i);
        }

        // 3. Qdrant 검색
        List<QdrantService.SearchResult> rawResults = qdrantService.searchSimilarCases(embeddingArray);

        // 4. SearchResult → QdrantSearchResult 변환 (null 체크 포함)
        List<QdrantSearchResult> results = rawResults.stream()
                .map(result -> new QdrantSearchResult(
                        result.getId(),
                        (float) result.getScore(),
                        result.getPayload() != null ? new HashMap<>(result.getPayload()) : new HashMap<>()
                ))
                .collect(Collectors.toList());

        // 5. 결과 반환
        return ResponseEntity.ok(results);
    }


}
