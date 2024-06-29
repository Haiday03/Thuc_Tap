package com.vanhai.TestAngular.Entity;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column(columnDefinition = "longtext")
	private String description;
	
	private int age;
	
	private String address;
	
	private String avatar;
	
	@Column(name = "date_join")
	private java.util.Date dateJoin;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private ClassRoom classroom;
}
