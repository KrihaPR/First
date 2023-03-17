package com.example.services;

import com.example.dto.StudentDTO;
import com.example.dto.StudentFindDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface StudentService {

    ResponseEntity<Object> addStudent(StudentDTO studentDTO);

    ResponseEntity<Object> deleteStudent(Integer id);

    ResponseEntity<Object> findAll(StudentFindDto studentFindDto);
}
