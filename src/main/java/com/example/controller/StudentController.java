package com.example.controller;

import com.example.dto.StudentDTO;
import com.example.dto.StudentFindDto;
import com.example.event.EmailEvent;
import com.example.publisher.EmailPublisher;
import com.example.services.StudentService;
import com.example.utils.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @ApiOperation(value = "Add new student")
    @PostMapping("/add-update")
    public ResponseEntity<Object> createUpdate(@RequestBody StudentDTO studentDTO){
        return studentService.addStudent(studentDTO);
    }

    @ApiOperation(value = "Update student record")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }

    @ApiOperation(value = "Find student")
    @PostMapping("/findAll")
    public ResponseEntity<Object> find(@RequestBody (required=false) StudentFindDto studentFindDto){
        return studentService.findAll(studentFindDto);
    }




}
