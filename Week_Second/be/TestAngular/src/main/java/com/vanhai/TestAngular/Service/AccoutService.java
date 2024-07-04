package com.vanhai.TestAngular.Service;

import com.vanhai.TestAngular.Entity.Account;

public interface AccoutService {
	
	public String login(Account account);
	
	public Boolean register(Account account);
}
