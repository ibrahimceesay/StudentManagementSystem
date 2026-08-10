package com.dev.StudentManagementSystem.enrollment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnrollmentRequest {

    private Long studentId;

    private Long courseId;
}
