package student;

import common.ResourceNotFoundException;
import department.Department;
import department.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import student.dto.CreateStudentRequest;
import student.dto.StudentResponse;

import java.time.Year;

@Service

@AllArgsConstructor
public class StudentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponse createStudent(CreateStudentRequest request){

        Department department = findDepartment(request.getDepartmentId());

        if (studentRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("A student already exits with the email: " + request.getEmail());
        }

        if (studentRepository.existsByPhoneNumber(request.getPhoneNumber())){
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

        return studentResponse;
    }


    private String generateStudentNumber(Department department) {

        String year = Integer.toString(Year.now().getValue());
        String yearDigit = year.substring(2);

        String prefix = yearDigit + department.getDepartmentCode();

        Long count = studentRepository.countByStudentNumberStartingWith(prefix) + 1;

        String paddedCount = String.format("%03d", count);

        return prefix + paddedCount;
    }

    private Department findDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }
}
