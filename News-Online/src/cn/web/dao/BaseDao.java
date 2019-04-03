package cn.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 通用DAO，连接和关闭数据库
 * @author gec
 *
 */
public class BaseDao {
	//定义数据库参数    url  drivername  user  password
	public static final String DRIVERNAME = "com.mysql.jdbc.Driver";//驱动
	public static final String URL = "jdbc:mysql://localhost:3306/news?useUnicode=true&characterEncoding=UTF-8";
	public static final String USER = "root";
	public static final String PASSWORD = "";//没有密码就给个双引号
	
	/**
	 * 连接数据库
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			Class.forName(DRIVERNAME); // 注册 JDBC 驱动
			con = DriverManager.getConnection(URL, USER, PASSWORD);// 打开连接
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		
	}
	
	/**
	 * 关闭数据库的资源
	 * @param con
	 * @param stmt
	 * @param rs
	 */
	public void closeDB(Connection con,Statement stmt,ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
