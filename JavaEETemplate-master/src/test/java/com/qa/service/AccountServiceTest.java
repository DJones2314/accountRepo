package com.qa.service;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.qa.persistence.domain.Account;
import com.qa.persistence.repository.AccountMapRepository;
import com.qa.util.JSONUtil;


public class AccountServiceTest {

	private AccountMapRepository repo;
	private String joeBloggs;
	private String janeBloggs;
	private JSONUtil util;

	@Before
	public void setup() {
		repo = new AccountMapRepository();
		
		joeBloggs = "{\"firstName\": \"Joe\",\"secondName\": \"Doe\",\"accountNumber\": 1234}";
		
		janeBloggs = "{\"firstName\": \"Jane\",\"secondName\": \"Bloggs\",\"accountNumber\": 4321}";
		
		util = new JSONUtil();
	}
	
	@Test
	public void addAccountTest() {
		repo.createAccount(joeBloggs);
		assertEquals(repo.getAccountMap().size(), 1);
	}
	
	@Test
	public void addAccountTest2() {
		repo.createAccount(joeBloggs);
		repo.createAccount(janeBloggs);
		assertEquals(repo.getAccountMap().size(), 2);
	}

	@Test
	public void removeAccountTest() {
		repo.createAccount(joeBloggs);
		repo.createAccount(janeBloggs);
		repo.deleteAccount(1234L);
		assertEquals(1, repo.getAccountMap().size());
	}
	
	@Test
	public void removeAccountTest2() {
		repo.createAccount(joeBloggs);
		repo.createAccount(janeBloggs);
		repo.deleteAccount(1234L);
		repo.deleteAccount(4321L);
		Assert.assertEquals(0, repo.getAccountMap().size());
	}
	
	
	@Test
	public void removeAccountTest3() {
		repo.createAccount(joeBloggs);
		repo.createAccount(janeBloggs);
		repo.deleteAccount(1234L);
		repo.deleteAccount(4321L);
		repo.deleteAccount(5L);
		Assert.assertEquals(0, repo.getAccountMap().size());
	}
	
	@Test
	public void accountConversionToJSONTestEmpty() {
		String emptyMap = util.getJSONForObject(repo.getAccountMap());
		Assert.assertEquals("{}", emptyMap);
	}
	
	@Test
	public void accountConversionToJSONTestEmptyWithConversion() {
		String emptyMap = util.getJSONForObject(repo.getAccountMap());
		String accountAsJSON = "{\"[4321\":{\"firstName\":\"Jane\",\"secondName\":\"Bloggs\",\"accountNumber\":4321},\"1234\":{\"firstName\":\"Joe\",\"secondName\":\"Doe]\",\"accountNumber\":1234}}";
		Assert.assertEquals("{}", emptyMap);
	}

	@Test
	public void accountConversionToJSONTest() {
		String accountAsJSON = "{\"4321\":{\"firstName\":\"Jane\",\"secondName\":\"Bloggs\",\"accountNumber\":4321},\"1234\":{\"firstName\":\"Joe\",\"secondName\":\"Doe\",\"accountNumber\":1234}}";
		repo.createAccount(joeBloggs);
		repo.createAccount(janeBloggs);
		String populatedAccountMap = util.getJSONForObject(repo.getAccountMap());
		Assert.assertEquals(accountAsJSON, populatedAccountMap);
	}

	@Test
	public void getCountForFirstNamesInAccountWhenZero() {
		Assert.assertEquals(repo.getNumberOfAccountWithFirstName("Joe"), 0);
	}
	
	@Test
	public void getCountForFirstNamesInAccountWhenOne() {
		repo.createAccount(joeBloggs);
		Assert.assertEquals(repo.getNumberOfAccountWithFirstName("Joe"), 1);
		
	}

	@Test
	public void getCountForFirstNamesInAccountWhenMult() {
		repo.createAccount(joeBloggs);
		Account joeGordon = new Account("Joe", "Gordon", 1236);
		repo.createAccount(util.getJSONForObject(joeGordon));
		Assert.assertEquals(2, repo.getNumberOfAccountWithFirstName("Joe"));
	}
}
