package com.dev.StudentManagementSystem.course;


import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseResponse createCourse(CreateCourseRequest request){
        return null;
    }
}
