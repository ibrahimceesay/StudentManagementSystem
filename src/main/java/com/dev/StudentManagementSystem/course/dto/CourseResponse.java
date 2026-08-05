package com.dev.StudentManagementSystem.course.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class CourseResponse {

    private Long id;

    private String name;

    private String courseCode;

    private Integer creditHours;

    private Integer enrollmentSpots;

    private String lecturerName;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
