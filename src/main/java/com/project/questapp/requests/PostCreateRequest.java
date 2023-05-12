package com.project.questapp.requests;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class PostCreateRequest {

    private Long id ;
    private String title;
    private String text ;
    private Long userId;
}
