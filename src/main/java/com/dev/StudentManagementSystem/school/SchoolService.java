package com.dev.StudentManagementSystem.school;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.school.dto.CreateSchoolRequest;
import com.dev.StudentManagementSystem.school.dto.SchoolResponse;
import com.dev.StudentManagementSystem.school.dto.UpdatedSchoolResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    /**
     * Create new school entity
     */
    public SchoolResponse createSchool(CreateSchoolRequest request) {

        if (schoolRepository.existsByName(request.getName())) {
            throw new IllegalStateException("A school exist with the name: " + request.getName());
        }

        School school = new School();
        school.setName(request.getName());


        School saved = schoolRepository.save(school);
        return toResponse(saved);
    }

    /**
     * Get all schools both active and inactive
     */
    @Transactional(readOnly = true)
    public List<SchoolResponse> getAllSchools() {

        return schoolRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Update School information
     */
    public UpdatedSchoolResponse updateSchoolInfo(Long id, CreateSchoolRequest request) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        if (!school.getName().equals(request.getName()) && schoolRepository.existsByName(request.getName())) {
            throw new IllegalStateException("School already exits with name: " + request.getName());
        }

        school.setName(request.getName());

        return toUpdateResponse(school);
    }

    /**
     * Deactivate School
     */
    public void deActivateSchool(Long id) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        school.setIsActive(false);
    }

    /**
     * Method helper to find school by id
     */
    @Transactional(readOnly = true)
    public SchoolResponse findById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        return toResponse(school);
    }

    /**
     * Map type Student to type StudentResponse
     */
    private SchoolResponse toResponse(School saved) {
        SchoolResponse response = new SchoolResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }

    /**
     * Map type School to type UpdatedSchoolResponse
     */
    private UpdatedSchoolResponse toUpdateResponse(School saved) {

        UpdatedSchoolResponse response = new UpdatedSchoolResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        return response;
    }
}
