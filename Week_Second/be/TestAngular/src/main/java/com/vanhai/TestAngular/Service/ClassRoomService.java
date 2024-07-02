package com.vanhai.TestAngular.Service;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vanhai.TestAngular.DTO.ClassRoomDTO;

public interface ClassRoomService {

	public Page<ClassRoomDTO> findAll(Pageable pageable);
	
	public ClassRoomDTO add(ClassRoomDTO classRoom);
	
	public ClassRoomDTO update(ClassRoomDTO classRoom);
	
	public boolean delete(UUID id);
	
	public ClassRoomDTO findById(UUID id);
	
	public Boolean removeStudentInClass(UUID studentId);
	
	public Boolean addStudentToClass(UUID student_id, UUID class_id);
	
	public Boolean checkEmail(String email);
}
