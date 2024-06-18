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
	    String jpql = "SELECT s FROM Student s WHERE "
	            + "s.address LIKE :address "
	            + "AND s.description LIKE :description "
	            + "AND s.email LIKE :email "
	            + "AND s.name LIKE :name "
	            + "AND s.numberPhone LIKE :numberPhone "
	            + "AND s.birthDate BETWEEN :birthDateStart AND :birthDateEnd "
	            + "AND s.gpa >= :gpa";

	    TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
	    query.setParameter("address", "%" + searchRequest.address + "%");
	    query.setParameter("description", "%" + searchRequest.description + "%");
	    query.setParameter("email", "%" + searchRequest.email + "%");
	    query.setParameter("name", "%" + searchRequest.name + "%");
	    query.setParameter("numberPhone", "%" + searchRequest.numberPhone + "%");
	    query.setParameter("birthDateStart", searchRequest.birthDateStart);
	    query.setParameter("birthDateEnd", searchRequest.birthDateEnd);
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
