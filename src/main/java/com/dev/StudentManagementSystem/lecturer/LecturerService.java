package com.dev.StudentManagementSystem.lecturer;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.department.Department;
import com.dev.StudentManagementSystem.department.DepartmentRepository;
import com.dev.StudentManagementSystem.lecturer.dto.CreateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.LecturerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Service
@Transactional
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final DepartmentRepository departmentRepository;

    public LecturerResponse creatLecturer(CreateLecturerRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        if(lecturerRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("Lecturer already exist with email: " + request.getEmail());
        }

        Lecturer lecturer = new Lecturer();

        lecturer.setName(request.getName());
        lecturer.setEmail(request.getEmail());
        lecturer.setPhoneNumber(request.getPhoneNumber());
        lecturer.setDepartment(department);

        Lecturer saved = lecturerRepository.save(lecturer);

        return toResponse(saved);
    }

    public LecturerResponse toResponse(Lecturer saved){

        LecturerResponse response = new LecturerResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setEmail(saved.getEmail());
        response.setIsActive(saved.getIsActive());
        response.setDepartmentName(saved.getDepartment().getName());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}
