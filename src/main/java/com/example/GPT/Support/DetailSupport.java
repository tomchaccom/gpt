package com.example.GPT.Support;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class DetailSupport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

    @Lob
    private String detail; // 전체를 한번에 다 저장할지.. 아니면 그냥... 흠..

    @OneToOne(mappedBy = "detailSupport")
    private SupportProgram supportProgram;
}
