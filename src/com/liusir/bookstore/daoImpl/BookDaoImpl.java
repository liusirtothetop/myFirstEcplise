package com.liusir.bookstore.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.liusir.bookstore.dao.BookDao;
import com.liusir.bookstore.domain.Book;
import com.liusir.bookstore.domain.ShoppingCartItem;
import com.liusir.bookstore.web.CriteriaBook;
import com.liusir.bookstore.web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDao {
	
	@Override
	public Book getBook(int id) {
		
		String Sql = "select * from mybooks where Id =?";
		
		return query(Sql, id);
	}
	
	@Override
	public Page<Book> getPage(CriteriaBook cb) {

		Page<Book> page = new Page<Book>(cb.getPageNo());

		page.setToltaItemNumber(getTotalBookNumber(cb));
		// 检验pageNo的合法性

		cb.setPageNo(page.getPageNo());

		page.setList(getPageList(cb, 3));

		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {

		String sql = "select count(id) from mybooks where Price>=? and Price <=?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {

		String sql = "SELECT Id, Author, Title, Price, PublishingDate, " +
				"SalesAmount, StoreNumber, Remark FROM mybooks " +
				"WHERE Price >= ? AND Price <= ? " +
				"LIMIT ?, ?";
		
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), 
				(cb.getPageNo() - 1) * pageSize, pageSize);

	}
	@Override
	
	public int getStoreNumber(Integer id) {

		String sql = "select StoreNumber from mybooks where Id=?";

		return getSingleVal(sql, id);
	}

	@Override
	 public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
         String sql="UPDATE mybooks SET Salesamount=Salesamount+?,Storenumber=Storenumber-? WHERE Id=?";
         Object [][] params=new Object[items.size()][3];
         List<ShoppingCartItem> list=new ArrayList<>(items);
         for(int i=0;i<items.size();i++)
         {
        	 
        	 params[i][0]=list.get(i).getQuantity();
        	 params[i][1]=list.get(i).getQuantity();
        	 params[i][2]=list.get(i).getBook().getId();
         }
           batch(sql, params);
		
	}

}
