package com.qa.persistence.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


import com.qa.persistence.domain.Account;
import com.qa.util.JSONUtil;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public class DataBaseRepository implements AccountRepository{

	
	@PersistenceContext(unitName = "primary")
	private EntityManager manager;
	
	JSONUtil jsonutil = new JSONUtil();
	
	public String getAllAccounts() {
		Query getAll = manager.createQuery("Select m FROM Account m");
		return jsonutil.getJSONForObject(getAll.getResultList());
	}
	
	private Account getAccount(Long id) {
		return manager.find(Account.class, id);
		
	}

	@Transactional(REQUIRED)
	public String updateAccount(Long id, String account) {
		Account updatedDetails = jsonutil.getObjectForJSON(account, Account.class);
		Account account1 = getAccount(id);
		if (account1 != null) {
		
			manager.remove(account1);
			manager.persist(updatedDetails);
			return "{\"message\": \"account has been updated\"}";
		}
				
		return "{\"message\": \"account could not be found\"}";
	}
	
	@Transactional(REQUIRED)
	public String createAccount(String account) {
		Account account2 = jsonutil.getObjectForJSON(account, Account.class);
		manager.persist(account2);
		return "{\"message\": \"account created\"}";
	}

	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account account3 = getAccount(id);
		if (account3 != null) {
			manager.remove(account3);
		}
		return "{\"message\": \"account sucessfully deleted\"}";
	}
	
	
}
