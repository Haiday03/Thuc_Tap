package com.vanhai.TestAngular.DTO;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

	private UUID id;
	
	private String name;
	
	private String email;
	
	private int age;
	
	private String address;
	
	private String className;
}
