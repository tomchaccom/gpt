package com.example.GPT.Support;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;
    private final DetailRepository detailRepository;

    @GetMapping("/details/{id}")
    public DetailResponseDto getDetail(@PathVariable Long id) {
        return detailService.findDetail(id);
    }

    @PostMapping("/details")
    public DetailResponseDto saveDetail(@RequestBody DetailRequestDto dto){

        DetailSupport entity = new DetailSupport();
        entity.setTarget(dto.getTarget());
        entity.setContent(dto.getContent());
        entity.setProcedureInfo(dto.getProcedureInfo());
        detailRepository.save(entity);
        return new DetailResponseDto(detailRepository.findByTarget("test").getTarget(),"t","t","t","t","t") ;

    }
}
