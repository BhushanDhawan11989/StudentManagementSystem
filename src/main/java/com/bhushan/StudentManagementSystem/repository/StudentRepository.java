package com.bhushan.StudentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bhushan.StudentManagementSystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}