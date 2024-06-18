package com.demo.day_one.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    
    private String name;
    
    @Column(name = "birth_date")
    private Date birthDate;
    
    @Column(name = "number_phone")
    private String numberPhone;
    
    private String address;
    
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "longtext")
    private String description;
    
    private float gpa;
    
    @Enumerated(EnumType.STRING) // Ánh xạ enum dưới dạng chuỗi
    private Gender gender;

    // No-args constructor
    public Student() {}

    // All-args constructor
    public Student(UUID id, String name, Date birthDate, String numberPhone, String address, String email, String description, float gpa, Gender gender) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
