package com.example.GPT.Detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailSupportDto {
    private Long id;
    private String target;
    private String content;
    private String procedureInfo;
}
