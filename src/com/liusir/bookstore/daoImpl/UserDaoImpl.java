package com.liusir.bookstore.daoImpl;

import com.liusir.bookstore.dao.UserDao;
import com.liusir.bookstore.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao{

	@Override
	public User getUser(String username) {
    
		 String sql="select * from userinfo where username=?";
		return query(sql, username);
	}

}
