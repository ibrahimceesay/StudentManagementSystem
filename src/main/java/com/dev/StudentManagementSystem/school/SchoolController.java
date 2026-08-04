package com.dev.StudentManagementSystem.school;

import com.dev.StudentManagementSystem.school.dto.CreateSchoolRequest;
import com.dev.StudentManagementSystem.school.dto.SchoolResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    public SchoolResponse createSchool(@Valid @RequestBody CreateSchoolRequest request) {
        return schoolService.createSchool(request);
    }
}
