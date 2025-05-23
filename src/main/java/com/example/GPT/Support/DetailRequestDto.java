package com.example.GPT.Support;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailRequestDto {
    private String target;

    private String content;

    private String procedureInfo;
}
