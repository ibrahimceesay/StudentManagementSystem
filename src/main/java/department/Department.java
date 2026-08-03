package department;

import course.Course;
import jakarta.persistence.*;
import lombok.*;
import school.School;
import student.Student;
import teacher.Lecturer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "department_code")
    private String departmentCode;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Lecturer> teachers = new HashSet<>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
