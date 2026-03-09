package com.bhushan.StudentManagementSystem.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhushan.StudentManagementSystem.entity.Student;
import com.bhushan.StudentManagementSystem.repository.StudentRepository;

@RestController
@RequestMapping("/api/students/location")
public class StudentLocationController {

    private final StudentRepository repo;

    public StudentLocationController(StudentRepository repo) {
        this.repo = repo;
    }

    @PutMapping("/{id}")
    public Student updateLocation(@PathVariable Integer id,
                                  @RequestParam Double lat,
                                  @RequestParam Double lng,
                                  @RequestParam String address) {

        Student s = repo.findById(id).orElseThrow();

        s.setLatitude(lat);
        s.setLongitude(lng);
        s.setAddress(address);

        return repo.save(s);
    }
}