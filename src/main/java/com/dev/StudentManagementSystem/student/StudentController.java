package com.dev.StudentManagementSystem.student;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.dev.StudentManagementSystem.student.dto.CreateStudentRequest;
import com.dev.StudentManagementSystem.student.dto.StudentResponse;

@RestController
@AllArgsConstructor

@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createNewStudent(@Valid @RequestBody CreateStudentRequest request){
       return studentService.createStudent(request);
    }
}
