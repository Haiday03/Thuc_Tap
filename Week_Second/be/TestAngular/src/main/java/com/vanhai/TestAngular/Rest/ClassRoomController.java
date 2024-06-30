package com.vanhai.TestAngular.Rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.vanhai.TestAngular.DTO.ClassRoomDTO;
import com.vanhai.TestAngular.Service.ClassRoomService;

@RestController
@RequestMapping("/api/class-room")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClassRoomController {

	@Autowired
	private ClassRoomService classRoomService;
	
	@GetMapping("/find-all")
	public ResponseEntity<Page<ClassRoomDTO>> getAllClassRoom(@RequestParam("pageSize")int pageSize, @RequestParam("pageNumber")int pageNumber){
		Page<ClassRoomDTO> res = classRoomService.findAll(PageRequest.of(pageNumber, pageSize));
		if(res == null || res.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Page<ClassRoomDTO>>(res, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ClassRoomDTO> saveClassRoom(@RequestBody ClassRoomDTO classRoomDTO){
		ClassRoomDTO newClassRoomDTO = classRoomService.add(classRoomDTO);
		if(newClassRoomDTO == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ClassRoomDTO>(newClassRoomDTO, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ClassRoomDTO> updateClassRoom(@RequestBody ClassRoomDTO classRoomDTO){
		ClassRoomDTO oldClassRoomDTO = classRoomService.update(classRoomDTO);
		if(oldClassRoomDTO == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ClassRoomDTO>(oldClassRoomDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteClassRoom(@PathVariable("id")UUID id){
		if(classRoomService.delete(id))
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClassRoomDTO> findById(@PathVariable("id")UUID id){
		ClassRoomDTO res = classRoomService.findById(id);
		if(res == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ClassRoomDTO>(res, HttpStatus.OK);
	}
	
	@GetMapping("/remove-student/{id}")
	public ResponseEntity<Boolean> removeStudentInClassRoom(@PathVariable("id")UUID studentId){
		if(classRoomService.removeStudentInClass(studentId))
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/add-student")
	public ResponseEntity<Boolean> addStudentToClass(@RequestParam("student_id") UUID studentId, @RequestParam("class_id") UUID classId) {
	    if (classRoomService.addStudentToClass(studentId, classId)) {
	        return new ResponseEntity<>(true, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
	}

	
}
