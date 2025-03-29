import com.example.GPT.gpt.GPTCofiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class RestTemplateTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(GPTCofiguration.class);


    @Test
    @DisplayName("restTemplate 빈이 존재하는지 확인하기 ")
    void findRestTemplateBena(){
        RestTemplate restTemplateBean = ac.getBean("restTemplate", RestTemplate.class);

        System.out.println("restTemplateBean = " + restTemplateBean);
        assertThat(restTemplateBean).isInstanceOf(RestTemplate.class);

    }

}
