package com.demo.day_one.Rest;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import javax.crypto.SealedObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.DTO.StudentDTO;
import com.demo.day_one.Entity.SearchObject;
import com.demo.day_one.Entity.Student;
import com.demo.day_one.Service.StudentDynamicServices;
import com.demo.day_one.Service.StudentService;

import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentDynamicServices studentDynamicServices;
		
    private static final Logger logger = (Logger) LoggerFactory.getLogger(StudentController.class);

	@GetMapping("/find-all")
	public ResponseEntity<Set<StudentDTO>> getAllStudent(){
		Set<StudentDTO> res = studentService.getAllStudent();
		logger.info("Found {} students", res.size());

		if(res.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<StudentDTO> save(@RequestBody Student student){
		StudentDTO studentDTO = studentService.save(student);
		if(studentDTO == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<StudentDTO> update(@RequestBody Student student){
		StudentDTO studentDTO = studentService.update(student);
		if(studentDTO == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("id")UUID id){
		if(!studentService.delete(id))
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@GetMapping("/find-student-by-name/{name}")
	public ResponseEntity<Set<StudentDTO>> findAllByName(@PathVariable("name")String name){
		Set<StudentDTO> res = studentService.findAllStudentByName(name);
		
		if(res == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Set<StudentDTO>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/find-by-date")
	public ResponseEntity<Set<StudentDTO>> findAllByAboutBirthDate(@RequestParam("start")Date start, @RequestParam("end")Date end){
        Set<StudentDTO> res = studentService.getAllStudentAboutBirthDate(start, end);
        if(res == null || res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/paging-student/{point}")
	public ResponseEntity<Set<StudentDTO>> pagingStudentByPoint(@PathVariable("point")float point, @RequestBody SearchObject sealedObject){
		Set<StudentDTO> res = studentService.pagingStudentByGPA(point, PageRequest.of(sealedObject.getPageNumber(), sealedObject.getPageSize()));
		
		if(res.isEmpty() || res == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Set<StudentDTO>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/find-criteria")
	public ResponseEntity<Set<StudentDTO>> findCriteria(@RequestBody SearchRequest searchRequest,
														@RequestParam("pageNumber")int pageNumber,
														@RequestParam("pageSize")int pageSize){
		//Set<StudentDTO> res = studentDynamicServices.findByCriteria(searchRequest, PageRequest.of(pageNumber, pageSize));
		//Set<StudentDTO> res = studentService.findCriteria(searchRequest, PageRequest.of(pageNumber, pageSize));
		Set<StudentDTO> res = studentService.findCriteriaByProceduce(searchRequest, PageRequest.of(pageNumber, pageSize));
		if(res == null || res.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Set<StudentDTO>>(res, HttpStatus.OK);
	}
	
	@PutMapping("/enroll-course")
	public ResponseEntity<StudentDTO> enrollCourse(@RequestParam("student_id")UUID student_id, @RequestParam("course_id")UUID course_id){
		StudentDTO studentDTO = studentService.enrollCourse(student_id, course_id);
		if(studentDTO == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
	}
	
	@GetMapping("/find-student-by-course/{id}")
	public ResponseEntity<Set<StudentDTO>> findStudentsByCourse(@PathVariable("id")UUID courseId){
		Set<StudentDTO> res = studentService.findStudentsByCourse(courseId);
		if(res == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Set<StudentDTO>>(res, HttpStatus.OK);
	}
	
}
