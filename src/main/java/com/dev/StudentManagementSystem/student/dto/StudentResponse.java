package com.dev.StudentManagementSystem.student.dto;

import lombok.*;
import com.dev.StudentManagementSystem.student.Gender;

import java.time.LocalDateTime;

@Getter
@Setter

//@AllArgsConstructor
public class StudentResponse {

    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private Gender gender;

    private String studentNumber;

    private String schoolName;

    private String departmentName;

    private LocalDateTime createdAt;
}
