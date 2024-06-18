package com.demo.day_one.DTO;

import com.demo.day_one.Entity.Gender;

public class GenderDTO {
    private String gender;
    
    // No-args constructor
    public GenderDTO() {
    }

    // All-args constructor
    public GenderDTO(String gender) {
        this.gender = gender;
    }
    
    // Constructor to convert Gender entity to GenderDTO
    public GenderDTO(Gender gender) {
        this.gender = gender.toString(); // Hoặc tùy thuộc vào cách bạn muốn chuyển đổi
    }

    // Getters and Setters
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
