package com.demo.day_one.Repository.Impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.Entity.Student;
import com.demo.day_one.Repository.StudentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO{

	@Autowired
	private EntityManager entityManager;

	@Override
	public org.springframework.data.domain.Page<Student> findAllByCriteria(SearchRequest searchRequest, Pageable pageable) {
	    StringBuilder jpql = new StringBuilder();
	    jpql.append("SELECT s FROM Student s WHERE 1 = 1 ");
	    	    
	    if (searchRequest.getAddress() != null && !searchRequest.getAddress().isBlank()) {
	        jpql.append("AND s.address LIKE :address ");
	    }
	    
	    if (searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank()) {
	        jpql.append("AND s.description LIKE :description ");
	    }
	    
	    if (searchRequest.getEmail() != null && !searchRequest.getEmail().isBlank()) {
	        jpql.append("AND s.email LIKE :email ");
	    }
	    
	    if (searchRequest.getName() != null && !searchRequest.getName().isBlank()) {
	        jpql.append("AND s.name LIKE :name ");
	    }
	    
	    if (searchRequest.getNumberPhone() != null && !searchRequest.getNumberPhone().isBlank()) {
	        jpql.append("AND s.numberPhone LIKE :numberPhone ");
	    }
	    
	    if (searchRequest.getBirthDateStart() != null) {
	        jpql.append("AND s.birthDate BETWEEN :birthDateStart AND :birthDateEnd ");
	    }
	    
	    if (searchRequest.getGpa() != 0) {
	            jpql.append("AND ");
	        jpql.append("s.gpa >= :gpa ");
	    }
	    
	    if(searchRequest.getClassName() != null && !searchRequest.getClassName().isBlank())
	    {
	    	jpql.append("AND s.classroom is not null AND s.classroom.className LIKE :classroom ");
	    }
	    
	    jpql.append("ORDER BY s.id DESC");

	    TypedQuery<Student> query = entityManager.createQuery(jpql.toString().trim(), Student.class);
	    
	    if (searchRequest.getAddress() != null && !searchRequest.getAddress().isBlank())
	        query.setParameter("address", "%" + searchRequest.getAddress() + "%");
	    
	    if (searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank())
	        query.setParameter("description", "%" + searchRequest.getDescription() + "%");
	    
	    if (searchRequest.getEmail() != null && !searchRequest.getEmail().isBlank())
	        query.setParameter("email", "%" + searchRequest.getEmail() + "%");
	    
	    if (searchRequest.getName() != null && !searchRequest.getName().isBlank())
	        query.setParameter("name", "%" + searchRequest.getName() + "%");
	    
	    if (searchRequest.getNumberPhone() != null && !searchRequest.getNumberPhone().isBlank())
	        query.setParameter("numberPhone", "%" + searchRequest.getNumberPhone() + "%");
	    
	    if (searchRequest.getBirthDateStart() != null && searchRequest.getBirthDateEnd() != null) {                
	        query.setParameter("birthDateStart", searchRequest.getBirthDateStart());
	        query.setParameter("birthDateEnd", searchRequest.getBirthDateEnd());
	    }else if(searchRequest.getBirthDateStart() != null) {
	        query.setParameter("birthDateStart", searchRequest.getBirthDateStart());
	        query.setParameter("birthDateEnd", Date.valueOf(LocalDate.now()));
	    }
	    
	    if (searchRequest.getGpa() != 0)
	        query.setParameter("gpa", searchRequest.getGpa());
	    
	    if(searchRequest.getClassName() != null && !searchRequest.getClassName().isBlank())
	    {
	    	query.setParameter("classroom", searchRequest.getClassName());
	    }
	    
	    int totalElements = query.getResultList().size();
	    
	    query.setFirstResult((int)pageable.getOffset());
	    query.setMaxResults(pageable.getPageSize());
	    
	    List<Student> li = query.getResultList();
	    
	    org.springframework.data.domain.Page<Student> page = new PageImpl<Student>(li, pageable, totalElements);
	    
	    return page;
	}



	@Override
	public Student findById(UUID id) {
		// TODO Auto-generated method stub
		String jpql = "SELECT k FROM Student k.id = :id";
		TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Student add(Student student) {
		// TODO Auto-generated method stub
		entityManager.persist(student);
		return student;
	}

	@Override
	public Student update(Student student) {
		// TODO Auto-generated method stub
		entityManager.merge(student);
		return student;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		Student s = entityManager.find(Student.class, id);
		entityManager.remove(s);
	}

}
