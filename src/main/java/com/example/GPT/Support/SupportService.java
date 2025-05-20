package com.example.GPT.Support;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SupportService {

    private final SupportRepository supportRepository;

    // ID를 통해서 제도 조회하기
    public SupportResponseDto findSupport(Long id){
        Optional<SupportProgram> entity = supportRepository.findById(id);
        if(entity.isPresent()){
            SupportProgram supportProgram = entity.get();
            return new SupportResponseDto(supportProgram.getCategory(),supportProgram.getProgramName(),
                    supportProgram.getDescription(),supportProgram.getContact() );
        }else{
            throw new EntityNotFoundException();
        }

    }

}
