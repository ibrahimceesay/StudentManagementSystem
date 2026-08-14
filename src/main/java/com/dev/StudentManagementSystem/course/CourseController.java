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

    /**
     * Create new course
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    /**
     * Get course by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    /**
     * get All courses
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Retrieve all courses from a department
     */
    @GetMapping("/department/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCoursesByDepartment(@PathVariable Long id) {
        return courseService.getCoursesByDepartmentId(id);
    }

    /**
     * Get all courses assigned to a lecturer by id
     */
    @GetMapping("/lecturer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getLecturerCourses(@PathVariable Long id) {
        return courseService.getCoursesByLecturerId(id);
    }

    /**
     * Update course info
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateCourseResponse updateCourse(@PathVariable Long id, @Valid @RequestBody UpdateCourseRequest request) {
        return courseService.updateCourse(id, request);
    }

    /**
     * Deactivate course
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deActivateCourse(@PathVariable Long id) {
        courseService.deActivateCourse(id);
    }
}
