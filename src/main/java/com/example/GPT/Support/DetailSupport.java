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
    private String target;
    @Lob
    private String content;
    @Lob
    private String procedureInfo;

    @OneToOne(mappedBy = "detailSupport")
    private SupportProgram supportProgram;
}
