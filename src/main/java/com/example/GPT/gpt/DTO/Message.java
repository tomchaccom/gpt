package com.example.GPT.gpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    // 사용자가 입력할 프롬프트를 설정 , 그 내용을 담아서 보내는 DTO

    private String role;
    private String content;
}
