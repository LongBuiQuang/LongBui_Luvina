/**
 * Copyright(C) 2020 Luvina JSC
 * TblUserDao.java, 17/11/2020 Bui Quang Long
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * interface TblUserDao khai báo các phương thức thao tác với bảng tbl_user
 * 
 * @author LA-PM Bui Quang Long
 *
 */

import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;

public interface TblUserDao extends BaseDao {
	/**
	 * phương thức getUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * admin từ Database
	 * 
	 * @param username là tên tài khoản admin
	 * @return trả về đối tượng TblUserEntity hoặc null
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUserEntity getUserByLoginName(String username) throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getListUsers là phương thức khai báo để lấy ra thông tin tài
	 * khoản khách hàng
	 * 
	 * @param offset          vị trí data cần lấy
	 * @param limit           số lượng lấy
	 * @param groupId         mã nhóm tìm kiếm
	 * @param fullName        tên tìm kiếm
	 * @param sortType        tên cột ưu tiên sắp xếp
	 * @param sortByFullName  giá trị sắp xếp của cột tên(ASC hoặc DESC)
	 * @param sortByCodeLevel giá trị sắp xếp của cột trình độ tiếng Nhật(ASC hoặc
	 *                        DESC)
	 * @param sortByEndDate   giá trị sắp xếp của cột ngày hết hạn(ASC hoặc DESC)
	 * @return mảng đối tượng UserInfor
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<UserInfoEntity> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getTotalUsers là phương thức lấy tổng số bản ghi (tài khoản khách
	 * hàng)
	 * 
	 * @param groupId  mã nhóm tìm kiếm
	 * @param fullName tên tìm kiếm
	 * @return tổng số bản ghi
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getColumnNameFromDB là phương thức lấy ra tên các cột
	 * 
	 * @return mảng chưa tên cột của các bảng tbl_user, mst_japan,
	 *         tbl_detail_user_japan
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<String> getColumnNameFromDB() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getAllUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * (cả admin và khách hàng)
	 * 
	 * @param username là tên tài khoản khách hàng
	 * @return trả về mảng đối tượng TblUserEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<TblUserEntity> getAllUserByLoginName(String username) throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getUserByEmail là phương thức khai báo để lấy ra
	 * List<TblUserEntity> có email truyền vào
	 * 
	 * @param email  là email nhập tại ADM003 trường hợp add và edit
	 * @param userID là mã của user
	 * @return trả về mảng đối tượng TblUserEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<TblUserEntity> getUserByEmail(String email, int userID) throws SQLException, ClassNotFoundException;

	/**
	 * phương thức insertUser là phương thức thực hiện insert dữ liệu từ đối tượng
	 * tblUserEntity vào bảng tbl_user trong database
	 * 
	 * @param tblUserEntity là đối tượng chứa thông tin của 1 user
	 * @return iDUser là mã của bản ghi được insert
	 * @throws SQLException
	 */
	public int insertUser(TblUserEntity tblUserEntity) throws SQLException;

	/**
	 * phương thức getUserInforById là phương thức lấy dữ liệu userInfor theo iD để
	 * hiển thị lên ADM005
	 * 
	 * @param iDUserInfor là iD được truyền về
	 * @return userInfoEntity là đối tượng chứa thông tin người dùng
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public UserInfoEntity getUserInforById(int iDUserInfor) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức getTblUserEntity là phương thức lấy ra thông tin TblUserEntity
	 * theo iDUserInfor
	 * 
	 * @param iDUserInfor là iD của user trong bảng tbl_user muốn lấy ra
	 * @return TblUserEntity là đối tượng chứa thông tin bản ghi trong bảng tbl_user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUserEntity getTblUserEntity(int iDUserInfor) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức updateUser là phương thức cập nhật thông tin user vào bảng
	 * tbl_user
	 * 
	 * @param tblUserEntity là đối tượng chứa thông tin user
	 * @throws SQLException
	 */
	public void updateUser(TblUserEntity tblUserEntity) throws SQLException;

	/**
	 * phương thức getRule là phương thức lấy ra rule của user theo userID
	 * 
	 * @param userID là mã của user
	 * @return giá trị rule
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getRule(int userID) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức deleteTblUser là phương thức xóa thông tin user tại bảng tbl_user
	 * (chức năng xóa tại ADM005) theo userID
	 * 
	 * @param userID là mã của user
	 * @throws SQLException
	 */
	public void deleteTblUser(int userID) throws SQLException;
}