package com.demo.day_one.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.day_one.Entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>{

}
