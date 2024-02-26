/**
 * Copyright(C) 2020 Luvina JSC
 * TblUserLogic.java, 17/11/2020 Bui Quang Long
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;

/**
 * interface TblUserLogic khai báo các phương thức gọi đến phương thức ở tầng
 * Dao bảng tbl_user
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface TblUserLogic {
	/**
	 * phương thức checkExistLogin là phương thức để kiểm tra tài khoản nhập có tồn
	 * tại hoặc tồn tại thì password có nhập đúng không
	 * 
	 * @param username tài khoản người dùng nhập
	 * @param password mật khẩu người dùng nhập
	 * @return trả về true nếu người dùng nhập đúng username và password
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExistLogin(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException;

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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức getUserByLoginName là phương thức lấy ra thông tin tài khoản
	 * admin
	 * 
	 * @param username là tên tài khoản người dùng
	 * @return trả về đối tượng TblUserEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblUserEntity getUserByLoginName(String username) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức getAllUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * khách hàng và tài khoản admin
	 * 
	 * @param username là tên tài khoản khách hàng
	 * @return trả về mảng đối tượng TblUserEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<TblUserEntity> getAllUserByLoginName(String username) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức checkExitEmail là phương thức kiểm tra tồn tại user, user có
	 * email trùng email muốn kiểm tra (Trường hợp add, edit)
	 * 
	 * @param email  là email muốn kiểm tra
	 * @param userID là mã của user
	 * @return trả về true nếu tồn tại tồn tại user
	 * @throws ClassNotFoundException, SQLException
	 */
	public boolean checkExitEmail(String email, int userID) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức createUser là phương thức thêm mới thông tin user và trình độ
	 * tiếng nhật của user
	 * 
	 * @param userInfoEntity chứa thông tin thêm mới
	 * @return true nếu thêm mới thành công
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean createUser(UserInfoEntity userInfoEntity)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

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
	 * phương thức checkTblUser là phương thức kiểm tra user có tồn tại hiển thị lên
	 * ADM005
	 * 
	 * @param iDUserInfor là iD được truyền về
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkTblUser(int iDUserInfor) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức updateUser là phương thức cập nhật thông tin userInfor trường hợp
	 * Edit
	 * 
	 * @param userInfoEntity chứa thông tin edit
	 * @return true nếu edit thành công
	 * @throws NoSuchAlgorithmException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean updateUser(UserInfoEntity userInfoEntity)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

	/**
	 * phương thức checkAdmin là phương thức kiểm tra user có userID có là admin
	 * 
	 * @param userID mã của user
	 * @return true là admin, false nếu ngược lại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkAdmin(int userID) throws ClassNotFoundException, SQLException;

	/**
	 * phương thức deleteUser là phương thức xóa user khi click button xóa tại
	 * ADM005
	 * 
	 * @param userID mã của user
	 * @return true xóa thành công, false nếu ngược lại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean deleteUser(int userID) throws ClassNotFoundException, SQLException;
}
