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
		
		if(searchRequest != null)
			jpql.append("WHERE ");
		
		boolean test = false;
		
		if(searchRequest.address != null && !searchRequest.address.isBlank()) {
			jpql.append("s.address LIKE :address ");
			test = true;
		}
		
		if(searchRequest.description != null && !searchRequest.description.isBlank()) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.description LIKE :description ");
			test = true;
		}
		
		if(searchRequest.email != null && !searchRequest.email.isBlank()) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.email LIKE :email ");
			test = true;
		}
		
		if(searchRequest.name != null && !searchRequest.name.isBlank()) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.name LIKE :name ");
			test = true;
		}
		
		if(searchRequest.numberPhone != null && !searchRequest.numberPhone.isBlank()) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.numberPhone LIKE :numberPhone ");
			test = true;
		}
		
		if(searchRequest.numberPhone != null && !searchRequest.numberPhone.isBlank()) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.numberPhone LIKE :numberPhone ");
			test = true;
		}
		
		if(searchRequest.birthDateStart != null && searchRequest.birthDateEnd != null) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.birthDate BETWEEN :birthDateStart AND :birthDateEnd ");
			test = true;
		}
		
		if(searchRequest.gpa != 0) {
			if(test)
				jpql.append("AND ");
			jpql.append("s.gpa >= :gpa");
		}
		

	    TypedQuery<Student> query = entityManager.createQuery(jpql.toString().trim(), Student.class);
	    
		if(searchRequest.address != null && !searchRequest.address.isBlank())
			query.setParameter("address", "%" + searchRequest.address + "%");
		
		if(searchRequest.description != null && !searchRequest.description.isBlank())
		    query.setParameter("description", "%" + searchRequest.description + "%");
		    
		if(searchRequest.email != null && !searchRequest.email.isBlank())
		    query.setParameter("email", "%" + searchRequest.email + "%");
		    
		if(searchRequest.name != null && !searchRequest.name.isBlank())
		    query.setParameter("name", "%" + searchRequest.name + "%");
		    
		if(searchRequest.numberPhone != null && !searchRequest.numberPhone.isBlank())
		    query.setParameter("numberPhone", "%" + searchRequest.numberPhone + "%");
		    
		if(searchRequest.birthDateStart != null && searchRequest.birthDateEnd != null) {				
			query.setParameter("birthDateStart", searchRequest.birthDateStart);
			query.setParameter("birthDateEnd", searchRequest.birthDateEnd);
		}
		    
		if(searchRequest.gpa != 0)
		    query.setParameter("gpa", searchRequest.gpa);

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
