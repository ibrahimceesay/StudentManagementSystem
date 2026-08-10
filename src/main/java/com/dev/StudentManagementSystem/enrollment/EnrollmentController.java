package com.dev.StudentManagementSystem.enrollment;

import com.dev.StudentManagementSystem.enrollment.dto.EnrollmentRequest;
import com.dev.StudentManagementSystem.enrollment.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enrollStudent(@Valid @RequestBody EnrollmentRequest request){
        return enrollmentService.enrollStudent(request);
    }
}
