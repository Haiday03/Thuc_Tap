package com.vanhai.TestAngular.DTO;

import java.sql.Date;
import java.util.UUID;

import com.vanhai.TestAngular.Entity.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

	private UUID id;
	private String name;
	private String email;
	private String description;
	private int age;
	private String address;
	private String avatar;
	private java.util.Date dateJoin;
	private ClassRoomDTO classroom;
	
	public StudentDTO(Student entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.description = entity.getDescription();
		this.age = entity.getAge();
		this.address = entity.getAddress();
		this.avatar = entity.getAvatar();
		this.dateJoin = entity.getDateJoin();
		
		if(entity.getClassroom() != null)
			this.classroom = new ClassRoomDTO(entity.getClassroom());
	}
}
