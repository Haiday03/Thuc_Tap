package com.demo.day_one.DTO;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
	
    private String name;
    private Date birthDateStart;
    private Date birthDateEnd;
    private String numberPhone;
    private String address;
    private String email;
    private String description;
    private float gpa;
    private String className;
}
