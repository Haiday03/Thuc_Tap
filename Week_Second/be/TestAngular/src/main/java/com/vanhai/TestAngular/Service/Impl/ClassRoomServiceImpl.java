package com.vanhai.TestAngular.Service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vanhai.TestAngular.DTO.ClassRoomDTO;
import com.vanhai.TestAngular.DTO.StudentDTO;
import com.vanhai.TestAngular.Entity.ClassRoom;
import com.vanhai.TestAngular.Repository.ClassRoomRepository;
import com.vanhai.TestAngular.Service.ClassRoomService;

@Service
public class ClassRoomServiceImpl implements ClassRoomService{

	@Autowired
	private ClassRoomRepository classRoomRepository;
		
	@Override
	public org.springframework.data.domain.Page<ClassRoomDTO> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		org.springframework.data.domain.Page<ClassRoom> res = classRoomRepository.findAll(pageable); 
		List<ClassRoomDTO> li = res.stream().map(x -> {
			ClassRoomDTO newClas = new ClassRoomDTO(x);
			newClas.setStudents(x.getStudents().stream().map(StudentDTO::new).collect(Collectors.toSet()));
			return newClas;
		}).collect(Collectors.toList());
		return new PageImpl<ClassRoomDTO>(li, pageable, res.getTotalElements());
	}

	@Override
	public ClassRoomDTO add(ClassRoomDTO classRoom) {
		// TODO Auto-generated method stub
		if(classRoom == null)
			return null;
		
		ClassRoom newClass = new ClassRoom();
		
		newClass.setClassName(classRoom.getClassName());
		newClass.setEmail(classRoom.getEmail());
		newClass.setEstablishmentDate(classRoom.getEstablishmentDate());
		
		return new ClassRoomDTO(classRoomRepository.save(newClass));
	}

	@Override
	public ClassRoomDTO update(ClassRoomDTO classRoom) {
		// TODO Auto-generated method stub
		if(classRoom == null)
			return null;
		
		ClassRoom oldClass = classRoomRepository.findById(classRoom.getId()).orElse(null);
		
		if(oldClass == null)
			return null;
		
		oldClass.setClassName(classRoom.getClassName());
		oldClass.setEmail(classRoom.getEmail());
		oldClass.setEstablishmentDate(classRoom.getEstablishmentDate());
		
		return new ClassRoomDTO(classRoomRepository.saveAndFlush(oldClass));
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		ClassRoom oldClass = classRoomRepository.findById(id).orElse(null);
		
		if(oldClass == null)
			return false;
		classRoomRepository.delete(oldClass);
		return true;
	}

}