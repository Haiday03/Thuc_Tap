package com.vanhai.TestAngular.Service.Impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public static final String UPLOAD_DIR = "src/main/java/com/vanhai/TestAngular/Images";
	
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
		newStudent.setDateJoin(new Date());
		
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
		if(student.getAvatar() != null) {
			if(oldStudent.getAvatar() != null)
				deleteImage(oldStudent.getAvatar());
			oldStudent.setAvatar(student.getAvatar());
		}
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
		
		if(oldStudent.getAvatar() != null)
			deleteImage(oldStudent.getAvatar());
		
		studentRepository.delete(oldStudent);
		return true;
	}
	
	private String saveFile(MultipartFile file) {
		if(file.isEmpty())
			throw new RuntimeException("Failed to store empty file");
		
		try {
			java.nio.file.Path directory = Paths.get(UPLOAD_DIR);
			if(!Files.exists(directory))
				Files.createDirectories(directory);
			
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			java.nio.file.Path destPath = directory.resolve(fileName);
			file.transferTo(destPath);
			
			return  fileName;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to store file", e);
		}
	}

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
	@Override
	public StudentDTO save(String name, String email, String description, Integer age, String address, MultipartFile avatar,
			UUID class_id) {
		// TODO Auto-generated method stub
		Student student = new Student();
		
		if(name != null && !name.trim().equals(""))
		{
			student.setName(name);
		}
		
		if(email != null && isValidEmail(email))
		{
			student.setEmail(email);
		}else
			return null;
		
		if(description != null && !description.trim().equals(""))
		{
			student.setDescription(description);
		}
		
		if(age != null && age > 0)
		{
			student.setAge(age);
		}
		
		if(address != null && !address.trim().equals(""))
		{
			student.setAddress(address);
		}
		
		if(avatar != null) {
			String fileName = saveFile(avatar);
			String fileUrl = "http://localhost:3471/api/student/images/" + fileName;
			student.setAvatar(fileUrl);
		}
		
		if(class_id != null) {
			ClassRoom classRoom = classRoomRepository.findById(class_id).orElse(null);
			if(classRoom != null)
				student.setClassroom(classRoom);
		}
		
		student.setDateJoin(new Date());
		return new StudentDTO(studentRepository.save(student));
	}
	
	@SuppressWarnings("null")
	public StudentDTO convert(UUID id, String name, String email, String description, Integer age, String address, MultipartFile avatar,
			UUID class_id) {
Student student = new Student();
		
		student.setId(id);
		if(name != null && !name.trim().equals(""))
		{
			student.setName(name);
		}
		
		if(email != null && isValidEmail(email))
		{
			student.setEmail(email);
		}
		
		if(description != null && !description.trim().equals(""))
		{
			student.setDescription(description);
		}
		
		if(age != null && age.intValue() > 0)
		{
			student.setAge(age.intValue());
		}else
			student.setAge(0);
		
		if(address != null && !address.trim().equals(""))
		{
			student.setAddress(address);
		}
		
		if(avatar != null) {
			String fileName = saveFile(avatar);
			String fileUrl = "http://localhost:3471/api/student/images/" + fileName;
			student.setAvatar(fileUrl);
		}
		
		if(class_id != null) {
			ClassRoom classRoom = classRoomRepository.findById(class_id).orElse(null);
			if(classRoom != null)
				student.setClassroom(classRoom);
		}
		
		return new StudentDTO(student);
	}

	@Override
	public boolean checkEmailExists(String email) {
		// TODO Auto-generated method stub
		return studentRepository.existsByEmail(email);
	}

	@Override
	public boolean deleteImage(String fileName) {
		// TODO Auto-generated method stub
		try {
			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName.substring(41)).normalize();
			Files.deleteIfExists(filePath);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

}
