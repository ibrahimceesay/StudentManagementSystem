package com.dev.StudentManagementSystem.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class CreateCourseRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotNull(message = "Number of credit hours is required")
    private Integer creditHours;

    @NotNull(message = "Number of enrollment spots is required")
    private Integer enrollmentSpots;

    @NotNull(message = "Lecturer id is required")
    private Long lecturerId;

    @NotEmpty(message = "At least one department is required")
    private Set<Long> departmentIds;
}
