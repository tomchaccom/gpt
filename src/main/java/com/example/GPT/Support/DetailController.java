package com.example.GPT.Support;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;

    @GetMapping("/details/{id}")
    public DetailResponseDto getDetail(@PathVariable Long id) {
        return detailService.findDetail(id);
    }
}
