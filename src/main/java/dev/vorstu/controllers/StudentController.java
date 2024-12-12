package dev.vorstu.controllers;

import dev.vorstu.dto.StudentDTO;
import dev.vorstu.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.security.Principal;

@RestController
@RequestMapping("api/base")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO changeStudent(@RequestBody StudentDTO changingStudent, @PathVariable Long id) {
        return studentService.updateStudent(changingStudent, id);
    }


    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudentById(id);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO createStudent(@RequestBody StudentDTO newStudent) {
        return studentService.saveStudent(newStudent);
    }


    @GetMapping("students")
    public Page<StudentDTO> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection

    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentService.findAll(pageable);
    }

    @GetMapping(value = "students/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudentDTO> searchStudents(
            @RequestParam("filter") String filter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection)
    {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentService.findByFilter(filter, pageable);
    }


}