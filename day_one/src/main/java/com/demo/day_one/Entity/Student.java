package com.demo.day_one.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    @JsonIgnore
    private ClassRoom classroom;

}
