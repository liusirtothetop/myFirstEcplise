package com.liusir.bookstore.daoImpl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.liusir.bookstore.dao.TradeDao;
import com.liusir.bookstore.domain.Trade;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDao{

	@Override
	public void insert(Trade trade) {
  
		String sql="insert into trade (userId,tradeTime) values(?,?)";
		long key = insert(sql, trade.getUserId(),trade.getTradeTime());
		//得到主键
		trade.setTradeId((int) key);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
 
		String sql="select tradeId,userId,tradeTime from trade where userId=?"
				+ " ORDER BY tradeTime DESC";
		return new LinkedHashSet<Trade>(queryForList(sql, userId)) ;
	}

}
