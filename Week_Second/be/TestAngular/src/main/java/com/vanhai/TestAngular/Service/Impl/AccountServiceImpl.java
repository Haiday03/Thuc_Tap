package com.vanhai.TestAngular.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vanhai.TestAngular.Config.JwtUtils;
import com.vanhai.TestAngular.Entity.Account;
import com.vanhai.TestAngular.Repository.AccountRepository;
import com.vanhai.TestAngular.Service.AccoutService;

@Service
public class AccountServiceImpl implements AccoutService{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(Account account) {
		// TODO Auto-generated method stub
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return jwtUtils.generateToken(authentication);
	}

	@Override
	public Boolean register(Account account) {
		// TODO Auto-generated method stub
		Account account2 = accountRepository.findByUsername(account.getUsername());
		
		if(account2 != null)
			return false;
		
		Account account3 = new Account();
		
		account3.setUsername(account.getUsername());
		account3.setPassword(passwordEncoder.encode(account.getPassword()));
		
		accountRepository.save(account3);
		return true;
	}
	
	
}
