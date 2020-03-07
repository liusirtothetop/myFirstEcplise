package com.liusir.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.liusir.bookstore.domain.Book;
import com.liusir.bookstore.domain.ShoppingCart;
import com.liusir.bookstore.domain.ShoppingCartItem;
import com.liusir.bookstore.domain.User;
import com.liusir.bookstore.service.AccountService;
import com.liusir.bookstore.service.BookService;
import com.liusir.bookstore.service.UserService;
import com.liusir.bookstore.utils.ShoppingCartUtils;
import com.liusir.bookstore.web.CriteriaBook;
import com.liusir.bookstore.web.Page;

public class bookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	private BookService bookService = new BookService();
	
	protected void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		获取 username 请求参数的值
		String username = request.getParameter("username");
//		调用 UserService 的 getUser(username) 获取User 对象：要求 trades 是被装配好的，而且每一个 Trrade 对象的 items 也被装配好
		User user = userService.getAllTradeItems(username);

//		把 User 对象放入到 request 中
		if(user == null){
			response.sendRedirect(request.getServletPath() + "/error.jsp");
			return;
		}
		request.setAttribute("user", user);
//		转发页面到 /WEB-INF/pages/trades.jsp
		request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
	
	}

	

	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountId = request.getParameter("accountId");
		String userName = request.getParameter("username");
		// 验证表单
		StringBuffer errors = validForm(accountId, userName);
		if (errors.toString().equals(" ")) {
			// 验证用户
			errors = validateUser(userName, accountId);
			if (errors.toString().equals(" ")) {
				// 验证库存
				errors = validateBookStore(request, response);
				if (errors.toString().equals(" ")) {
					// 验证账户
					errors = validateAccount(request, accountId, response);
				}

			}
		}
		if (!errors.toString().equals(" ")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		bookService.cash(ShoppingCartUtils.getShoppingCart(request, response),accountId,userName);

		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}

	private AccountService accountService = new AccountService();

	private StringBuffer validateAccount(HttpServletRequest request, String accountId, HttpServletResponse response) {
		StringBuffer errors = new StringBuffer(" ");
		// 获得购物车
		ShoppingCart cart = ShoppingCartUtils.getShoppingCart(request, response);

		int balance = accountService.getAccountByAccountId(Integer.parseInt(accountId)).getBalance();

		if (balance < cart.getTotalMoney()) {
			errors.append("余额不足！");
		}

		return errors;
	}

	private StringBuffer validateBookStore(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer errors = new StringBuffer(" ");
		// 获得购物车
		ShoppingCart cart = ShoppingCartUtils.getShoppingCart(request, response);
		for (ShoppingCartItem item : cart.getItems()) {
			// 判断比较需要的书的数量是否和数据库中书的数量
			if (item.getQuantity() > bookService.getBook(item.getBook().getId()).getStoreNumber()) {
				errors.append(item.getBook().getTitle() + "书的库存不足<br>");
			}

		}
		return errors;
	}

	// 当需要于数据库连接时 ，，不能在servlet中进行数据库的一系列的操作，
	// 需要通过其它的方式，来进行，例如service
	private UserService userService = new UserService();

	// 验证 用户的账户是否匹配
	private StringBuffer validateUser(String userName, String accountId) {
		boolean flag = false;
		StringBuffer errors = new StringBuffer(" ");
		User user = userService.getUserbyName(userName);
		if (user != null) {
			int accountId2 = user.getAccountId();
			if (accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}
		if (!flag) {
			errors.append("用户名与账号不匹配！");
		}
		return errors;

	}

	private StringBuffer validForm(String accountId, String userName) {
		StringBuffer errors = new StringBuffer(" ");
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}
		if (userName == null || userName.trim().equals("")) {
			errors.append("账户Id不能为空");
		}
		return errors;
	}

	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");

		String quantityStr = request.getParameter("quantity");

		int id = -1;
		int quantity = -1;
		// 一般开发需要的用的就是异常的方式
		try {
			quantity = Integer.parseInt(quantityStr);
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		System.out.println(id + ":" + quantity);
		// 得到购物车 。
		ShoppingCart cart = ShoppingCartUtils.getShoppingCart(request, response);

		if (id > 0 && quantity > 0)
			bookService.updateQuantity(cart, id, quantity);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", cart.getBookNumber());
		result.put("totalMoney", cart.getTotalMoney());
		// 将Map改编成json
		Gson json = new Gson();
		String Json = json.toJson(result);

		// 将json 传送回去。
		response.setContentType("text/javaScript");
		response.getWriter().print(Json);

	}

	// 购物车清空操作
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart shoppingCart = ShoppingCartUtils.getShoppingCart(request, response);

		bookService.clearCart(shoppingCart);

		request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}

	// 购物车删除操作
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		// 一般开发需要的用的就是异常的方式
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		ShoppingCart cart = ShoppingCartUtils.getShoppingCart(request, response);
		bookService.removeItem(cart, id);
		if (cart.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);

	}

	protected void toForwordPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getParameter("page");

		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		boolean flag = true;
		int id = -1;
		// 一般开发需要的用的就是异常的方式
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0) {
			// 的到shoppingCart;
			ShoppingCart sc = ShoppingCartUtils.getShoppingCart(request, response);

			// 调用shoppingCart的添加方法
			flag = bookService.addToCart(id, sc);

			System.out.println(sc);
		}
		if (flag) {
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/error.jsp");

	}

	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");

		String maxPriceStr = request.getParameter("maxPrice");

		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		int pageNo = 1;
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (Exception e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (Exception e) {
		}
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);

		request.setAttribute("bookpage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}

	// 获取单本书的详细信息
	// uri中保存的东西是 是通过js操作来保存的。
	// BookServic是一个便于维护的操作。
	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		Book book = null;
		int id = -1;
		// 一般开发需要的用的就是异常的方式
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0) {
			book = bookService.getBook(id);
		}
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}

		request.setAttribute("book", book);

		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);

	}

}
