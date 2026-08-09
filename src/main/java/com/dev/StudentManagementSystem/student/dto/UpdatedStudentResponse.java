package com.dev.StudentManagementSystem.student.dto;

import com.dev.StudentManagementSystem.student.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdatedStudentResponse {

    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private Gender gender;

    private String studentNumber;

    private String schoolName;

    private String departmentName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
