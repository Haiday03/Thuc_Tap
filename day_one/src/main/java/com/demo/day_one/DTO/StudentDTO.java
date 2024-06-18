package com.demo.day_one.DTO;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import com.demo.day_one.Entity.Student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO implements Serializable {

    private UUID id;
    private String name;
    private Date birthDate;
    private String numberPhone;
    private String address;
    private String email;
    private String description;
    private float gpa;
    private GenderDTO gender;

    public StudentDTO(Student s) {
        this.id = s.getId();
        this.name = s.getName();
        this.birthDate = s.getBirthDate();
        this.numberPhone = s.getNumberPhone();
        this.address = s.getAddress();
        this.email = s.getEmail();
        this.description = s.getDescription();
        this.gpa = s.getGpa();
        if (s.getGender() != null)
            this.gender = new GenderDTO(s.getGender());
    }

}
