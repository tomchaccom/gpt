package com.example.GPT.Detail;

import com.example.GPT.Support.SupportProgram;
import com.example.GPT.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;


}
