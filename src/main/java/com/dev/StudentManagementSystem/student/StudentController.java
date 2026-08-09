package com.dev.StudentManagementSystem.student;

import com.dev.StudentManagementSystem.student.dto.CreateStudentRequest;
import com.dev.StudentManagementSystem.student.dto.StudentResponse;
import com.dev.StudentManagementSystem.student.dto.UpdateStudentRequest;
import com.dev.StudentManagementSystem.student.dto.UpdatedStudentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    /**
     * Create new Student
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createNewStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.createStudent(request);
    }

    /**
     * Get all student
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse> getStudents() {
        return studentService.getStudents();
    }

    /**
     * Get a student by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getStudent(Long id) {
        return studentService.getStudent(id);
    }

    /**
     * Update student by id
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedStudentResponse updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    /**
     * Deactivate student
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateStudent(@PathVariable Long id) {
        studentService.deActivateStudent(id);
    }
}
