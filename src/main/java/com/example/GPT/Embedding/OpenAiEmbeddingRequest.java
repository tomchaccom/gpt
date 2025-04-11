package com.example.GPT.Embedding;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OpenAiEmbeddingRequest {

    private String model;
    private List<String> input;

}
