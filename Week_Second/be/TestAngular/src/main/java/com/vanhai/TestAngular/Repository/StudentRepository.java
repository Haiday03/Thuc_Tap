package com.vanhai.TestAngular.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanhai.TestAngular.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>{

}
