package com.example.GPT.gpt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GPTCofiguration {

    @Value("${openai.api.key}")
    private String openAiKey;

    // restTemplate 객체를 생성함 동기 http 클라이언트임
    // 이 코드가 인증 헤더에 api key를 포함시켜서 전송, 인터셉터의 역할 알아보기

    @Bean
    public RestTemplate restTemplate() { // http header에 담기는 인증 정보 넘기기, 클라이언트 요청 역할
        RestTemplate restTemplate = new RestTemplate(); // 서버 내에서 클라이언트 역할을 하는 객체
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization","Bearer " + openAiKey);
            return execution.execute(request, body);

        }));
        return restTemplate;
    }

}
