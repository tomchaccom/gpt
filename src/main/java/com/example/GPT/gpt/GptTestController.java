package com.example.GPT.gpt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GptTestController {

    private final GptService gptService;

    public GptTestController(GptService gptService) {
        this.gptService = gptService;
    }

    @GetMapping("/chat")
    public String gptCall(@RequestParam(name = "prompt") String prompt){
        return gptService.gptCalling(prompt);
    }

    @GetMapping("/chat2")
    public String gptcall2(@RequestParam(name = "prompt") String prompt){
        return gptService.promptCalling(prompt);
    }

    @GetMapping("/chat3")
    public String gptcall3(){
        return gptService.promptCalling("당뇨환자를 위한 오늘의 조언을 한줄로 간단하게 해줘");
    }

}
