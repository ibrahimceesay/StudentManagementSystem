package com.dev.StudentManagementSystem.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Boolean existsByStudentIdAndCourseIdAndIsActiveTrue (Long studentId, Long courseId);

    Long countByCourseIdAndIsActiveTrue (Long courseId);
}
