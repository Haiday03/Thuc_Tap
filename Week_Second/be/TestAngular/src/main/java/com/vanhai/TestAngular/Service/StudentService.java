package com.vanhai.TestAngular.Service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanhai.TestAngular.DTO.SearchRequest;
import com.vanhai.TestAngular.DTO.StudentDTO;

public interface StudentService {

	public Page<StudentDTO> findAllByCriteria(SearchRequest searchRequest, Pageable pageable);
	
	public StudentDTO add(StudentDTO student);
	
	public StudentDTO update(StudentDTO student);
	
	public boolean delelte(UUID id);
	
}
