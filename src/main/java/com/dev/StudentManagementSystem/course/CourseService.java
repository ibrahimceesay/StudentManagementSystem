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

    @Transactional(readOnly = true)
    public CourseResponse getCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course not found with id: " + id));

        return toResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses(){
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UpdateCourseResponse updateCourse(Long id, UpdateCourseRequest request){

        Course course = findCourseById(id);

        Lecturer lecturer = lecturerService.findById(request.getLecturerId());

        List<Department> departments = departmentService.findDepartmentsById(request.getDepartmentIds());

        if(course.getLecturer().getId().equals(request.getLecturerId())) {
            throw new IllegalStateException("Lecturer already assigned to course: " + request.getName());
        }

        if(departments.size() != request.getDepartmentIds().size()){
            throw new IllegalStateException("One or more departments not found in list: " + request.getDepartmentIds());
        }

        Course updatedCourse = new Course();
        course.setName(request.getName());
        course.setCourseCode(request.getCourseCode());
        course.setCreditHours(request.getCreditHours());
        course.setEnrollmentSpots(request.getEnrollmentSpots());
        course.setLecturer(lecturer);
        course.setDepartments(new HashSet<>(departments));

        Course saved = courseRepository.save(course);
        return toUpdatedCourseResponse(saved);
    }

    public void deActivateCourse(Long id){
        Course course = findCourseById(id);

        course.setIsActive(false);
        courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public Course findCourseById(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByLecturerId(Long id){
        return courseRepository.findByLecturerId(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByDepartmentId(Long id){

        return courseRepository.findByDepartmentId(id)
                .stream()
                .map(this::toResponse)
                .toList();
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

    public UpdateCourseResponse toUpdatedCourseResponse (Course saved){
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
