package com.demo.day_one.DTO;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import com.demo.day_one.Entity.Student;

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

    public StudentDTO() {
    }

    public StudentDTO(UUID id, String name, Date birthDate, String numberPhone, String address, String email, String description, float gpa, GenderDTO gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.numberPhone = numberPhone;
        this.address = address;
        this.email = email;
        this.description = description;
        this.gpa = gpa;
        this.gender = gender;
    }

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

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }
}
