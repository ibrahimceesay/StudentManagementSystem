package com.dev.StudentManagementSystem.school.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSchoolRequest {

    @NotBlank(message = "Name is required")
    private String name;
}
