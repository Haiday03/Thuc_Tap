package com.demo.day_one.Service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.DTO.StudentDTO;
import com.demo.day_one.Entity.Student;
import com.demo.day_one.Repository.StudentDynamicRepository;
import com.demo.day_one.Service.StudentDynamicServices;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class StudentDynamicServicesImpl implements StudentDynamicServices{

	@Autowired
	private StudentDynamicRepository studentDynamicRepository;
	
	
	@Override
	public Set<StudentDTO> findByCriteria(SearchRequest searchRequest, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Student> page = studentDynamicRepository.findAll(new Specification<Student>() {
			
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<>();
                if(searchRequest.address != null && !searchRequest.address.isBlank())
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("address"), "%" + searchRequest.address + "%")));
				
                if(searchRequest.birthDateStart != null && searchRequest.birthDateEnd != null)
					predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("birthDate"), searchRequest.birthDateStart, searchRequest.birthDateEnd)));
                
                if(searchRequest.description != null && !searchRequest.description.isBlank())
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%" + searchRequest.description + "%")));
                
                if(searchRequest.email != null && !searchRequest.email.isBlank())
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("email"),"%" + searchRequest.email + "%")));
                
                if(searchRequest.gpa != 0)
                	predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("gpa"), searchRequest.gpa)));

                if(searchRequest.name != null && !searchRequest.name.isBlank())
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"),"%" + searchRequest.name + "%")));
                
                if(searchRequest.numberPhone != null && !searchRequest.numberPhone.isBlank())
                	predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("numberPhone"),"%" + searchRequest.name + "%")));

                
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	
		page.getTotalElements();
		page.getTotalPages();
		
		return page.getContent().stream().map(StudentDTO::new).collect(Collectors.toSet());
	}

}
