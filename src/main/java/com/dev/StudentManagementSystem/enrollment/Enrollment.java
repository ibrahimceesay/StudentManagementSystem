package com.dev.StudentManagementSystem.enrollment;

import com.dev.StudentManagementSystem.course.Course;
import jakarta.persistence.*;
import lombok.*;
import com.dev.StudentManagementSystem.student.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "enrolled_on", updatable = false)
    private LocalDate enrolledOn;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.enrolledOn = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
