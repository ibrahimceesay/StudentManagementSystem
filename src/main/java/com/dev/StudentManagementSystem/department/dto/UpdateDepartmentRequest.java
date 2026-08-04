package com.dev.StudentManagementSystem.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDepartmentRequest {


    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Department code is required")
    private String departmentCode;

    @NotNull(message = "School id is required")
    private Long school_id;



}
