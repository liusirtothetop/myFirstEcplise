package com.liusir.bookstore.filter;

import java.sql.Connection;

public class ThreadLocalGet {

	private  ThreadLocal<Connection> thread=new ThreadLocal<Connection>();
	//必须的但是实例类
	private static ThreadLocalGet local=new ThreadLocalGet();

    public ThreadLocalGet(){}
	
    public static ThreadLocalGet getInstance() {
    	return local;
    }
    
    public void setConnection(Connection connection) {
    	
    	thread.set(connection);
    	
    }
    public Connection getConnection() {
    
    	return thread.get();
    }
    
    public void removeConnection() {
    	
    	thread.remove();
    }
	
}
