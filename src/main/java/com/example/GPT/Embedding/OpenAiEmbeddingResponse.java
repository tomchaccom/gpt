package com.example.GPT.Embedding;

import java.util.List;

public class OpenAiEmbeddingResponse {
    public List<Data> data;

    public static class Data {
        public List<Double> embedding;
    }

}
