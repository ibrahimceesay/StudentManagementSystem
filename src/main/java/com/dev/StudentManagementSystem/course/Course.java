package com.dev.StudentManagementSystem.course;

import com.dev.StudentManagementSystem.department.Department;
import com.dev.StudentManagementSystem.enrollment.Enrollment;
import jakarta.persistence.*;
import com.dev.StudentManagementSystem.lecturer.Lecturer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "credit_hours")
    private Integer creditHours;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "enrollment_spots")
    private Integer enrollmentSpots;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer lecturer;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollment = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "department_course",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Department> departments = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {this.createAt = LocalDateTime.now();}

    @PreUpdate
    protected void onUpdate() {this.updatedAt = LocalDateTime.now();}
}
