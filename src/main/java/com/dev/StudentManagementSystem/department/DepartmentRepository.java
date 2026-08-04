package com.dev.StudentManagementSystem.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Boolean existsByName(String name);

    Boolean existsByDepartmentCode(String departmentCode);

}
