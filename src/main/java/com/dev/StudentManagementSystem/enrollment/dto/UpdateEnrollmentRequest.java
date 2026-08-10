package com.dev.StudentManagementSystem.enrollment.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEnrollmentRequest {

    private Long id;

    private Long studentId;

    private Long courseId;
}
