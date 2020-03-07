package com.liusir.bookstore.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.liusir.bookstore.dao.AccountDao;
import com.liusir.bookstore.dao.TradeDao;
import com.liusir.bookstore.dao.TradeItemDao;
import com.liusir.bookstore.dao.UserDao;
import com.liusir.bookstore.daoImpl.AccountDaoImpl;
import com.liusir.bookstore.daoImpl.TradeDaoImpl;
import com.liusir.bookstore.daoImpl.TradeItemDaoImpl;
import com.liusir.bookstore.daoImpl.UserDaoImpl;
import com.liusir.bookstore.domain.Account;
import com.liusir.bookstore.domain.Trade;
import com.liusir.bookstore.domain.TradeItem;
import com.liusir.bookstore.domain.User;

public class AccountDaoImplTest {

	TradeItemDao tradei=new TradeItemDaoImpl();
	TradeDao trade=new TradeDaoImpl();
	UserDao user=new UserDaoImpl();
	AccountDao accountDao=new AccountDaoImpl();
	@org.junit.Test
	public void Test() {
		Account account = accountDao.get(1);
		System.out.println(account.getBalance());
		
	}

	@org.junit.Test
	public void Test1() {
		accountDao.updateBalance(1, 50);
		
	}
	@org.junit.Test
	public void Test2() {
		User user2 = user.getUser("AAA");
		
		System.out.println(user2);
		
	}
	
	@org.junit.Test
	public void test3() {
		Trade trade1=new Trade();
		trade1.setUserId(3);
		trade1.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.insert(trade1);

		
	}
	@org.junit.Test
	public void test4() {
//     TradeItem tradeItem=new TradeItem();
//     tradeItem.setBookId(1);
//     tradeItem.setQuantity(20);
//     tradeItem.setTradeId(1);
//     List<TradeItem> list=new ArrayList();
//     list.add(tradeItem);
//     tradei.batchSave(list);		
		
		Set<TradeItem> tradeItemsWithTradeId = tradei.getTradeItemsWithTradeId(1);
		System.out.println(tradeItemsWithTradeId);
	}
	
}
