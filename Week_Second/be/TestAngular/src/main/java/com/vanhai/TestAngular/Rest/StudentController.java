package com.vanhai.TestAngular.Rest;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vanhai.TestAngular.DTO.SearchRequest;
import com.vanhai.TestAngular.DTO.StudentDTO;
import com.vanhai.TestAngular.Service.StudentService;
import com.vanhai.TestAngular.Service.Impl.StudentServiceImpl;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@PostMapping("/find-criteria")
	public ResponseEntity<Page<StudentDTO>> getStudents(@RequestBody SearchRequest searchRequest, @RequestParam("pageSize")int pageSize, @RequestParam("pageNumber")int pageNumber){
		
		Page<StudentDTO> res = studentService.findAllByCriteria(searchRequest, PageRequest.of(pageNumber - 1, pageSize));
		if(res == null || res.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Page<StudentDTO>>(res, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<StudentDTO> saveStudent(@RequestParam(value = "name", required = true)String name,
												@RequestParam(value = "email", required = true)String email,
												@RequestParam(value = "description", required = false)String description,
												@RequestParam(value = "age", required = false) Integer age,
												@RequestParam(value = "address", required = false)String address,
												@RequestParam(value = "avatar", required = false) MultipartFile file,
												@RequestParam(value = "class_id", required = false) UUID class_id
													){
		StudentDTO newStudent = studentService.save(name, email, description, age, address, file, class_id);
		if(newStudent == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<StudentDTO>(newStudent, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<StudentDTO> updateStudent(@RequestParam("id")UUID id,
													@RequestParam(value = "name", required = true)String name,
													@RequestParam(value = "email", required = true)String email,
													@RequestParam(value = "description", required = false)String description,
													@RequestParam(value = "age", required = false) Integer age,
													@RequestParam(value = "address", required = false)String address,
													@RequestParam(value = "avatar", required = false) MultipartFile file,
													@RequestParam(value = "class_id", required = false) UUID class_id
													){
		StudentDTO newStudent = studentService.convert(id, name, email, description, age, address, file, class_id);
		StudentDTO oldStudent = studentService.update(newStudent);
		
		if(oldStudent == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<StudentDTO>(newStudent, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteStudent(@PathVariable UUID id){
		if(studentService.delelte(id))
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/images/{fileName}")
	public ResponseEntity<org.springframework.core.io.Resource> serveFile(@PathVariable String fileName){
		try {
			Path filePath = Paths.get(StudentServiceImpl.UPLOAD_DIR).resolve(fileName).normalize();
			org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
			
			if(!resource.exists())
				return ResponseEntity.notFound().build();
			
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
		} catch (MalformedURLException e) {
			// TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/check-email/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
		if(studentService.checkEmailExists(email))
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
}
