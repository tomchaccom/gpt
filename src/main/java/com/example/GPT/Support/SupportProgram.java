package com.example.GPT.Support;

import com.example.GPT.Detail.DetailSupport;
import com.example.GPT.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class SupportProgram {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String programName;

    @Lob
    private String description;

    private String contact;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id")
    private DetailSupport detailSupport;

    // 이 메소드가 전체 내용을 불러올 수 있는가?
    private void setDetailSupport(DetailSupport detailSupport) {
        this.detailSupport = detailSupport;
        detailSupport.setSupportProgram(this);
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
