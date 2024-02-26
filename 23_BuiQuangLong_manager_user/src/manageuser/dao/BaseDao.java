/**
 * Copyright(C) 2020 Luvina JSC
 * BaseDao.java, 17/11/2020 Bui Quang Long
 */
package manageuser.dao;

import java.sql.SQLException;

/**
 * 
 * interface BaseDao khai báo các phương thức thao tác với Database
 * 
 * @author LA-PM Bui Quang Long
 *
 */

import com.mysql.jdbc.Connection;

public interface BaseDao {

	/**
	 * phương thức connectDatabase là phương thức được khai báo để mở kết nối tới
	 * Database
	 * 
	 * @return kết nối thành công hoặc thất bại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void connectDatabase() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức closeConnectDatabase là phương thức được khai báo để đóng kết nối
	 * Database
	 * 
	 * @return đóng kết nối thành công hoặc lỗi đóng kết nối
	 * @throws SQLException
	 */
	public void closeConnectDatabase() throws SQLException;

	/**
	 * phương thức getConnection là phương thức lấy ra conn
	 * 
	 * @return conn đối tượng kết nối đến DB
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException;

	/**
	 * phương thức getConn là phương thức lấy ra conn
	 * 
	 * @return conn đối tượng kết nối
	 */
	public Connection getConn();

	/**
	 * phương thức setConn là phương thức set giá trị cho thuộc tính conn
	 * 
	 * @param conn đối tượng kết nối
	 */
	public void setConn(Connection conn);

	/**
	 * phương thức setAutoCommit là phương thức setAutoCommit thuộc tính conn
	 * 
	 * @param value là true, false
	 */
	public void setAutoCommit(boolean value) throws SQLException;

	/**
	 * phương thức commit là phương thức thực hiện comit
	 * 
	 */
	public void commit() throws SQLException;

	/**
	 * phương thức rollback là phương thức thực hiện rollback
	 * 
	 */
	public void rollback() throws SQLException;
}