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
}
