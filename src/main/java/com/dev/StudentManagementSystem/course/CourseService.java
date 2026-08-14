package com.dev.StudentManagementSystem.course;


import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
import com.dev.StudentManagementSystem.course.dto.UpdateCourseRequest;
import com.dev.StudentManagementSystem.course.dto.UpdateCourseResponse;
import com.dev.StudentManagementSystem.department.Department;
import com.dev.StudentManagementSystem.department.DepartmentService;
import com.dev.StudentManagementSystem.lecturer.Lecturer;
import com.dev.StudentManagementSystem.lecturer.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerService lecturerService;
    private final DepartmentService departmentService;

    /**
     * Create a new Course service
     */
    public CourseResponse createCourse(CreateCourseRequest request) {

        Lecturer lecturer = lecturerService.findById(request.getLecturerId());

        List<Department> departmentList = departmentService.findDepartmentsById(request.getDepartmentIds());

        if (departmentList.size() != request.getDepartmentIds().size()) {
            throw new IllegalStateException("One or more departments not found in list: " + request.getDepartmentIds());
        }

        Course course = new Course();
        course.setName(request.getName());
        course.setCourseCode(request.getCourseCode());
        course.setCreditHours(request.getCreditHours());
        course.setEnrollmentSpots(request.getEnrollmentSpots());
        course.setLecturer(lecturer);
        course.setDepartments(new HashSet<>(departmentList));

        Course saved = courseRepository.save(course);
        return toResponse(saved);
    }

    /**
     * Retrieve a courses by id
     */
    @Transactional(readOnly = true)
    public CourseResponse getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return toResponse(course);
    }

    /**
     * Retrieve all courses
     */
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Get courses assigned to lecturer by id
     */
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByLecturerId(Long id) {

        if (!lecturerService.existsById(id)) {
            throw new IllegalStateException("Lecturer not found with id: " + id);
        }

        return courseRepository.findByLecturerId(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Update a course
     */
    public UpdateCourseResponse updateCourse(Long id, UpdateCourseRequest request) {

        Course course = findById(id);

        Lecturer lecturer = lecturerService.findById(request.getLecturerId());

        List<Department> departments = departmentService.findDepartmentsById(request.getDepartmentIds());

        if (departments.size() != request.getDepartmentIds().size()) {
            throw new IllegalStateException("One or more departments not found in list: " + request.getDepartmentIds());
        }

        course.setName(request.getName());
        course.setCourseCode(request.getCourseCode());
        course.setCreditHours(request.getCreditHours());
        course.setEnrollmentSpots(request.getEnrollmentSpots());
        course.setLecturer(lecturer);
        course.setDepartments(new HashSet<>(departments));

        Course saved = courseRepository.save(course);
        return toUpdatedCourseResponse(saved);
    }

    /**
     * Deactivate a course
     */
    public void deActivateCourse(Long id) {
        Course course = findById(id);

        course.setIsActive(false);
        courseRepository.save(course);
    }

    /**
     * Helper method to get a course by id
     */
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    /**
     * Get all courses that belong to a department by DepartmentId
     */
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByDepartmentId(Long id) {

        if(!departmentService.existsById(id)){
            throw new IllegalStateException("Department not found with id: " + id);
        }

        return courseRepository.findByDepartmentsId(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Helper method to cast Course to CourseResponse
     */
    private CourseResponse toResponse(Course saved) {

        CourseResponse response = new CourseResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCourseCode(saved.getCourseCode());
        response.setCreditHours(saved.getCreditHours());
        response.setEnrollmentSpots(saved.getEnrollmentSpots());
        response.setLecturerName(saved.getLecturer().getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreateAt());

        return response;
    }

    /**
     * Helper method to cast Course to UpdateCourseResponse
     */
    private UpdateCourseResponse toUpdatedCourseResponse(Course saved) {
        UpdateCourseResponse response = new UpdateCourseResponse();

        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCourseCode(saved.getCourseCode());
        response.setCreditHours(saved.getCreditHours());
        response.setEnrollmentSpots(saved.getEnrollmentSpots());
        response.setLecturerName(saved.getLecturer().getName());
        response.setIsActive(saved.getIsActive());
        response.setCreatedAt(saved.getCreateAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        return response;
    }

}
