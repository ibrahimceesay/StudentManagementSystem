package com.dev.StudentManagementSystem.course;


import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
import com.dev.StudentManagementSystem.lecturer.Lecturer;
import com.dev.StudentManagementSystem.lecturer.LecturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Service
@Transactional
public class CourseService {

    private final LecturerRepository lecturerRepository;

    public CourseResponse createCourse(CreateCourseRequest request) {

        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id: " + request.getLecturerId()));


        Course course = new Course();


        return null;
    }
}
