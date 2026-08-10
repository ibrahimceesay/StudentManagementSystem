package com.dev.StudentManagementSystem.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByLecturerId(Long LecturerId);

    List<Course> findByDepartmentsId(Long departmentId);
}
