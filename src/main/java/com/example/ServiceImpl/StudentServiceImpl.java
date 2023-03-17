package com.example.ServiceImpl;

import com.example.dto.StudentDTO;
import com.example.dto.StudentFindDto;
import com.example.event.EmailEvent;
import com.example.model.Student;
import com.example.publisher.EmailPublisher;
import com.example.repository.StudentRepo;
import com.example.services.StudentService;
import com.example.utils.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    StudentRepo studentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EntityManager entityManager ;


    public ResponseEntity<Object> addStudent(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        StudentDTO studResponseDto = new StudentDTO();
        if (student != null) {
            studentRepo.save(student);
            studResponseDto = modelMapper.map(student, StudentDTO.class);
        }

        if (studResponseDto != null) {
            publishEvent();
            return ResponseHandler.generateResponse("Created",HttpStatus.CREATED,studResponseDto) ;
        }
        return ResponseHandler.generateResponse("Not Created",HttpStatus.BAD_REQUEST,null);
    }

    @Autowired
    EmailPublisher emailPublisher;

//    @GetMapping("/notify/event")
    public void publishEvent(){
        emailPublisher
                .publishEmailEvent
                        (new EmailEvent("Employee added."));

        emailPublisher
                .publishMsgEvent
                        ("Exception occurred.");
    }

    @Override
    public ResponseEntity<Object> deleteStudent(Integer id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        if(studentOptional.isPresent()){
            studentRepo.delete(studentOptional.get());
            return ResponseHandler.generateResponse("Deleted Successfully",HttpStatus.OK,null);
        }
        return ResponseHandler.generateResponse("Fail to Delete",HttpStatus.BAD_REQUEST,null);
    }

    @Override
    public ResponseEntity<Object> findAll(StudentFindDto studentFindDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        if(studentFindDto.id!=null && studentFindDto.mobile!=null && studentFindDto.name!=null) {
            if (!StringUtils.isEmpty(studentFindDto.id)) {
                Predicate idPredicates = cb.equal(root.get("id"), studentFindDto.id);
                query.where(cb.or(
                        idPredicates
                ));
            }
            if (!StringUtils.isEmpty(studentFindDto.name)) {
                Predicate namePredicates = cb.like(root.get("name"), studentFindDto.name);
                query.where(cb.or(
                        namePredicates
                ));
            }
            if (!StringUtils.isEmpty(studentFindDto.mobile)) {
                Predicate mobilePredicates = cb.like(root.get("mobile"), studentFindDto.mobile);
                query.where(cb.or(
                        mobilePredicates
                ));
            }
            return ResponseHandler.generateResponse("Find All Students Successfully", HttpStatus.OK, entityManager.createQuery(query.select(root)).getResultList());
        }else{
            List<Student> stList = studentRepo.findAll();
            return ResponseHandler.generateResponse(stList.size() == 0 ? "No data found" : "Find All Students Successfully", HttpStatus.OK, stList);
        }

    }
}