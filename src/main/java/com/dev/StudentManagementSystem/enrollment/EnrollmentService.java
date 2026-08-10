package com.dev.StudentManagementSystem.enrollment;

import com.dev.StudentManagementSystem.course.Course;
import com.dev.StudentManagementSystem.course.CourseService;
import com.dev.StudentManagementSystem.enrollment.dto.EnrollmentRequest;
import com.dev.StudentManagementSystem.enrollment.dto.EnrollmentResponse;
import com.dev.StudentManagementSystem.student.Student;
import com.dev.StudentManagementSystem.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Transactional
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentResponse enrollStudent(EnrollmentRequest request){

        Student student = studentService.findById(request.getStudentId());

        Course course = courseService.findById(request.getCourseId());

        if(enrollmentRepository.existsByStudentIdAndCourseIdAndIsActiveTrue(request.getStudentId(), request.getCourseId())){
            throw new IllegalStateException("Student " + student.getId() + " is already enrolled in " + course.getCourseCode());
        }

        Long spotsTakent = enrollmentRepository.countByCourseIdAndIsActiveTrue(request.getCourseId());
        if(spotsTakent >= course.getEnrollmentSpots()){
            throw new IllegalStateException("Course " + course.getCourseCode() + " is full ( " +
                    enrollmentRepository.countByCourseIdAndIsActiveTrue(request.getCourseId()) + " / " + course.getEnrollmentSpots() + " )");
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return toResponse(saved);
    }

    private EnrollmentResponse toResponse(Enrollment saved){

        EnrollmentResponse response = new EnrollmentResponse();

        response.setId(saved.getId());
        response.setStudentName(saved.getStudent().getName());
        response.setStudentNumber(saved.getStudent().getStudentNumber());
        response.setCourseName(saved.getCourse().getName());
        response.setIsActive(saved.getIsActive());
        response.setEnrolledOn(saved.getEnrolledOn());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }
}
