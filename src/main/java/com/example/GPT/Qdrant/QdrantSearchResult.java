package com.example.GPT.Qdrant;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class QdrantSearchResult {
    private String id;
    private float score;
    private Map<String, Object> payload;

    public QdrantSearchResult(String id, float score, Map<String, Object> payload) {
        this.id = id;
        this.score = score;
        this.payload = payload;
    }

}
