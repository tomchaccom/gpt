package com.example.GPT.Qdrant;

import com.example.GPT.Embedding.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
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

    @PostMapping("/search/simple")
    public ResponseEntity<List<QdrantCaseDto>> searchSimpleResult(@RequestBody QuestionRequest questionRequest) {
        List<Float> embedding = embeddingService.getEmbedding(questionRequest.getQuestion());

        float[] embeddingArray = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            embeddingArray[i] = embedding.get(i);
        }

        List<QdrantService.SearchResult> rawResults = qdrantService.searchSimilarCases(embeddingArray);

        List<QdrantCaseDto> results = rawResults.stream()
                .map(result -> {
                    Map<String, String> payload = result.getPayload(); // 타입 일치
                    String title = payload != null && payload.get("title") != null ? payload.get("title") : "제목 없음";
                    String content = payload != null && payload.get("content") != null ? payload.get("content") : "내용 없음";
                    String similarity = String.format("%.2f%%", result.getScore() * 100);
                    return new QdrantCaseDto(title, content, similarity);
                })
                .collect(Collectors.toList());

        System.out.println("searchSimilarCases 호출됨, question: " + questionRequest.getQuestion());


        return ResponseEntity.ok(results);
    }
    @GetMapping("/simple")
    public ResponseEntity<List<QdrantCaseDto>> searchSimpleResultByGet(@RequestParam String question) {
        List<Float> embedding = embeddingService.getEmbedding(question);

        float[] embeddingArray = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            embeddingArray[i] = embedding.get(i);
        }

        List<QdrantService.SearchResult> rawResults = qdrantService.searchSimilarCases(embeddingArray);

        List<QdrantCaseDto> results = rawResults.stream()
                .map(result -> {
                    Map<String, String> payload = result.getPayload();
                    String title = payload != null && payload.get("case_id") != null ? payload.get("case_id") : "제목 없음";
                    String content = payload != null && payload.get("content") != null ? payload.get("content") : "내용 없음";
                    String similarity = String.format("%.2f%%", result.getScore() * 100);
                    return new QdrantCaseDto(title, content, similarity);
                })
                .collect(Collectors.toList());

        System.out.println("searchSimpleResultByGet 호출됨, question: " +  question);

        return ResponseEntity.ok(results);
    }



}
