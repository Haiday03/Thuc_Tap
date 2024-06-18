package com.demo.day_one.DTO;

import com.demo.day_one.Entity.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenderDTO {
    private String gender;
    
    // Constructor to convert Gender entity to GenderDTO
    public GenderDTO(Gender gender) {
        this.gender = gender.toString(); // Hoặc tùy thuộc vào cách bạn muốn chuyển đổi
    }


}
