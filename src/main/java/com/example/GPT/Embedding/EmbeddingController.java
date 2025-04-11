package com.example.GPT.Embedding;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/embedding")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping
    public List<Float> embed(@RequestParam String question) {
        return embeddingService.getEmbedding(question);
    }
}
