package com.liusir.bookstore.daoImpl;

import com.liusir.bookstore.dao.AccountDao;
import com.liusir.bookstore.domain.Account;

public class AccountDaoImpl  extends BaseDao<Account> implements AccountDao{

	@Override
	public Account get(Integer accountId) {
		String sql="select * from account where accountId=?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		
		String sql="update account set balance=balance-? where accountId=? ";
      //这里需要抛出一个异常处理，来显示  用户的金额不够
		update(sql, amount,accountId);
		
	}

	
}
