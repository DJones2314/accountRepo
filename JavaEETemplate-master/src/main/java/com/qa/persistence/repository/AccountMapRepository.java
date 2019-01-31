package com.qa.persistence.repository;

import java.util.HashMap;
import java.util.Map;

import com.qa.persistence.domain.Account;
import com.qa.util.JSONUtil;

public class AccountMapRepository implements AccountRepository {

	private Map<Long, Account> accountMap;

	JSONUtil jsonutil = new JSONUtil();

	private int count = 0;

	public AccountMapRepository() {
		accountMap = new HashMap<>();
	}

	public String getAllAccounts() {
		return jsonutil.getJSONForObject(accountMap.values());
	}

	public String createAccount(String account) {
		Account anAccount = jsonutil.getObjectForJSON(account, Account.class);
		accountMap.put((long) anAccount.getAccountNumber(), anAccount);
		return "Account created Successfully";
	}

	public String deleteAccount(Long id) {
		boolean countExists = accountMap.containsKey(id);
		if (countExists) {
			accountMap.remove(id);
		}
		return "Account deleted successfully";
	}

	public String updateAccount(Long id, String account) {
		Account anAccount = jsonutil.getObjectForJSON(account, Account.class);
		return "Account has been updated";

	}

	public Map<Long, Account> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<Long, Account> accountMap) {
		this.accountMap = accountMap;
	}
	
	public int getNumberOfAccountWithFirstName(String firstNameOfAccount) {
		return (int) accountMap.values().stream()
				.filter(eachAccount -> eachAccount.getFirstName().equals(firstNameOfAccount)).count();
	}

}
