package com.demo.day_one.Repository.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.Entity.Student;
import com.demo.day_one.Repository.StudentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class StudentDAOImpl implements StudentDAO{

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Student> findAllByCriteria(SearchRequest searchRequest, Pageable pageable) {
	    StringBuilder jpql = new StringBuilder();
	    jpql.append("SELECT s FROM Student s ");
	    
	    if (searchRequest != null)
	        jpql.append("WHERE ");
	    
	    boolean test = false;
	    
	    if (searchRequest.getAddress() != null && !searchRequest.getAddress().isBlank()) {
	        jpql.append("s.address LIKE :address ");
	        test = true;
	    }
	    
	    if (searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank()) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.description LIKE :description ");
	        test = true;
	    }
	    
	    if (searchRequest.getEmail() != null && !searchRequest.getEmail().isBlank()) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.email LIKE :email ");
	        test = true;
	    }
	    
	    if (searchRequest.getName() != null && !searchRequest.getName().isBlank()) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.name LIKE :name ");
	        test = true;
	    }
	    
	    if (searchRequest.getNumberPhone() != null && !searchRequest.getNumberPhone().isBlank()) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.numberPhone LIKE :numberPhone ");
	        test = true;
	    }
	    
	    if (searchRequest.getBirthDateStart() != null && searchRequest.getBirthDateEnd() != null) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.birthDate BETWEEN :birthDateStart AND :birthDateEnd ");
	        test = true;
	    }
	    
	    if (searchRequest.getGpa() != 0) {
	        if (test)
	            jpql.append("AND ");
	        jpql.append("s.gpa >= :gpa");
	    }
	    
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
	    }
	    
	    if (searchRequest.getGpa() != 0)
	        query.setParameter("gpa", searchRequest.getGpa());
	    
	    return query.getResultList();
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
