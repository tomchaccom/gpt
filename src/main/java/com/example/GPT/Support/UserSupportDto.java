package com.example.GPT.Support;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserSupportDto {
    private Long id;
    private List<Long> supportIdList;


    // 유저 id로 유저를 조회 -> 지원제도 id들을 한번에 받아와서, 이를 이 엔티티에 저장하는 목적
}
