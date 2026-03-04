package com.bhushan.StudentManagementSystem.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bhushan.StudentManagementSystem.entity.Student;
import com.bhushan.StudentManagementSystem.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/students")
public class StudentApiController {

    private final StudentRepository repository;

    // FIXED CONSTRUCTOR
    public StudentApiController(StudentRepository repository) {
        this.repository = repository;
    }

    // 1️⃣ GET ALL STUDENTS
    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // 2️⃣ GET STUDENT BY ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // 3️⃣ ADD STUDENT
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    // 4️⃣ UPDATE STUDENT
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id,
                                 @RequestBody Student studentDetails) {

        Student student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());

        return repository.save(student);
    }

    // 5️⃣ DELETE STUDENT
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        repository.deleteById(id);
        return "Student deleted successfully";
    }
}