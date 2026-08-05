package com.dev.StudentManagementSystem.department;

import com.dev.StudentManagementSystem.department.dto.CreateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.DepartmentResponse;
import com.dev.StudentManagementSystem.department.dto.UpdateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.UpdatedDepartmentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/departments")

public class DepartmentController {

    private final DepartmentService departmentService;


    /**
     * Post mapping to create a new department
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    /**
     * Get mapping to get a department by id
     * */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse getDepartment(@PathVariable Long id){
        return departmentService.getDepartment(id);
    }

    /**
     * Get mapping to retrieve all departments
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     * Update mapping
     * */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedDepartmentResponse updateDepartment(@PathVariable Long id, @Valid @RequestBody UpdateDepartmentRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    /**
     * Delete mapping to deactivate department
     * */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateDepartment(@PathVariable Long id){
        departmentService.deActivateDepartment(id);
    }

}
