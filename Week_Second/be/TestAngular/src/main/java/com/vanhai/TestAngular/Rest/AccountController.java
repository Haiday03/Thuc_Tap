package com.vanhai.TestAngular.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanhai.TestAngular.DTO.JwtView;
import com.vanhai.TestAngular.Entity.Account;
import com.vanhai.TestAngular.Service.AccoutService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

	@Autowired
	private AccoutService accountService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtView> authenticate(@RequestBody Account account){
		return new ResponseEntity<JwtView>(accountService.login(account), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestBody Account account){
		if(accountService.register(account))
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		
	}
}
