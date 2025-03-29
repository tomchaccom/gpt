package com.example.GPT.gpt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 어떤 내용을 저장할 건지에 따라서 내용이 좀 달라질듯

@Entity
public class Gpt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

