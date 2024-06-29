package com.vanhai.TestAngular.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanhai.TestAngular.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>{

	@Query("SELECT s FROM Student s WHERE s.classroom.id = :id")
	List<Student> findByClassRoomId(@Param("id")UUID class_id);
}
