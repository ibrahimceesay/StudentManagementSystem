package com.dev.StudentManagementSystem.school;

import com.dev.StudentManagementSystem.school.dto.SchoolResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    Boolean existsByName(String name);
}
