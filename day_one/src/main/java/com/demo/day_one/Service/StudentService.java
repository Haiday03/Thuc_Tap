package com.demo.day_one.Service;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.DTO.StudentDTO;
import com.demo.day_one.Entity.Student;

public interface StudentService {

	public Set<StudentDTO> getAllStudent();
	
	public StudentDTO save(Student student);
	
	public StudentDTO update(Student student);
	
	public boolean delete(UUID id);
	
	public Set<StudentDTO> findAllStudentByName(String name);
	
	public Set<StudentDTO> getAllStudentAboutBirthDate(Date start, Date end);
	
	public Set<StudentDTO> pagingStudentByGPA(float point, Pageable pageable);
	
	public Set<StudentDTO> findCriteria(SearchRequest searchRequest, Pageable pageable);
	
	public Set<StudentDTO> findCriteriaByProceduce(SearchRequest searchRequest, Pageable pageable);
	
	public StudentDTO enrollCourse(UUID student_id, UUID course_id);
	
	public Set<StudentDTO> findStudentsByCourse(UUID courseId);
}
