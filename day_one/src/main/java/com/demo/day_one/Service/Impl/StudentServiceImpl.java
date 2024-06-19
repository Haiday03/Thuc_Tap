package com.demo.day_one.Service.Impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.day_one.DTO.SearchRequest;
import com.demo.day_one.DTO.StudentDTO;
import com.demo.day_one.Entity.Student;
import com.demo.day_one.Repository.StudentDAO;
import com.demo.day_one.Repository.StudentRepository;
import com.demo.day_one.Service.StudentService;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepository;
	
	//use JPA Entity Management
	@Autowired
	private StudentDAO studentDAO;

	@Override
	public Set<StudentDTO> getAllStudent() {
		   List<Student> students = studentRepository.findAll();
		   Set<StudentDTO> studentDTOs = students.stream()
		            .map(StudentDTO::new)
		            .collect(Collectors.toSet());
		    return studentDTOs;
	}


	@Override
	public StudentDTO save(Student student) {
		// TODO Auto-generated method stub
		Student newStudent = studentRepository.save(student);
		
		return newStudent == null ? null : new StudentDTO(newStudent);
	}

	public StudentDTO update(Student student) {
	    // Lấy đối tượng Student cũ từ cơ sở dữ liệu theo id của student mới
	    Student oldStudent = studentRepository.findById(student.getId()).orElse(null);
	    
	    // Nếu không tìm thấy đối tượng cũ, trả về null
	    if (oldStudent == null) {
	        return null;
	    }
	    
	    // Cập nhật các trường của đối tượng cũ bằng giá trị từ đối tượng mới
	    oldStudent.setAddress(student.getAddress());
	    oldStudent.setBirthDate(student.getBirthDate());
	    oldStudent.setName(student.getName());
	    oldStudent.setNumberPhone(student.getNumberPhone());
	    oldStudent.setEmail(student.getEmail());
	    oldStudent.setDescription(student.getDescription());
	    oldStudent.setGpa(student.getGpa());
	    oldStudent.setGender(student.getGender());
	    
	    // Lưu đối tượng cũ đã được cập nhật vào cơ sở dữ liệu
	    return new StudentDTO(studentRepository.saveAndFlush(oldStudent));
	}


	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		Student oldStudent = studentRepository.findById(id).orElse(null);
		if(oldStudent == null)
			return false;
		studentRepository.delete(oldStudent);
		return true;
	}

	@Override
	public Set<StudentDTO> findAllStudentByName(String name) {
		// TODO Auto-generated method stub
		
		List<Student> li = studentRepository.findStudentByName(name);
		Set<StudentDTO> res = new HashSet<>();
		li.stream().map(StudentDTO::new).forEach(res::add);
		return res.isEmpty() ? null : res; 
	}

	@Override
	public Set<StudentDTO> getAllStudentAboutBirthDate(Date start, Date end) {
		// TODO Auto-generated method stub
		List<Student> li = studentRepository.findStudentAboutBirthDate(start, end);
		Set<StudentDTO> res = new HashSet<>();
		li.stream().map(StudentDTO::new).forEach(res::add);
		return res.isEmpty() ? null : res; 
	}

	@Override
	public Set<StudentDTO> pagingStudentByGPA(float point, Pageable pageable) {
		// TODO Auto-generated method stub
		List<Student> li = studentRepository.pagingStudentByGPA(point, pageable);
		Set<StudentDTO> res = new HashSet<>();
		li.stream().map(StudentDTO::new).forEach(res::add);
		return res.isEmpty() ? null : res; 
	}


	@Override
	public Set<StudentDTO> findCriteria(SearchRequest searchRequest, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Student> li = studentDAO.findAllByCriteria(searchRequest, pageable);
		Set<StudentDTO> res = new TreeSet<StudentDTO>((s1, s2) -> s1.getId().compareTo(s2.getId()));
		li.stream().map(StudentDTO::new).forEach(res::add);
		return res;
	}


	@Override
	@Transactional
	public Set<StudentDTO> findCriteriaByProceduce(SearchRequest searchRequest, Pageable pageable) {
		// TODO Auto-generated method stub
		StringBuilder strCondition = new StringBuilder();
		
		if(searchRequest.getAddress() != null && !searchRequest.getAddress().isBlank())
		{
			strCondition.append("AND address = " + searchRequest.getAddress() + " ");
		}
        if(searchRequest.getBirthDateStart() != null)
        {
        	if(searchRequest.getBirthDateEnd() == null) {
    			strCondition.append("AND birth_date BETWEEN " + searchRequest.getBirthDateStart() + " AND " + Date.valueOf(LocalDate.now()));
        	}else {
    			strCondition.append("AND birth_date BETWEEN " + searchRequest.getBirthDateStart() + " AND " + searchRequest.getBirthDateEnd());
        	}
        }
        if(searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank())
        {
			strCondition.append("AND description = " + searchRequest.getDescription() + " ");
        }
        if(searchRequest.getEmail() != null && !searchRequest.getEmail().isBlank())
        {
			strCondition.append("AND email = " + searchRequest.getEmail() + " ");
        }
        if(searchRequest.getGpa() != 0)
        {
			strCondition.append("AND gpa >= " + searchRequest.getGpa() + " ");
        }
        if(searchRequest.getName() != null && !searchRequest.getName().isBlank())
        {
			strCondition.append("AND name = " + searchRequest.getName() + " ");
        }
        if(searchRequest.getNumberPhone() != null && !searchRequest.getNumberPhone().isBlank())
        {
			strCondition.append("AND number_phone = " + searchRequest.getNumberPhone() + " ");
        }
        
        List<Student> li = studentRepository.findCriteria(strCondition.toString(), pageable.getPageSize(), pageable.getPageNumber());
		Set<StudentDTO> res = new TreeSet<StudentDTO>((s1, s2) -> s1.getId().compareTo(s2.getId()));
		li.stream().map(StudentDTO::new).forEach(res::add);
		return res;
	}

}
