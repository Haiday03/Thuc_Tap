package com.demo.day_one.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.day_one.Entity.Student;

import jakarta.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>{

	//Method to find all student by name
	@Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
	public List<Student> findStudentByName(@Param("name")String name);
		
	@Query("SELECT s FROM Student s WHERE s.birthDate between :startDate AND  :endDate")
	public List<Student> findStudentAboutBirthDate(@Param("startDate")Date start, @Param("endDate")Date end);
	
	@Query("SELECT s FROM Student s WHERE s.gpa >= :point")
	public List<Student> pagingStudentByGPA(@Param("point")float point, Pageable pageable);
	
	@Procedure(procedureName = "find_criteria")
	@Transactional
	public List<Student> findCriteria(@Param("str_condition")String strCondition, @Param("page_size")int pageSize, @Param("page_number")int pageNumber);
}
