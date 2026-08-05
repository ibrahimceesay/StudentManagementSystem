package com.dev.StudentManagementSystem.course;

import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@Valid @RequestBody CreateCourseRequest request){
        return courseService.createCourse(request);
    }
}
