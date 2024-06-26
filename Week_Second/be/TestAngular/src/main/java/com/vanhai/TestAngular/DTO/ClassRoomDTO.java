package com.vanhai.TestAngular.DTO;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import com.vanhai.TestAngular.Entity.ClassRoom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomDTO {

	private UUID id;
	private String className;
	private String email;
	private Date establishmentDate;
	private Set<StudentDTO> students;
	
	public ClassRoomDTO(ClassRoom entity) {
		this.id = entity.getId();
		this.className = entity.getClassName();
		this.email = entity.getEmail();
		this.establishmentDate = entity.getEstablishmentDate();
	}
}
