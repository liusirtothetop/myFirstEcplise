package com.liusir.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liusir.bookstore.dao.AccountDao;
import com.liusir.bookstore.dao.BookDao;
import com.liusir.bookstore.dao.TradeDao;
import com.liusir.bookstore.dao.TradeItemDao;
import com.liusir.bookstore.dao.UserDao;
import com.liusir.bookstore.daoImpl.AccountDaoImpl;
import com.liusir.bookstore.daoImpl.BookDaoImpl;
import com.liusir.bookstore.daoImpl.TradeDaoImpl;
import com.liusir.bookstore.daoImpl.TradeItemDaoImpl;
import com.liusir.bookstore.daoImpl.UserDaoImpl;
import com.liusir.bookstore.domain.Book;
import com.liusir.bookstore.domain.ShoppingCart;
import com.liusir.bookstore.domain.ShoppingCartItem;
import com.liusir.bookstore.domain.Trade;
import com.liusir.bookstore.domain.TradeItem;
import com.liusir.bookstore.web.CriteriaBook;
import com.liusir.bookstore.web.Page;

public class BookService {

	
	private BookDao bookdao=new BookDaoImpl();
	
	 public Page<Book> getPage(CriteriaBook criteriaBook){
		
		return  bookdao.getPage(criteriaBook);
}

	public Book getBook(int id) {
		
		return bookdao.getBook(id);
	}

	public void getShoppingCart(int id, HttpServletRequest request) {

		  
		HttpSession session = request.getSession();
		
		
   
		
		
		
	}

	public boolean addToCart(int id, ShoppingCart sc) {
      
		System.out.println("nihao");
		if(sc!=null)
		{
			sc.addBook(bookdao.getBook(id));
		    return true;
		}
		return false;
	}

	public void removeItem(ShoppingCart cart, int id) {
       //删除操作
		
		cart.removeItem(id);
	
	}

	public void clearCart(ShoppingCart shoppingCart) {
         //清空操作
		  shoppingCart.clear();
	}

	public void updateQuantity(ShoppingCart cart, int id, int quantity) {
    	//修改数量
 		cart.updateItemQuantity(id, quantity);
	}

	 private AccountDao accountDao=new AccountDaoImpl();
	 private UserDao userDao=new UserDaoImpl();
	 private TradeDao tradeDao=new TradeDaoImpl();
	 private TradeItemDao tradeItemDao=new TradeItemDaoImpl();
	
	public void cash(ShoppingCart shoppingCart, String accountId, String userName) {
             
		//1.更新mybooks 数据的StoreNumber和SaleNumber
		bookdao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		//2.更新account中的balance
		accountDao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		//3.向trade添加一条记录
		Trade trade=new Trade();
    	trade.setTradeTime(new Date(new java.util.Date().getTime()) );
		trade.setUserId(userDao.getUser(userName).getUserId());
		tradeDao.insert(trade);
		//4.向tradeItem数据表中插入n条数据
		Collection <TradeItem> tradeItem=new ArrayList();
		for(ShoppingCartItem item:shoppingCart.getItems())
		{
			TradeItem it=new TradeItem();
			it.setBookId(item.getBook().getId());
			it.setQuantity(item.getQuantity());
			it.setTradeId(trade.getTradeId());
			tradeItem.add(it);
		}
		tradeItemDao.batchSave(tradeItem);
		//购物车清空操作
		shoppingCart.clear();
		

	}
}
