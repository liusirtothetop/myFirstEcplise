package com.liusir.bookstore.service;

import com.liusir.bookstore.dao.AccountDao;
import com.liusir.bookstore.daoImpl.AccountDaoImpl;
import com.liusir.bookstore.domain.Account;

public class AccountService {

	private AccountDao account=new AccountDaoImpl();
	
	public Account getAccountByAccountId(Integer accountId) {
		
		return account.get(accountId);
		
	}
}
