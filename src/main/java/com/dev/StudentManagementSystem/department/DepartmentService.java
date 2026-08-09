package com.dev.StudentManagementSystem.department;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.course.CourseService;
import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.department.dto.CreateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.DepartmentResponse;
import com.dev.StudentManagementSystem.department.dto.UpdateDepartmentRequest;
import com.dev.StudentManagementSystem.department.dto.UpdatedDepartmentResponse;
import com.dev.StudentManagementSystem.school.School;
import com.dev.StudentManagementSystem.school.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@AllArgsConstructor

@Service
@Transactional

public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CourseService courseService;
    private final SchoolService schoolService;

    /**
     * Create a new department entity
     */
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        School school = schoolService.findById(request.getSchoolId());

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
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieve all departments belonging to a school by school id
     */
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getDepartmentsBySchoolId(Long id) {
        return departmentRepository.findBySchoolId(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieve specific department by id
     */
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(Long id) {
        Department department = findById(id);

        return toResponse(department);
    }

    /**
     * Retrieve all courses that belong to a department
     */
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByDepartment(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new IllegalStateException("Department not found with id: " + id);
        }

        return courseService.getCoursesByDepartmentId(id);
    }

    /**
     * Update department information
     */
    public UpdatedDepartmentResponse updateDepartment(Long id, UpdateDepartmentRequest request) {

        Department department = findById(id);

        if (!department.getName().equals(request.getName()) &&
                departmentRepository.existsByName(request.getName())) {
            throw new IllegalStateException("Department exist with name: " + request.getName());
        }

        if (!department.getDepartmentCode().equals(request.getDepartmentCode()) &&
                departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new IllegalStateException("Department exist with code: " + request.getDepartmentCode());
        }

        department.setName(request.getName());
        department.setDepartmentCode(request.getDepartmentCode());

        Department saved = departmentRepository.save(department);
        return toUpdatedDepartmentResponse(saved);
    }

    /**
     * Deactivate department
     */

    public void deActivateDepartment(Long id) {

        Department department = findById(id);

        department.setIsActive(false);

        departmentRepository.save(department);
    }

    /**
     * Retrieve a department by id
     */
    @Transactional(readOnly = true)
    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    /**
     * Retrieve multiple departments by id
     */
    @Transactional(readOnly = true)
    public List<Department> findDepartmentsById(Set<Long> ids) {
        return departmentRepository.findAllById(ids);
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

    /**
     * Helper method to map Department to UpdatedDepartmentResponse
     */
    private UpdatedDepartmentResponse toUpdatedDepartmentResponse(Department saved) {

        UpdatedDepartmentResponse response = new UpdatedDepartmentResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDepartmentCode(saved.getDepartmentCode());
        response.setSchoolName(saved.getSchool().getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        return response;
    }
}
