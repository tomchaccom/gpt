package com.example.GPT.gpt;

import com.example.GPT.gpt.DTO.ChatGptRequest;
import com.example.GPT.gpt.DTO.ChatGptResponse;
import com.example.GPT.gpt.DTO.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GptService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String gptCalling(String prompt){

        List<Message> messages = List.of(new Message("user", prompt));
        ChatGptRequest request = new ChatGptRequest(model, messages);

        // post 요청에 대한 결과를 객체로 반환해준다
        ChatGptResponse response = restTemplate.postForObject(apiUrl, request, ChatGptResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
    // DB에 어떤 값을 저장할 건지가 중요할 듯
    public String promptCalling(String userInput){

        String systemPrompt= """
               
                ##목적
                - 사용자의 프롬프트 입력이 스토킹 피해가 맞는 아닌지를 기준에 맞게  True / False 로 판결해라
                - 판결 시 어떠한 유형의 스토킹인지 출력하기 
                - 유사한 판례를 제공할 것임. 판례는 0~10 사이의 수를 가지고 있고, 이는 유사도가 높은 순서이다
                - 스토킹 피해가 아니라면 스토킹 유형은 출력하지 않아도 된다, 하지만 스토킹 판별 기준은 True/False로 출력한다
                
                ##맥락
                - 사용자가 법에 대한 지식이 없다고 생각하고 알기 쉽게 설명하기 
                
                ##역할
                - 당신은 스토킹 범죄 관련 최고의 전문가이자 법조인입니다.
                - 스토킹 범죄의 성립 여부를 확실하게 판단하고, 그에 대한 근거를 설명할 수 있습니다.
                
                #예시 및 지침 
                - 스토킹을 판단하는 기준은 아래 4가지 이다 
                   
                   접근 및 물리적 행위 (스토커가 피해자의 집, 학교, 직장 등 실제 장소에 나타나거나 미행, 기다림, 접근 시도 등 물리적으로 존재를 드러내는 행위)
                
                   통신 및 물건을 통한 괴롭힘 (전화, 문자, SNS 메시지, 이메일 등 특정인을 대상으로 반복적으로 연락하거나, 원치 않는 선물·택배 등을 보내는 등의 방식으로 정신적 불안이나 공포를 유발하는 행위)
                
                   개인정보 및 신분 침해 (피해자의 개인정보를 동의 없이 수집·이용하거나, 계정 해킹, 위치 추적, 사칭 등으로 프라이버시를 침해하는 행위)
                
                   지속성/반복성 (해당 행위가 한두 번에 그치지 않고 2회 이상 반복되며, 일정 기간 지속적으로 이어지는 경우)
                
                # 스토킹 유형
                - 오프라인 직접 스토킹
                - 물건 전달형 스토킹
                - 통신/사이버 스토킹
                - 개인정보 악용형 스토킹 (신청형 스토킹)
                - 디지털 성적 스토킹
                
                # 스토킹 행위
                - 불안감조성 
                - 장난전화  
                - 지속적 괴롭힘 
                - 불법정보의 유통                 
                - 폭행
                - 협박 
                - 업무방해
                - 주거침입 
                - 통신매체를 이용한 음란행위
                - 여성폭력
                
                
                # 응답형식 
                - json 형식으로 응답하기 
                - {
                      "스토킹 유형":"사이버 스토킹",
                      "스토킹 판결 여부": "False",
                      "접근 및 물리적 행위": "True",
                      "통신 및 물건을 통한 괴롭힘" : "True",
                      "개인정보 및 신분 침해": "True",
                      "지속성/반복성" : "True"
                  }
                - 위 형식으로 결과를 출력
                
                """;

        List<Message> messages = List.of(
                 new Message("system",systemPrompt),
                 new Message("user",userInput)

        );
        ChatGptRequest request = new ChatGptRequest(model,messages);

        ChatGptResponse response = restTemplate.postForObject(apiUrl,request,ChatGptResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
