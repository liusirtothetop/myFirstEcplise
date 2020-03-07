package com.liusir.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liusir.bookstore.db.JDBCUtils;

/**
 * Servlet Filter implementation class ThreadLoaclFilter
 */
public class ThreadLoaclFilter implements Filter {

    public ThreadLoaclFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	
	/**
	 * ThreadLocal:
	 * > 一种绑定的机制，将一个对象进行绑定，地层中是一个map对象来进行绑定。
	 * > 但是不能这并不是 解决线程安全的要求，在线程安全方面也是应用的是synchronize
	 * > 在Spring中有专门的事务操作，等之后可以将这个案例与spring中的技术相互吻合。
	 */
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		
       //通过ThreaLocal来进行Connection来进行绑定
		Connection connection=null;
		try {
			 connection=JDBCUtils.getConnection();
			 //开启事务
			 connection.setAutoCommit(false);
			 ThreadLocalGet.getInstance().setConnection(connection);
			 chain.doFilter(request, response);
			 connection.commit();
		} catch (Exception e) {
          e.printStackTrace();
          try {
        	  //事务回滚
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
          
          HttpServletRequest requ=(HttpServletRequest) request;
          HttpServletResponse respon=(HttpServletResponse)response;
          respon.sendRedirect(requ.getContextPath()+"/error.jsp");
		}finally {
			//将Connection中的信息解除绑定
			ThreadLocalGet.getInstance().removeConnection();
			JDBCUtils.release(connection);
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
