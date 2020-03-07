package com.liusir.bookstore.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liusir.bookstore.domain.ShoppingCart;

public class ShoppingCartUtils {

	/**
	 * 
	 * 获取购物车对象: 从 session 中获取, 若 session 中没有改对象.
	 * 则创建一个新的购物车对象, 放入到 session 中.
	 * 若有, 则直接返回. 
	 * @param request
	 * @param response
	 * @return
	 */
	
	public static ShoppingCart getShoppingCart(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ShoppingCart shoppingcart = (ShoppingCart) session.getAttribute("ShoppingCart");
		if (shoppingcart == null) {
			shoppingcart = new ShoppingCart();
			session.setAttribute("ShoppingCart", shoppingcart);
		}
		return shoppingcart;
	}

}
