package com.example.GPT.gpt.DTO;


import lombok.Data;
import java.util.*;

@Data
public class ChatGptRequest {

    private String model; // gpt-3.5-turbo 사용 예정 
    private List<Message> messages; // 사용자가 전달하는 메시지를 저장하는 리스트 

    public ChatGptRequest(String model, List<Message> messages) {
        
        this.model = model;
        this.messages =messages;

    }

}
