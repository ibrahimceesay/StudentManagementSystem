package com.dev.StudentManagementSystem.school;

import com.dev.StudentManagementSystem.school.dto.CreateSchoolRequest;
import com.dev.StudentManagementSystem.school.dto.SchoolResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolResponse createSchool(CreateSchoolRequest request) {

        if (schoolRepository.existsByName(request.getName())) {
            throw new IllegalStateException("A school exist with the name: " + request.getName());
        }

        School school = new School();
        school.setName(request.getName());


        School saved = schoolRepository.save(school);
        return toResponse(saved);
    }

    public List<SchoolResponse> getAllSchools() {

        return schoolRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SchoolResponse toResponse(School saved) {
        SchoolResponse response = new SchoolResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}
