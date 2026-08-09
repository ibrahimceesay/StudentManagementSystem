package com.dev.StudentManagementSystem.student;

import com.dev.StudentManagementSystem.common.ResourceNotFoundException;
import com.dev.StudentManagementSystem.department.Department;
import com.dev.StudentManagementSystem.department.DepartmentService;
import com.dev.StudentManagementSystem.student.dto.CreateStudentRequest;
import com.dev.StudentManagementSystem.student.dto.StudentResponse;
import com.dev.StudentManagementSystem.student.dto.UpdateStudentRequest;
import com.dev.StudentManagementSystem.student.dto.UpdatedStudentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;

    /**
     * Create student service
     */
    public StudentResponse createStudent(CreateStudentRequest request) {

        Department department = departmentService.findById(request.getDepartmentId());

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("A student already exits with the email: " + request.getEmail());
        }

        if (studentRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalStateException("This phone number is already in use");
        }

        String studentNumber = generateStudentNumber(department);

        Student student = new Student();
        student.setName(request.getName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setEmail(request.getEmail());
        student.setGender(request.getGender());
        student.setStudentNumber(studentNumber);
        student.setDepartment(department);

        Student saved = studentRepository.save(student);

        return toResponse(saved);
    }

    /**
     * Retrieve all students
     */
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieve a student by id
     */
    @Transactional(readOnly = true)
    public StudentResponse getStudent(Long id) {

        Student student = findById(id);

        return toResponse(student);
    }

    /**
     * Update student service
     */
    public UpdatedStudentResponse updateStudent(Long id, UpdateStudentRequest request) {

        Student student = findById(id);
        Department department = departmentService.findById(request.getDepartmentId());

        if (!student.getPhoneNumber().equals(request.getPhoneNumber()) && studentRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalStateException("Student exist with phone number: " + request.getPhoneNumber());
        }

        if (!student.getEmail().equals(request.getEmail()) && studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Student exist with email: " + request.getEmail());
        }

        student.setName(request.getName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setEmail(request.getEmail());
        student.setGender(request.getGender());
        student.setDepartment(department);

        Student saved = studentRepository.save(student);

        return toUpdatedStudentResponse(saved);
    }

    /**
     * Deactivate student service
     */
    public void deActivateStudent(Long id) {

        Student student = findById(id);

        student.setIsActive(false);
        studentRepository.save(student);
    }

    /**
     * Helper method to find a student by id
     */
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    /**
     * Helper method to cast Student to StudentResponse
     */
    private StudentResponse toResponse(Student saved) {
//        return new StudentResponse(saved.getName(), saved.getPhoneNumber(),saved.getEmail(), saved.getGender(), saved.getStudentNumber(), saved.getDepartment().getName());

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId(saved.getId());
        studentResponse.setName(saved.getName());
        studentResponse.setPhoneNumber(saved.getPhoneNumber());
        studentResponse.setEmail(saved.getEmail());
        studentResponse.setGender(saved.getGender());
        studentResponse.setStudentNumber(saved.getStudentNumber());
        studentResponse.setSchoolName(saved.getDepartment().getSchool().getName());
        studentResponse.setDepartmentName(saved.getDepartment().getName());
        studentResponse.setCreatedAt(saved.getCreatedAt());

        return studentResponse;
    }

    /**
     * Helper method to cast Student to UpdatedStudentResponse
     */
    private UpdatedStudentResponse toUpdatedStudentResponse(Student saved) {

        UpdatedStudentResponse studentResponse = new UpdatedStudentResponse();

        studentResponse.setId(saved.getId());
        studentResponse.setName(saved.getName());
        studentResponse.setPhoneNumber(saved.getPhoneNumber());
        studentResponse.setEmail(saved.getEmail());
        studentResponse.setGender(saved.getGender());
        studentResponse.setStudentNumber(saved.getStudentNumber());
        studentResponse.setSchoolName(saved.getDepartment().getSchool().getName());
        studentResponse.setDepartmentName(saved.getDepartment().getName());
        studentResponse.setCreatedAt(saved.getCreatedAt());
        studentResponse.setUpdatedAt(saved.getUpdatedAt());

        return studentResponse;
    }

    /**
     * Helper method to generate an alphanumerical studentNumber
     */
    private String generateStudentNumber(Department department) {

        String year = Integer.toString(Year.now().getValue());
        String yearDigit = year.substring(2);

        String prefix = yearDigit + department.getDepartmentCode();

        Long count = studentRepository.countByStudentNumberStartingWith(prefix) + 1;

        String paddedCount = String.format("%03d", count);

        return prefix + paddedCount;
    }
}
