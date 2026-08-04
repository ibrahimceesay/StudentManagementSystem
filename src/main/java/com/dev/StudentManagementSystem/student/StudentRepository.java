package com.dev.StudentManagementSystem.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Long countByStudentNumberStartingWith(String prefix);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
