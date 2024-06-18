package com.demo.day_one.DTO;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
	
    public String name;
    public Date birthDateStart;
    public Date birthDateEnd;
    public String numberPhone;
    public String address;
    public String email;
    public String description;
    public float gpa;
}
