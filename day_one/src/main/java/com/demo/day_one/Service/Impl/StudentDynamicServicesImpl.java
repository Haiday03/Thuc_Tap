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
        Page<Student> page = studentDynamicRepository.findAll(new Specification<Student>() {
            
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                
                if(searchRequest.getAddress() != null && !searchRequest.getAddress().isBlank())
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("address"), "%" + searchRequest.getAddress() + "%")));
                
                if(searchRequest.getBirthDateStart() != null && searchRequest.getBirthDateEnd() != null)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.between(root.get("birthDate"), searchRequest.getBirthDateStart(), searchRequest.getBirthDateEnd())));
                
                if(searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank())
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%" + searchRequest.getDescription() + "%")));
                
                if(searchRequest.getEmail() != null && !searchRequest.getEmail().isBlank())
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("email"),"%" + searchRequest.getEmail() + "%")));
                
                if(searchRequest.getGpa() != 0)
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("gpa"), searchRequest.getGpa())));

                if(searchRequest.getName() != null && !searchRequest.getName().isBlank())
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"),"%" + searchRequest.getName() + "%")));
                
                if(searchRequest.getNumberPhone() != null && !searchRequest.getNumberPhone().isBlank())
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("numberPhone"),"%" + searchRequest.getNumberPhone() + "%")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    
        page.getTotalElements();
        page.getTotalPages();
        
        return page.getContent().stream().map(StudentDTO::new).collect(Collectors.toSet());
    }

}
