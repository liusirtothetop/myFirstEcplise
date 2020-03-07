package com.liusir.bookstore.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liusir.bookstore.dao.TradeItemDao;
import com.liusir.bookstore.domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDao {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		
		String sql="insert into tradeitem (bookId,quantity,tradeId) values(?,?,?)";
		Object[][] params = new Object[items.size()][3];
		List<TradeItem> list = new ArrayList<>(items);
		for (int i = 0; i < list.size(); i++) {
			params[i][0] = list.get(i).getBookId();
			params[i][1] = list.get(i).getQuantity();
			params[i][2] = list.get(i).getTradeId();
		}

	   	batch(sql, params);
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
     String sql="select itemId,bookId,quantity,tradeId from tradeItem where tradeId=?";
     List<TradeItem> queryForList = queryForList(sql, tradeId);

		return new HashSet<TradeItem>(queryForList);
	}

}
