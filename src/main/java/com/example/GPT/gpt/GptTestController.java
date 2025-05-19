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

    @GetMapping("/chat2")
    public String gptCall(@RequestParam(name = "prompt") String prompt){
        return gptService.gptCalling(prompt);
    }

    @GetMapping("/chat")
    public String gptcall2(@RequestParam(name = "prompt") String prompt){
        return gptService.promptCalling(prompt);
    }


}
