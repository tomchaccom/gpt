package com.example.GPT.Support;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SupportResponseDto{

        private String category;
        private String programName;
        @Lob
        private String description;
        private String contact;
}
