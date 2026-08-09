package com.dev.StudentManagementSystem.lecturer;


import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.lecturer.dto.CreateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.LecturerResponse;
import com.dev.StudentManagementSystem.lecturer.dto.UpdateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.UpdateLecturerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerResponse createLecturer(@Valid @RequestBody CreateLecturerRequest request) {
        return lecturerService.createLecturer(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LecturerResponse> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LecturerResponse getLecturer(@PathVariable Long id) {
        return lecturerService.getLecturer(id);
    }

    @GetMapping("/{id}/courses")
    public List<CourseResponse> getLecturerCourses(@PathVariable Long id){
        return lecturerService.getLecturerCourses(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateLecturerResponse updateLecturer(@PathVariable Long id, @Valid @RequestBody UpdateLecturerRequest request) {
        return lecturerService.updateLecturer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateLecturer(@PathVariable Long id) {
        lecturerService.deActivateLecturer(id);
    }
}
