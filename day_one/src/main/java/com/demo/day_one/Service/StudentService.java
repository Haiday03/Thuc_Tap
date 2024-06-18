package com.demo.day_one.Service;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

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
	
}
