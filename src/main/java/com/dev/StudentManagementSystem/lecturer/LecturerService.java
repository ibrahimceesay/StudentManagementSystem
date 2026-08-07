package com.dev.StudentManagementSystem.lecturer;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.department.Department;
import com.dev.StudentManagementSystem.department.DepartmentService;
import com.dev.StudentManagementSystem.lecturer.dto.CreateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.LecturerResponse;
import com.dev.StudentManagementSystem.lecturer.dto.UpdateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.UpdateLecturerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor

@Service
@Transactional
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final DepartmentService departmentService;

    /**
     * Create a new lecturer
     */
    public LecturerResponse createLecturer(CreateLecturerRequest request) {

        Department department = departmentService.findById(request.getDepartmentId());

        if (lecturerRepository.existsByEmail(request.getEmail())) {
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

    /**
     * Retrieve all lecturers
     */
    @Transactional(readOnly = true)
    public List<LecturerResponse> getAllLecturers() {
        return lecturerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieve a lecturer by id with type LecturerResponse
     */
    @Transactional(readOnly = true)
    public LecturerResponse getLecturer(Long id) {
        Lecturer lecturer = findById(id);

        return toResponse(lecturer);
    }

//    @Transactional(readOnly = true)
//    public List<CourseResponse> getLecturerCourses(Long id){
//        if(!lecturerRepository.existsById(id)){
//            throw new ResourceNotFoundException("Lecturer not found with id: " + id);
//        }
//
//        return lecturerRepository.findCoursesByLecturerId(id)
//                .stream()
//                .map()
//                .toList();
//
//    }

    /**
     * Update lecturer information
     */
    public UpdateLecturerResponse updateLecturer(Long id, UpdateLecturerRequest request) {

        Lecturer lecturer = findById(id);

        Department department = departmentService.findById(request.getDepartmentId());

        if (!lecturer.getEmail().equals(request.getEmail()) && lecturerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Lecturer exists with email: " + request.getEmail());
        }

        lecturer.setName(request.getName());
        lecturer.setPhoneNumber(request.getPhoneNumber());
        lecturer.setEmail(request.getEmail());
        lecturer.setDepartment(department);

        Lecturer saved = lecturerRepository.save(lecturer);

        return toUpdatedResponse(saved);
    }

    /**
     * Deactivate a lecturer
     */
    public void deActivateLecturer(Long id) {
        Lecturer lecturer = findById(id);

        lecturer.setIsActive(false);

        lecturerRepository.save(lecturer);
    }

    /**
     * Retrieve lecturer by id
     *
     */
    @Transactional(readOnly = true)
    public Lecturer findById(Long id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id: " + id));
    }

    /**
     * Helper method to map Lecturer to LecturerResponse
     */
    private LecturerResponse toResponse(Lecturer saved) {

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

    /**
     * Helper method to map Lecturer to UpdateLecturerResponse
     */
    private UpdateLecturerResponse toUpdatedResponse(Lecturer saved) {

        UpdateLecturerResponse response = new UpdateLecturerResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setEmail(saved.getEmail());
        response.setIsActive(saved.getIsActive());
        response.setDepartmentName(saved.getDepartment().getName());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        return response;
    }
}
