/**
 * Copyright(C) 2020 Luvina JSC
 * TblUserLogicImpl.java, 17/11/2020 Bui Quang Long
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import manageuser.dao.BaseDao;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.BaseDaoImpl;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapanEntity;
import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * 
 * class TblUserLogicImpl có các phương thức gọi đến phương thức ở tầng Dao bảng
 * tbl_user
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class TblUserLogicImpl implements TblUserLogic {

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
	@Override
	public boolean checkExistLogin(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
//		Khai báo biến check để kiểm tra tài khoản có tồn tại
		boolean check = false;
		try {
//			Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Lấy ra đối tượng userEntity. 
			TblUserEntity userEntity = tblUserDaoImpl.getUserByLoginName(username);
//		    Nếu kết quả userEntity lấy ra bằng null
			if (userEntity == null) {
//			Tài khoản không tồn tại check = false
				check = false;
			} else {
//			    Nếu tài khoản tồn tại
//			    Khởi tạo đối tượng common
				Common common = new Common();
//			    Mã hóa password người dùng nhập và salt được lấy từ Database
				String passAfterEncryption;
				passAfterEncryption = common.encryptPassword(password, userEntity.getSalt());
//			    Kiểm tra password người dùng nhập và password trong Database
				boolean checkSame = common.compareString(passAfterEncryption, userEntity.getPassWord());
//			    Nếu giống nhau
				if (checkSame) {
//				Người dùng nhập đúng tài khoản check = true;
					check = true;
				} else {
//				Người dùng nhập sai password
					check = false;
				}
			}
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, checkExistLogin " + e.getMessage());
			throw e;
		}
//		Trả về kết quả
		return check;
	}

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
	@Override
	public List<UserInfoEntity> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException {
//		Khai báo mảng listUserInfoEntity
		List<UserInfoEntity> listUserInfoEntity = new ArrayList<>();
		try {
//		    Khởi tạo một đối tượng 
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Khởi tạo đối tượng common
			Common common = new Common();
//			Gọi hàng replaceWildCard
			fullName = common.replaceWildCard(fullName);
//			Lấy ra danh sách đối tượng UserInfoEntity và lưu vào mảng listUserInfoEntity
			listUserInfoEntity = tblUserDaoImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
					sortByCodeLevel, sortByEndDate);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl.java, getListUsers " + e.getMessage());
			throw e;
		}
//		Trả về listUserInfoEntity
		return listUserInfoEntity;
	}

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
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		int totalUser = 0;
		try {
//			Khởi tạo một đối tượng 
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//			Khởi tạo đối tượng common
			Common common = new Common();
//			Gọi hàng replaceWildCard
			fullName = common.replaceWildCard(fullName);
			totalUser = tblUserDaoImpl.getTotalUsers(groupId, fullName);
		} catch (Exception e) {
			System.out.println("TblUserLogicImpl.java, getTotalUsers " + e.getMessage());
			throw e;
		}
		return totalUser;
	}

	/**
	 * phương thức getUserByLoginName là phương thức lấy ra thông tin tài khoản
	 * admin
	 * 
	 * @param username là tên tài khoản người dùng
	 * @return trả về đối tượng TblUserEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public TblUserEntity getUserByLoginName(String username) throws ClassNotFoundException, SQLException {
		TblUserEntity tblUserEntity = null;
		try {
//		    Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//			Lấy ra đối tượng tblUserEntity 
			tblUserEntity = tblUserDaoImpl.getUserByLoginName(username);
		} catch (ClassNotFoundException | SQLException e) {
//			Ghi log
			System.out.println("TblUserLogicImpl.java, getUserByLoginName " + e.getMessage());
			throw e;
		}
		return tblUserEntity;
	}

	/**
	 * phương thức getAllUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * khách hàng và tài khoản admin
	 * 
	 * @param username là tên tài khoản khách hàng
	 * @return trả về mảng đối tượng TblUserEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public List<TblUserEntity> getAllUserByLoginName(String username) throws ClassNotFoundException, SQLException {
		List<TblUserEntity> listTblUserEntity;
		try {
//		    Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Lấy ra mảng listTblUserEntity
			listTblUserEntity = tblUserDaoImpl.getAllUserByLoginName(username);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, getAllUserByLoginName " + e.getMessage());
			throw e;
		}
		return listTblUserEntity;
	}

	/**
	 * phương thức checkExitEmail là phương thức kiểm tra tồn tại user, user có
	 * email trùng email muốn kiểm tra (Trường hợp add, edit)
	 * 
	 * @param email  là email muốn kiểm tra
	 * @param userID là mã của user
	 * @return trả về true nếu tồn tại tồn tại user
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public boolean checkExitEmail(String email, int userID) throws ClassNotFoundException, SQLException {
		boolean check = false;
		List<TblUserEntity> listTblUserEntity;
		try {
//		    Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//			Lấy ra listTblUserEntity
			listTblUserEntity = tblUserDaoImpl.getUserByEmail(email, userID);
//			Nếu có user ứng với email
			if (listTblUserEntity.size() != Constant.ZERO) {
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, getUserByEmail " + e.getMessage());
			throw e;
		}
		return check;
	}

	/**
	 * phương thức createUser là phương thức thêm mới thông tin user và trình độ
	 * tiếng nhật của user
	 * 
	 * @param userInfoEntity chứa thông tin thêm mới
	 * @return true nếu thêm mới thành công
	 * @throws SQLException, NoSuchAlgorithmException, ClassNotFoundException
	 */
	@Override
	public boolean createUser(UserInfoEntity userInfoEntity)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
//	    Biến check để kiểm tra insert dữ liệu thành công, không thành 
		boolean check = false;
//	    Khởi tạo đối tượng baseDaoImpl
		BaseDao baseDaoImpl = new BaseDaoImpl();
		Connection connection = null;
		try {
//			Lấy ra đối tượng connection để dùng chung
			connection = baseDaoImpl.getConnection();
//		    Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Khởi tạo đối tượng tblDetailUserJapanDaoImpl
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
//		    setAutoCommit 
			baseDaoImpl.setAutoCommit(false);
//		    Khởi tạo common
			Common common = new Common();
			TblUserEntity tblUserEntity = common.getTblUserEntity(userInfoEntity);
//          Gọi phương thức setConn
			tblUserDaoImpl.setConn(connection);
//			Gọi phương thức insertUser
			int iDUser = tblUserDaoImpl.insertUser(tblUserEntity);
			if (!"0".equals(userInfoEntity.getCodeLevel())) {
//              Lấy đối tượng tblDetailUserJapanEntity
				TblDetailUserJapanEntity tblDetailUserJapanEntity = common.getTblDetailUserJapanEntity(userInfoEntity,
						iDUser);
//				Set connection
				tblDetailUserJapanDaoImpl.setConn(connection);
//			    Gọi phương thức insertDetailUserJapan
				tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapanEntity);
			}
//			Thực hiện commit
			baseDaoImpl.commit();
//			Gán check = true
			check = true;
		} catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
//          Thực hiện rollback
			baseDaoImpl.rollback();
			System.out.println("TblUserLogicImpl.java, createUser " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			baseDaoImpl.closeConnectDatabase();
		}
		return check;
	}

	/**
	 * phương thức getUserInforById là phương thức lấy dữ liệu userInfor theo iD để
	 * hiển thị lên ADM005
	 * 
	 * @param iDUserInfor là iD được truyền về
	 * @return userInfoEntity là đối tượng chứa thông tin người dùng
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public UserInfoEntity getUserInforById(int iDUserInfor) throws ClassNotFoundException, SQLException {
//      Khai báo userInfoEntity
		UserInfoEntity userInfoEntity = null;
		try {
//		    Khởi tạo đối tượng tblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Lấy ra đối tượng userInfoEntity
			userInfoEntity = tblUserDaoImpl.getUserInforById(iDUserInfor);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, getUserInforById " + e.getMessage());
			throw e;
		}
//		Trả về userInfoEntity
		return userInfoEntity;
	}

	/**
	 * phương thức checkTblUser là phương thức kiểm tra user có tồn tại
	 * 
	 * @param iDUserInfor là iD được truyền về
	 * @param check       là kết quả check tồn tại UserInfor
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public boolean checkTblUser(int iDUserInfor) throws ClassNotFoundException, SQLException {
//		Khai báo biến check = false là không tồn tại UserInfor 
		boolean check = false;
		try {
//          Khởi tạo đối tượng tblUserDaoImpl 
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//		    Khởi tạo đối tượng
			TblUserEntity tblUserEntity = tblUserDaoImpl.getTblUserEntity(iDUserInfor);
			if (tblUserEntity != null) {
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, checkTblUser " + e.getMessage());
			throw e;
		}
//      Trả về kết quả kiểm tra
		return check;
	}

	/**
	 * phương thức updateUser là phương thức cập nhật thông tin userInfor trường hợp
	 * Edit
	 * 
	 * @param userInfoEntity chứa thông tin edit
	 * @return true nếu edit thành công
	 * @throws ClassNotFoundException, SQLException, NoSuchAlgorithmException
	 */
	@Override
	public boolean updateUser(UserInfoEntity userInfoEntity)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
//	    Biến check để kiểm tra insert dữ liệu thành công, không thành 
		boolean check = false;
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		Connection connection = null;
		try {
//          Khởi tạo common
			Common common = new Common();
//			Khởi tạo tblDetailUserJapanLogicImpl
			TblDetailUserJapanLogic tblDetailUserJapanLogicImpl = new TblDetailUserJapanLogicImpl();
//			Lấy userID
			int userID = userInfoEntity.getUserID();
//			Kiểm tra tồn tại trình độ tiếng Nhật ứng với userID
			boolean checkHaveJapan = tblDetailUserJapanLogicImpl.checkDatailUserJapan(userID);
//          Transacsion
			tblUserDaoImpl.connectDatabase();
//			Set setAutoCommit
			tblUserDaoImpl.setAutoCommit(false);
//			Lấy ra tblUserEntity
			TblUserEntity tblUserEntity = common.getTblUserEntity(userInfoEntity);
//			Update tblUserEntity vào bảng tbl_user
			tblUserDaoImpl.updateUser(tblUserEntity);
//			Lấy ra connection
			connection = tblUserDaoImpl.getConn();
//			Khởi tạo tblDetailUserJapanDaoImpl
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
//			Kiểm tra có nhập trình độ tiếng Nhật
			if (!Constant.ZERO_STRING.equals(userInfoEntity.getCodeLevel())) {
//              Lấy tblDetailUserJapanEntity
				TblDetailUserJapanEntity tblDetailUserJapanEntity = common.getTblDetailUserJapanEntity(userInfoEntity,
						userInfoEntity.getUserID());
//				Set connection cho tblDetailUserJapanDaoImpl
				tblDetailUserJapanDaoImpl.setConn(connection);
//				Nếu đã có trình độ tiếng Nhật trong DB. Thực hiện update
				if (checkHaveJapan) {
					tblDetailUserJapanDaoImpl.updateDetailUserJapan(tblDetailUserJapanEntity);
				} else {
//				    Nếu không có trình độ tiếng Nhật trong DB. Thực hiện insert
					tblDetailUserJapanDaoImpl.insertDetailUserJapan(tblDetailUserJapanEntity);
				}
			} else {
//              Kiểm tra không nhập trình độ tiếng Nhật
//				Nếu đã có trình độ tiếng Nhật trong DB. Thực hiện delete   
				if (checkHaveJapan) {
//						Set connection cho tblDetailUserJapanDaoImpl
					tblDetailUserJapanDaoImpl.setConn(connection);
					tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userInfoEntity.getUserID());
				}
			}
//			Thực hiện commit
			tblUserDaoImpl.commit();
			check = true;
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
//			Thực hiện rollback()
			tblUserDaoImpl.rollback();
			System.out.println("TblUserLogicImpl.java, updateUser " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			tblUserDaoImpl.closeConnectDatabase();
		}
		return check;
	}

	/**
	 * phương thức checkAdmin là phương thức kiểm tra user có userID có là admin
	 * 
	 * @param userID mã của user
	 * @return true là admin, false nếu ngược lại
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public boolean checkAdmin(int userID) throws ClassNotFoundException, SQLException {
		boolean check = false;
		try {
//		    Khởi tạo 
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
//          Lấy ra rule theo userID	
			int rule = tblUserDaoImpl.getRule(userID);
			if (rule == Constant.ZERO) {
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl.java, checkAdmin " + e.getMessage());
			throw e;
		}
		return check;
	}

	/**
	 * phương thức deleteUser là phương thức xóa user khi click button xóa tại
	 * ADM005
	 * 
	 * @param userID mã của user
	 * @return true xóa thành công, false nếu ngược lại
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public boolean deleteUser(int userID) throws ClassNotFoundException, SQLException {
		boolean checkDeleteUser = false;
		Connection connection = null;
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		try {
//          Khởi tạo 
			TblDetailUserJapanLogic tblDetailUserJapanLogicImpl = new TblDetailUserJapanLogicImpl();
//		    Check tồn tại trình độ tiếng Nhật
			boolean checkHaveJapan = tblDetailUserJapanLogicImpl.checkDatailUserJapan(userID);
//			Trasaction
			tblUserDaoImpl.connectDatabase();
			tblUserDaoImpl.setAutoCommit(false);
//			Nếu có trình độ tiếng Nhật
			if (checkHaveJapan) {
//				Lấy ra connection
				connection = tblUserDaoImpl.getConn();
				TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
//				Set connection
				tblDetailUserJapanDaoImpl.setConn(connection);
//				Xóa trình độ tiếng Nhật
				tblDetailUserJapanDaoImpl.deleteDetailUserJapan(userID);
//              Xóa thông tin user trong tbl_user
				tblUserDaoImpl.deleteTblUser(userID);
			} else {
//              Không có trình độ tiếng Nhật
				tblUserDaoImpl.deleteTblUser(userID);
			}
//			Commit
			tblUserDaoImpl.commit();
			checkDeleteUser = true;
		} catch (ClassNotFoundException | SQLException e) {
//			Thực hiện rollback()
			tblUserDaoImpl.rollback();
			System.out.println("TblUserLogicImpl.java, deleteUser " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			tblUserDaoImpl.closeConnectDatabase();
		}
		return checkDeleteUser;
	}
}