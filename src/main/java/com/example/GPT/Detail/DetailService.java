package com.example.GPT.Detail;

import com.example.GPT.Support.SupportProgram;
import com.example.GPT.Support.SupportRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DetailService {

    private final DetailRepository detailRepository;
    private final SupportRepository supportRepository;

    //상세 정보를 출력하기
    public DetailResponseDto findDetail(Long id){
        Optional<DetailSupport> byId = detailRepository.findById(id);
        Optional<SupportProgram> bySupport = supportRepository.findById(id);

        if (byId.isPresent() && bySupport.isPresent()) {
            DetailSupport detail = byId.get();
            SupportProgram support = bySupport.get();
            return new DetailResponseDto(support.getProgramName(),support.getDescription(),detail.getTarget(),
                    detail.getContent(),detail.getProcedureInfo(),support.getContact());
        }else{
            throw new EntityNotFoundException();
        }


    }
}
