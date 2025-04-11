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
    /*private final GptRepository gptRepository;

    @Autowired
    public GptService(RestTemplate restTemplate, GptRepository gptRepository){
        this.restTemplate = restTemplate;
        this.gptRepository = gptRepository;

    }*/

    public String gptCalling(String prompt){

        List<Message> messages = List.of(new Message("user", prompt));
        ChatGptRequest request = new ChatGptRequest(model, messages);

        // post 요청에 대한 결과를 객체로 반환해준다
        ChatGptResponse response = restTemplate.postForObject(apiUrl, request, ChatGptResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
    // DB에 어떤 값을 저장할 건지가 중요할 듯
    public String promptCalling(String userInput){

        // 판례는 DB에 넣어 두고 꺼내서 쓰자 그게 낫겠다
        String systemPrompt = """
            당신은 스토킹 범죄 전문가 법조인입니다.
            사용자가 제공한 사건이 스토킹 범죄에 해당하는지 분석하세요.
            판결의 내용을 o,x 로 확실하게 판단하고, 판결 내용에 대한 이유만 설명하시오
            판결의 기준은 접근 및 물리적 행위, 통신 및 물건을 통한 괴롭힘, 개인정보 및 신분 침해, 지속성/반복성이 기준입니다.
            위 기준을 통해서 스토킹의 유형도 판단해서 출력하세요

            🔹 스토킹 범죄가 **성립하는 경우**:
            - 어떤 기준으로 스토킹이 성립하는 지에 대한 이유 설명
            - 스토킹 유형: 사이버 스토킹 이러한 형태로 출력

            🔹 스토킹 범죄가 **성립하지 않는 경우**:
            - 왜 해당하지 않는지 설명하고,
            - 피해자가 정부 지원금을 받을 수 있는지 알려주세요.

            🔹 참고: 유사한 판례를 제공해주세요.

               판례: 울산지방법원_2023고단3296
               사건 내용: 헤어진 피해자에게 반복하여 연락하는 방식으로 스토킹하고 협박한 피고인에게 
                         벌금 900만원을 선고한 판결
                         
            
            """;

        String systemPrompt2 = """
               
                ##목적
                - 사용자의 프롬프트 입력이 스토킹 피해가 맞는 아닌지를 기준에 맞게  O/ X 로 판결해라
                - 판결의 결과에 대해서 타당한 이유를 50자 이내로 설명해라
                - 판결 시 어떠한 유형의 스토킹인지, 간단하게 출력하기 예시로 사이버 스토킹
                - 유사한 판례가 존재할 시 그 판례도 사용자에게 출력으로 전달해라
                
                ##맥락
                - 사용자가 법에 대한 지식이 없다고 생각하고 알기 쉽게 설명하기 
                
                ##역할
                - 당신은 스토킹 범죄 관련 최고의 전문가이자 법조인입니다.
                - 스토킹 범죄의 성립 여부를 확실하게 판단하고, 그에 대한 근거를 설명할 수 있습니다.
                
                #예시 및 지침 
                - 스토킹을 판단하는 기준은 아래 4가지 이다 
                    - 접근 및 물리적 행위, 통신 및 물건을 통한 괴롭힘, 개인정보 및 신분 침해, 지속성/반복성
                - 판결 결과는 아래와 같이 출력한다 
                    
                # 어조
                - 존댓말로 판결의 이유를 명료하게 전달하기 
                
                """;

        List<Message> messages = List.of(
                  //new Message("system",systemPrompt2),
                 new Message("user",userInput)

        );
        ChatGptRequest request = new ChatGptRequest(model,messages);

        ChatGptResponse response = restTemplate.postForObject(apiUrl,request,ChatGptResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
