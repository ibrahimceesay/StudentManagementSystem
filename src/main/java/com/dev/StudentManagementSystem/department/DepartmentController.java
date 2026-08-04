package com.dev.StudentManagementSystem.department;

import com.dev.StudentManagementSystem.department.dto.CreateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.DepartmentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/department")

public class DepartmentController {

    private final DepartmentService departmentService;


    /**
     * Post mapping to create a new department
     */
    @PostMapping
    public DepartmentResponse createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    /**
     * Get mapping to retrieve all departments
     */
    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
