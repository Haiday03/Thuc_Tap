package com.demo.day_one.DTO;

import java.util.Set;
import java.util.UUID;

import com.demo.day_one.Entity.ClassRoom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRoomDTO {

	private UUID id;
	private String className;
	private String address;
	private String description;
	private Set<StudentDTO> students;
	
	public ClassRoomDTO(ClassRoom entity) {
		this.id = entity.getId();
		this.className = entity.getClassName();
		this.address = entity.getAddress();
		this.description = entity.getDescription();
	}
}
