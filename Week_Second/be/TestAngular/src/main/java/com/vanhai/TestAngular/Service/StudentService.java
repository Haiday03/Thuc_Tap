package com.vanhai.TestAngular.Service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.vanhai.TestAngular.DTO.SearchRequest;
import com.vanhai.TestAngular.DTO.StudentDTO;

public interface StudentService {

	public Page<StudentDTO> findAllByCriteria(SearchRequest searchRequest, Pageable pageable);
	
	public StudentDTO add(StudentDTO student);
	
	public StudentDTO update(StudentDTO student);
	
	public boolean delelte(UUID id);
	
	public StudentDTO save(String name, String email, String description, Integer age, String address, MultipartFile avatar, UUID class_id);
	
	public boolean checkEmailExists(String email);
	
	public boolean deleteImages(String fileName);
}
