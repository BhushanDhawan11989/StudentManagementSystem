package com.bhushan.StudentManagementSystem.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhushan.StudentManagementSystem.entity.Student;
import com.bhushan.StudentManagementSystem.repository.StudentRepository;
import com.bhushan.StudentManagementSystem.service.StudentService;

@Service
public class ServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		List<Student> list=  studentRepository.findAll();
		return list;
	}

}
