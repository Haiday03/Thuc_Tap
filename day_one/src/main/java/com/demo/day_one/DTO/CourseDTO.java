package com.demo.day_one.DTO;

import java.util.Set;
import java.util.UUID;

import com.demo.day_one.Entity.Course;

public class CourseDTO {
	
	private UUID id;
	private String courseName;
	private String description;
	private Set<StudentDTO> students;
	
	public CourseDTO(Course entity) {
		this.id = entity.getId();
		this.courseName = entity.getCourseName();
		this.description = entity.getDescription();
	}
}
