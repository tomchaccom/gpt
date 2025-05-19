package com.example.GPT.Qdrant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class QdrantCaseDto {
    private String title;
    private String content;
    private String similarity;
}
