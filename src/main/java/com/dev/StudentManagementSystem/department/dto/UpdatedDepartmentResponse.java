package com.dev.StudentManagementSystem.department.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class UpdatedDepartmentResponse {

    private Long id;

    private String name;

    private String departmentCode;

    private String schoolName;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
