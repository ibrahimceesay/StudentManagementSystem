package com.dev.StudentManagementSystem.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class SchoolResponse {

    private Long id;

    private String name;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
