package com.liusir.bookstore.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.liusir.bookstore.dao.Dao;
import com.liusir.bookstore.db.JDBCUtils;
import com.liusir.bookstore.filter.ThreadLocalGet;
import com.liusir.bookstore.utils.ReflectionUtils;

public class BaseDao<T> implements Dao<T> {

	private QueryRunner queryrunner = new QueryRunner();
	private Class<T> clazz = null;

	public BaseDao() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}

	@Override
	public long insert(String sql, Object... args) {
		// 杩欓噷鏄繑鍥炰富閿殑鍊�
		long key = 0;
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ThreadLocalGet.getInstance().getConnection();
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					statement.setObject(i + 1, args[i]);
				}
			}
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					key = resultSet.getLong(1);
				}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			//不需要关闭
			//JDBCUtils.release(connection);
			JDBCUtils.release(resultSet, statement);
		}
		return key;
	}

	@Override
	public void update(String sql, Object... args) {

		Connection connection = null;

		try {
			connection =ThreadLocalGet.getInstance().getConnection();
			queryrunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			JDBCUtils.release(connection);
//		}

	}

	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ThreadLocalGet.getInstance().getConnection();
			T query = queryrunner.query(connection,sql, new BeanHandler<T>(clazz), args);
			
			return query;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;

		try {
			connection =ThreadLocalGet.getInstance().getConnection();
			List<T> list = queryrunner.query(connection, sql, new BeanListHandler<T>(clazz), args);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			JDBCUtils.release(connection);
//		}
		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;

		try {
			connection = ThreadLocalGet.getInstance().getConnection();

			V query = (V) queryrunner.query(connection, sql, new ScalarHandler(), args);

			return query;
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			JDBCUtils.release(connection);
//		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		try {
			connection = ThreadLocalGet.getInstance().getConnection();
			queryrunner.batch(connection, sql, params);

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally {
//			JDBCUtils.release(connection);
//		}

	}

}
