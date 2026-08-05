package com.dev.StudentManagementSystem.lecturer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class LecturerResponse {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String departmentName;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
