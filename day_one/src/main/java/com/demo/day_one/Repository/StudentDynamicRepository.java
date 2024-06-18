package com.demo.day_one.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.day_one.Entity.Student;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface StudentDynamicRepository extends CrudRepository<Student, UUID>, JpaSpecificationExecutor<Student>{

}