/**
 * Copyright(C) 2020 Luvina JSC
 * TblDetailUserJapanDao.java, 14/12/2020 Bui Quang Long
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblDetailUserJapanEntity;

/**
 *
 * interface TblDetailUserJapanDao khai báo các phương thức thao tác với bảng
 * tbl_detail_user_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface TblDetailUserJapanDao extends BaseDao {
	/**
	 * phương thức insertDetailUserJapan là phương thức thực hiện insert dữ liệu từ
	 * đối tượng tblDetailUserJapanEntity vào bảng tbl_detail_user_japan
	 * 
	 * @param tblDetailUserJapanEntity là đối tượng chứa thông tin trình độ tiếng
	 *                                 nhật
	 * @throws SQLException
	 */
	public void insertDetailUserJapan(TblDetailUserJapanEntity tblDetailUserJapanEntity) throws SQLException;

	/**
	 * phương thức getDetailUserJapanEntity là phương thức lấy trình độ tiếng nhật
	 * trong bảng tbl_detail_user_japan ứng với userID
	 * 
	 * @param userID là mã của user
	 * @return mảng TblDetailUserJapanEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<TblDetailUserJapanEntity> getDetailUserJapanEntity(int userID)
			throws ClassNotFoundException, SQLException;

	/**
	 * phương thức updateDetailUserJapan là phương thức thực hiện update dữ liệu từ
	 * đối tượng tblDetailUserJapanEntity vào hàng tương ứng của bảng
	 * tbl_detail_user_japan
	 * 
	 * @param tblDetailUserJapanEntity là đối tượng chứa thông tin trình độ tiếng
	 *                                 nhật
	 * @throws SQLException
	 */
	public void updateDetailUserJapan(TblDetailUserJapanEntity tblDetailUserJapanEntity) throws SQLException;

	/**
	 * phương thức deleteDetailUserJapan là phương thức thực hiện delete dữ liệu
	 * trong bảng tbl_detail_user_japan tương ứng user_id
	 * 
	 * @param userID là mã của user
	 * @throws SQLException
	 */
	public void deleteDetailUserJapan(int userID) throws SQLException;

}