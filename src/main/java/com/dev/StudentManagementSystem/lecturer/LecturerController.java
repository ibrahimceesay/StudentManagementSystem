package com.dev.StudentManagementSystem.lecturer;


import com.dev.StudentManagementSystem.lecturer.dto.CreateLecturerRequest;
import com.dev.StudentManagementSystem.lecturer.dto.LecturerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerResponse createLecturer(@Valid @RequestBody CreateLecturerRequest request){
        return lecturerService.creatLecturer(request);
    }
}
