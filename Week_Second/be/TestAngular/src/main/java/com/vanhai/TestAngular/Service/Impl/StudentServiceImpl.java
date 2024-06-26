package com.vanhai.TestAngular.Service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vanhai.TestAngular.DTO.SearchRequest;
import com.vanhai.TestAngular.DTO.StudentDTO;
import com.vanhai.TestAngular.Entity.ClassRoom;
import com.vanhai.TestAngular.Entity.Student;
import com.vanhai.TestAngular.Repository.ClassRoomRepository;
import com.vanhai.TestAngular.Repository.StudentRepository;
import com.vanhai.TestAngular.Repository.StudentRepositoryEntityManager;
import com.vanhai.TestAngular.Service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ClassRoomRepository classRoomRepository;
	
	@Autowired
	private StudentRepositoryEntityManager studentRepositoryEntityManager;
	
	@Override
	public Page<StudentDTO> findAllByCriteria(SearchRequest searchRequest, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Student> res = studentRepositoryEntityManager.findByCriteria(searchRequest, pageable);
		List<StudentDTO> set = res.stream().map(StudentDTO::new)
								.collect(Collectors.toList());
		return new PageImpl<StudentDTO>(set, pageable, res.getTotalElements());
	}

	@Override
	public StudentDTO add(StudentDTO student) {
		// TODO Auto-generated method stub
		if(student == null)
			return null;
		Student newStudent = new Student();
		
		newStudent.setName(student.getName());
		newStudent.setEmail(student.getEmail());
		newStudent.setDescription(student.getDescription());
		newStudent.setAge(student.getAge());
		newStudent.setAddress(student.getAddress());
		newStudent.setAvatar(student.getAvatar());
		if(student.getClassroom() != null) {
			ClassRoom classRoom = classRoomRepository.findById(student.getClassroom().getId()).orElse(null);
			if(classRoom != null)
				newStudent.setClassroom(classRoom);
		}
		
		return new StudentDTO(studentRepository.save(newStudent));
	}

	@Override
	public StudentDTO update(StudentDTO student) {
		// TODO Auto-generated method stub
		if(student == null)
			return null;
		Student oldStudent = studentRepository.findById(student.getId()).orElse(null);
		if(oldStudent == null)
			return null;
		
		oldStudent.setName(student.getName());
		oldStudent.setEmail(student.getEmail());
		oldStudent.setDescription(student.getDescription());
		oldStudent.setAge(student.getAge());
		oldStudent.setAddress(student.getAddress());
		oldStudent.setAvatar(student.getAvatar());
		if(student.getClassroom() != null) {
			ClassRoom classRoom = classRoomRepository.findById(student.getClassroom().getId()).orElse(null);
			if(classRoom != null)
				oldStudent.setClassroom(classRoom);
		}
		
		return new StudentDTO(studentRepository.saveAndFlush(oldStudent));
	}

	@Override
	public boolean delelte(UUID id) {
		// TODO Auto-generated method stub
		Student oldStudent = studentRepository.findById(id).orElse(null);
		if(oldStudent == null)
			return false;
		studentRepository.delete(oldStudent);
		return true;
	}

}
