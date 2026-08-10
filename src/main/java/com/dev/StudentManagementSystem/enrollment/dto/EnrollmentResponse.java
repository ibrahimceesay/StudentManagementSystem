package com.dev.StudentManagementSystem.enrollment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponse {

    private Long id;

    private String studentName;

    private String studentNumber;

    private String courseName;

    private Boolean isActive;

    private LocalDate enrolledOn;

    private LocalDateTime createdAt;
}
