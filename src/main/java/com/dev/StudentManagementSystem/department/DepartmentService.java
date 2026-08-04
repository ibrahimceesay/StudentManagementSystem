package com.dev.StudentManagementSystem.department;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.department.dto.CreateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.DepartmentResponse;
import com.dev.StudentManagementSystem.school.School;
import com.dev.StudentManagementSystem.school.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor

@Service
@Transactional

public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final SchoolRepository schoolRepository;

    /**
     * Create a new department entity
     */
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        School school = schoolRepository.findById(request.getSchool_id())
                .orElseThrow(() -> new ResourceNotFoundException("School does not exist with id: " + request.getSchool_id()));

        if (departmentRepository.existsByName(request.getName())) {
            throw new IllegalStateException("Department already exist with name: " + request.getName());
        }

        if (departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new IllegalStateException("Department exist with department code: " + request.getDepartmentCode());
        }

        Department department = new Department();

        department.setName(request.getName());
        department.setDepartmentCode(request.getDepartmentCode());
        department.setSchool(school);

        Department saved = departmentRepository.save(department);

        return toResponse(saved);
    }

    /**
     * Retrieve all departments
     * */

    public List<DepartmentResponse> getAllDepartments(){
        return departmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Map type department to type DepartmentResponse
     */

    private DepartmentResponse toResponse(Department saved) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDepartmentCode(saved.getDepartmentCode());
        response.setSchoolName(saved.getSchool().getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }

    public List<DepartmentResponse> getAllDepartmentsBySchool(Long id) {

        if (!schoolRepository.existsById(id)){
            throw new IllegalStateException("School does not exist with id: " + id);
        }

        return departmentRepository.findBySchoolId(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
