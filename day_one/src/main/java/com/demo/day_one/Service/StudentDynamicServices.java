package com.demo.day_one.Service;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.DTO.StudentDTO;
import com.demo.day_one.Entity.Student;

public interface StudentDynamicServices {

	public Set<StudentDTO> findByCriteria(SearchRequest searchRequest, Pageable pageable);
}
