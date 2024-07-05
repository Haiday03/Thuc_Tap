package com.vanhai.TestAngular.Service;

import com.vanhai.TestAngular.DTO.JwtView;
import com.vanhai.TestAngular.Entity.Account;

public interface AccoutService {
	
	public JwtView login(Account account);
	
	public Boolean register(Account account);
}
