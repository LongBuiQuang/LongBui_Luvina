/**
 * Copyright(C) 2020 Luvina JSC
 * BaseDaoImpl.java, 17/11/2020 Bui Quang Long
 */
package manageuser.dao.impl;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import manageuser.dao.BaseDao;
import manageuser.utils.Constant;

/**
 * class BaseDaoImpl chứa các phương thức liên quan đến kết nối với Database
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class BaseDaoImpl implements BaseDao {
//	Khai báo một đối tượng Connection
	Connection conn;

	/**
	 * phương thức connectDatabase là phương thức được khai báo để mở kết nối tới
	 * Database
	 * 
	 * @return kết nối thành công hoặc thất bại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void connectDatabase() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(Constant.DRIVER);
			conn = (Connection) DriverManager.getConnection(Constant.DB_URL, Constant.DB_USER_NAME,
					Constant.DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println("Kết nối database thất bại");
			throw e;
		}
	}

	/**
	 * phương thức closeConnectDatabase là phương thức được khai báo để đóng kết nối
	 * Database
	 * 
	 * @return đóng kết nối thành công hoặc lỗi đóng kết nối
	 * @throws SQLException
	 */
	@Override
	public void closeConnectDatabase() throws SQLException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Lỗi đóng kết nối");
			throw e;
		}
	}

	/**
	 * phương thức getConnection là phương thức lấy ra conn
	 * 
	 * @return conn đối tượng kết nối đến DB
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		try {
			connectDatabase();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("BaseDaoImpl.java, getConnection " + e.getMessage());
			throw e;
		}
		return conn;
	}

	/**
	 * phương thức getConn là phương thức lấy ra conn
	 * 
	 * @return conn đối tượng kết nối
	 */
	@Override
	public Connection getConn() {
		return conn;
	}

	/**
	 * phương thức setConn là phương thức set giá trị cho thuộc tính conn
	 * 
	 * @param conn đối tượng kết nối
	 */
	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * phương thức setAutoCommit là phương thức setAutoCommit thuộc tính conn
	 * 
	 * @param value là true, false
	 */
	@Override
	public void setAutoCommit(boolean value) throws SQLException {
		try {
			this.conn.setAutoCommit(value);
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl.java, setAutoCommit" + e.getMessage());
			throw e;
		}
	}

	/**
	 * phương thức commit là phương thức thực hiện comit
	 * 
	 */
	@Override
	public void commit() throws SQLException {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl.java, commit" + e.getMessage());
			throw e;
		}
	}

	/**
	 * phương thức rollback là phương thức thực hiện rollback
	 * 
	 */
	@Override
	public void rollback() throws SQLException {
		try {
			this.conn.rollback();
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl.java, rollback" + e.getMessage());
			throw e;
		}
	}

}