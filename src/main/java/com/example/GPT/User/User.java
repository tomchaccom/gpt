package com.example.GPT.User;

import com.example.GPT.Detail.DetailSupport;
import com.example.GPT.Support.SupportProgram;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    public User() {

    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    private List<SupportProgram> supportProgramList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DetailSupport> detailSupportList = new ArrayList<>();

}


