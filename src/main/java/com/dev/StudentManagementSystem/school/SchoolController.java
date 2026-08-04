package com.dev.StudentManagementSystem.school;

import com.dev.StudentManagementSystem.school.dto.CreateSchoolRequest;
import com.dev.StudentManagementSystem.school.dto.SchoolResponse;
import com.dev.StudentManagementSystem.school.dto.UpdatedSchoolResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    /**
     * Post mapping to create a new school entity
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolResponse createSchool(@Valid @RequestBody CreateSchoolRequest request) {
        return schoolService.createSchool(request);
    }

    /**
     * Get mapping to retrieve all schools
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SchoolResponse> getAllSchools() {
        return schoolService.getAllSchools();
    }

    /**
     * Get mapping to find a school by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolResponse findById(@PathVariable Long id) {
        return schoolService.findById(id);
    }

    /**
     * Patch mapping to update school information with id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedSchoolResponse updateSchool(@PathVariable Long id, @Valid @RequestBody CreateSchoolRequest request) {
        return schoolService.updateSchoolInfo(id, request);
    }

    /**
     * Delete mapping to deactivate school with id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateSchool(@PathVariable Long id) {
        schoolService.deActivateSchool(id);
    }
}
