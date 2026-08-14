package com.dev.StudentManagementSystem.course;

import com.dev.StudentManagementSystem.course.dto.CourseResponse;
import com.dev.StudentManagementSystem.course.dto.CreateCourseRequest;
import com.dev.StudentManagementSystem.course.dto.UpdateCourseRequest;
import com.dev.StudentManagementSystem.course.dto.UpdateCourseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@Valid @RequestBody CreateCourseRequest request){
        return courseService.createCourse(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getCourse(@PathVariable Long id){
        return courseService.getCourse(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourses();
    }

    /**
     * Retrieve all courses from a department
     * */
    @GetMapping("/department/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCoursesByDepartment(@PathVariable Long id){
        return courseService.getCoursesByDepartmentId(id);
    }

    @GetMapping("/lecturer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getLecturerCourses(@PathVariable Long id){
        return courseService.getCoursesByLecturerId(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateCourseResponse updateCourse(@PathVariable Long id, @Valid @RequestBody UpdateCourseRequest request){
        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateCourse(@PathVariable Long id){
        courseService.deActivateCourse(id);
    }
}
