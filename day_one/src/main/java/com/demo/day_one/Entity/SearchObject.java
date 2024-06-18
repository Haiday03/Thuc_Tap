package com.demo.day_one.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchObject {
	
	private int pageSize;
	private int pageNumber;
	private String key;
}
