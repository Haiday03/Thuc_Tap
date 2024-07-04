package com.vanhai.TestAngular.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanhai.TestAngular.Entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{

	public Account findByUsername(String username);
}
