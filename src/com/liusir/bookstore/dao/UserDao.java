package com.liusir.bookstore.dao;

import com.liusir.bookstore.domain.User;

public interface UserDao {

	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);

}

