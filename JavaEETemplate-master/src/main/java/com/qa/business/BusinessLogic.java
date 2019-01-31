package com.qa.business;

import javax.inject.Inject;

import com.qa.persistence.domain.Account;
import com.qa.persistence.repository.AccountRepository;
import com.qa.util.JSONUtil;

public class BusinessLogic implements BusinessService{

	@Inject
	private AccountRepository repo;
	@Inject 
	private JSONUtil jsonutil;
	
	@Override
	public String getAllAccounts() {
		return repo.getAllAccounts();
	}

	@Override
	public String addAccount(String account) {
		Account anAccount = jsonutil.getObjectForJSON(account, Account.class);
		if(anAccount.getAccountNumber() == 9) {
			return "{\"message\": \"This account has been blocked\"}";
		} else {
			return repo.createAccount(account);
		}
	}

	@Override
	public String deleteAccount(Long id) {
		
		return repo.deleteAccount(id);
	}
	

}
