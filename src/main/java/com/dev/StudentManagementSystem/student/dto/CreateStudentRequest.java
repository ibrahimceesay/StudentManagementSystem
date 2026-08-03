package com.dev.StudentManagementSystem.student.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.dev.StudentManagementSystem.student.Gender;

@Getter
@Setter

public class CreateStudentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Gender is Required")
    private Gender gender;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Department is required")
    private Long departmentId;
}
