package com.example.GPT.Support;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/supports/{id}")
    public SupportResponseDto getSupports(@PathVariable long id) {
        return supportService.findSupport(id);

    }
}
