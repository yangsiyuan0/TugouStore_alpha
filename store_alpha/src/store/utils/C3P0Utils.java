package store.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 定义一个连接池工具类：
 * 1. 获取连接池对象
 * 2. 获取连接对象
 * 3. 释放资源
 */
public class C3P0Utils {
	// 空参构造器：默认加载src路径下的c3p0-config文件
	private static DataSource datasource = new ComboPooledDataSource();

	/**
	 * 获取连接池对象
	 * @return
	 */
	public static DataSource getDataSource(){
		return datasource;
	}
	
	/**
	 * 获取连接对象
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			con = datasource.getConnection();
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("服务器连接异常");
		}
		return con;
	}
	
	/**
	 * 释放资源
	 * @param con
	 * @param stat
	 * @param rs
	 */
	public static void release(Connection con,Statement stat,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stat = null;
		}
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
		}
	}
	
	/**
	 * 释放资源
	 * @param con
	 * @param stat
	 */
	public static void release(Connection con,Statement stat){
		
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stat = null;
		}
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
		}
	}
}
