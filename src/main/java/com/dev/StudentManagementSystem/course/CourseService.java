package com.dev.StudentManagementSystem.course;


import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
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
}
