package com.bhushan.StudentManagementSystem.controller;

import com.bhushan.StudentManagementSystem.entity.Student;
import com.bhushan.StudentManagementSystem.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/students")
    public String listStudents(Model model){
        model.addAttribute("students", repository.findAll());
        return "students";
    }

    // CREATE FORM
    @GetMapping("/students/add")
    public String addForm(Model model){
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // SAVE (CREATE + UPDATE BOTH)
    @PostMapping("/students/save")
    public String save(@ModelAttribute Student student){
        repository.save(student);
        return "redirect:/students";
    }

    // ---------- UPDATE ----------
    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Integer id, Model model){
        Student student = repository.findById(id).orElse(null);
        model.addAttribute("student", student);
        return "add-student";
    }

    // ---------- DELETE ----------
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Integer id){
        repository.deleteById(id);
        return "redirect:/students";
    }
}