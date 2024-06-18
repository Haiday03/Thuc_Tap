package com.demo.day_one.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.Entity.*;

public interface StudentDAO {

	public List<Student> findAllByCriteria(SearchRequest searchRequest, Pageable pageable);
	
	public Student findById(UUID id);
	
	public Student add(Student student);
	
	public Student update(Student student);
	
	public void delete(UUID id);
}
