package com.example.GPT.Support;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DetailResponseDto {


    private String programName;
    @Lob
    private String description;
    @Lob
    private String target;
    @Lob
    private String content;
    @Lob
    private String procedureInfo;
    private String contact;
}
