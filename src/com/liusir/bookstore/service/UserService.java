package com.liusir.bookstore.service;

import java.util.Set;

import com.liusir.bookstore.dao.BookDao;
import com.liusir.bookstore.dao.TradeDao;
import com.liusir.bookstore.dao.TradeItemDao;
import com.liusir.bookstore.dao.UserDao;
import com.liusir.bookstore.daoImpl.BookDaoImpl;
import com.liusir.bookstore.daoImpl.TradeDaoImpl;
import com.liusir.bookstore.daoImpl.TradeItemDaoImpl;
import com.liusir.bookstore.daoImpl.UserDaoImpl;
import com.liusir.bookstore.domain.Trade;
import com.liusir.bookstore.domain.TradeItem;
import com.liusir.bookstore.domain.User;

public class UserService {

	private UserDao userDao= new UserDaoImpl();
	private BookDao bookDao=new BookDaoImpl();
	private TradeDao tradeDao=new TradeDaoImpl();
	private TradeItemDao tradeItemDao=new TradeItemDaoImpl();
	
	//的到用户的操作
	public User getUserbyName(String name) {
		
	return userDao.getUser(name);	
	}
	
	public User getAllTradeItems(String name) {
		
		User user =userDao .getUser(name);
		if (user != null) {
			// 得到每个trades的交易信息
			Set<Trade> trades = tradeDao.getTradesWithUserId(user.getUserId());
			// 为每个trade的tradeItem集合 填充完整
			if(trades!=null) {
			for (Trade trade : trades) {
				Set<TradeItem> set = tradeItemDao.getTradeItemsWithTradeId(trade.getTradeId());
				  if(set!=null) {
					  for(TradeItem item:set) {
						  item.setBook(bookDao.getBook(item.getBookId()));
					  }
				  }
				trade.setItems(set);
				}
			}
			// 在用户上设置trade用户
			user.setTrades(trades);
			return user;
			}
		else
		{
			return null;
		}
	}
		
		
	}
	
