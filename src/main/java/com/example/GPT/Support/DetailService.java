package com.example.GPT.Support;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DetailService {

    private final DetailRepository detailRepository;

    //상세 정보를 출력하기
    public DetailResponseDto findDetail(Long id){
        Optional<DetailSupport> byId = detailRepository.findById(id);

        if (byId.isPresent()){
            DetailSupport detail = byId.get();
            return new DetailResponseDto(detail.getTarget(),detail.getContent(),detail.getProcedureInfo());
        }else{
            throw new EntityNotFoundException();
        }


    }
}
