/**
 * Copyright(C) 2020 Luvina JSC
 * TblUserDaoImpl.java, 7/12/2020 Bui Quang Long
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.utils.Constant;

/**
 * 
 * class TblUserDaoImpl chứa các phương thức thao tác với bảng tbl_user
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/**
	 * phương thức getUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * admin từ Database
	 * 
	 * @param username là tên tài khoản admin
	 * @return trả về đối tượng TblUserEntity hoặc null
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public TblUserEntity getUserByLoginName(String username) throws SQLException, ClassNotFoundException {
//		Khai báo một đối tượng TblUserEntity
		TblUserEntity userEntity = null;
		try {
//			Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
//			    Nối chuỗi 
				sql.append("select u.password, u.salt ");
				sql.append("from tbl_user u ");
				sql.append("where u.login_name =? and u.rule = ? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//			    Set giá trị cho login_name trong câu sql 
				stmt.setString(++i, username);
//		        Set giá trị cho rule trong câu sql
				stmt.setString(++i, Constant.RULE_ADMIN);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				if (rs.next()) {
//					Khởi tạo đối tượng userEntity
					userEntity = new TblUserEntity();
//				    Set giá trị cho thuộc tính password của đối tượng userEntity
					userEntity.setPassWord(rs.getString(1));
//				    Set giá trị cho thuộc tính salt của đối tượng userEntity
					userEntity.setSalt(rs.getString(2));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getUserByLoginName " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối với Database
			closeConnectDatabase();
		}
//		Trả về đối tượng userEntity
		return userEntity;
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
//      Lấy ra danh sách cột để phòng chống lỗi SQL INJECTION cho mệnh đề ORDER BY
		List<String> listColumnName = getColumnNameFromDB();
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
				StringBuilder sql = new StringBuilder(
						"select u.user_id, u.full_name, u.birthday, g.group_name, u.email, u.tel, mj.name_level, j.end_date, j.total, mj.code_level ");
				sql.append("from tbl_user u ");
				sql.append("inner join mst_group g using(group_id) ");
				sql.append("left join tbl_detail_user_japan j using(user_id) ");
				sql.append("left join mst_japan mj using(code_level) where u.rule = ? ");
//			    Nếu groupId khác 0 
				if (groupId != 0) {
//				Nối thêm chuỗi
					sql.append("and group_id = ? ");
				}
//			    Nếu fullName khác rỗng
				if (!"".equals(fullName)) {
//				Nối thêm chuỗi 
					sql.append("and full_name like ? ");
				}
//              So sánh chuỗi "full_name" với sortType và kiểm tra sortType có nằm trong listColumnName
				if ("full_name".equals(sortType) && listColumnName.contains(sortType)) {
					sql.append("order by u.full_name ");
					sql.append(sortByFullName);
					sql.append(", mj.code_level ");
					sql.append(sortByCodeLevel);
					sql.append(", j.end_date ");
					sql.append(sortByEndDate);
					sql.append(" limit ?, ? ");
				}
//	            So sánh chuỗi "code_level" với sortType và kiểm tra sortType có nằm trong listColumnName
				if ("code_level".equals(sortType) && listColumnName.contains(sortType)) {
					sql.append("order by mj.code_level ");
					sql.append(sortByCodeLevel);
					sql.append(", u.full_name ");
					sql.append(sortByFullName);
					sql.append(", j.end_date ");
					sql.append(sortByEndDate);
					sql.append(" limit ?, ? ");
				}
//		        So sánh chuỗi "end_date" với sortType và kiểm tra sortType có nằm trong listColumnName
				if ("end_date".equals(sortType) && listColumnName.contains(sortType)) {
					sql.append("order by j.end_date ");
					sql.append(sortByEndDate);
					sql.append(", u.full_name ");
					sql.append(sortByFullName);
					sql.append(", mj.code_level ");
					sql.append(sortByCodeLevel);
					sql.append(" limit ?, ? ");
				}

//		        Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//		        Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//              Khởi tạo biến i
				int i = 0;
				stmt.setString(++i, Constant.RULE_USER);
//			    Nếu groupId khác 0 
				if (groupId != 0) {
//		            Set giá trị cho group_id trong câu sql 
					stmt.setInt(++i, groupId);
				}
//			    Nếu fullName khác rỗng
				if (!"".equals(fullName)) {
//		            Set giá trị cho fullName trong câu sql 
					stmt.setString(++i, "%" + fullName + "%");
				}
//              Set giá trị cho offset
				stmt.setInt(++i, offset);
//              Set giá trị cho limit
				stmt.setInt(++i, limit);
//		        Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//		        Duyệt hết các bản ghi trả về
				while (rs.next()) {
//                  Khởi tạo một đố tượng UserInfoEntity
					UserInfoEntity userInfoEntity = new UserInfoEntity();
					int k = 0;
//				    Gán giá trị cho thuộc tính userID
					userInfoEntity.setUserID(rs.getInt(++k));
//				    Gán giá trị cho thuộc tính fullName
					userInfoEntity.setFullName(rs.getString(++k));
//				    Gán giá trị cho thuộc tính birthday
					userInfoEntity.setBirthday(rs.getString(++k));
//				    Gán giá trị cho thuộc tính groupName
					userInfoEntity.setGroupName(rs.getString(++k));
//				    Gán giá trị cho thuộc tính email
					userInfoEntity.setEmail(rs.getString(++k));
//				    Gán giá trị cho thuộc tính tel
					userInfoEntity.setTel(rs.getString(++k));
//				    Gán giá trị cho thuộc tính nameLevel
					userInfoEntity.setNameLevel(rs.getString(++k));
//				    Gán giá trị cho thuộc tính endDate
					userInfoEntity.setEndDate(rs.getString(++k));
//				    Gán giá trị cho thuộc tính total
					userInfoEntity.setTotal(rs.getString(++k));
//				    Thêm đối tượng userInfoEntity vào mảng listUserInfoEntity
					listUserInfoEntity.add(userInfoEntity);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getListUsers " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối với Database
			closeConnectDatabase();
		}
		return listUserInfoEntity;
	}

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
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
//		Biến totalUser để lưu tổng số bản ghi
		int totalUser = 0;
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
//			    Tạo câu sql
				StringBuilder sql = new StringBuilder();
				sql.append("select count(u.user_id) ");
				sql.append("from mst_group g ");
				sql.append("inner join tbl_user u using(group_id) ");
				sql.append("where u.rule = ? ");
//		        Nếu groupId khác 0 
				if (groupId != 0) {
//			        Nối thêm chuỗi
					sql.append("and group_id = ? ");
				}
//		        Nếu fullName khác rỗng
				if (!"".equals(fullName)) {
//			        Nối thêm chuỗi 
					sql.append("and full_name like ? ");
				}
//		        Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//				Khởi tạo biến i
				int i = 0;
//		        Set giá trị cho rule trong câu sql 	
				stmt.setString(++i, Constant.RULE_USER);
//			    Nếu groupId khác 0 
				if (groupId != 0) {
//		            Set giá trị cho group_id trong câu sql 
					stmt.setInt(++i, groupId);
				}
//			    Nếu fullName khác rỗng
				if (!"".equals(fullName)) {
//		            Set giá trị cho fullName trong câu sql 
					stmt.setString(++i, "%" + fullName + "%");
				}
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu tồn tại bản ghi trả về
				if (rs.next()) {
//					Lấy giá trị totalUser
					totalUser = rs.getInt(1);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getTotalUsers " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối với Database
			closeConnectDatabase();
		}
		return totalUser;
	}

	/**
	 * phương thức getColumnNameFromDB là phương thức lấy ra tên các cột
	 * 
	 * @return mảng chưa tên cột của các bảng tbl_user, mst_japan,
	 *         tbl_detail_user_japan
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<String> getColumnNameFromDB() throws SQLException, ClassNotFoundException {
//		Tạo mảng listColumnName để lưu tên của các cột
		List<String> listColumnName = new ArrayList<>();
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
//				Tạo câu lệnh sql
				StringBuilder sql = new StringBuilder();
				sql.append("select column_name ");
				sql.append("from information_schema.columns ");
				sql.append("where table_schema = ? ");
				sql.append("and table_name = ? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt	
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//				Khởi tạo biến i
				int i = 0;
//				Set giá trị tên database cho ? đầu tiên
				stmt.setString(++i, Constant.DATABASENAME);
//				Set giá trị tên bảng cho ? thứ 2
				stmt.setString(++i, Constant.TBL_USER);
//				Thực thi truy vấn
				ResultSet rs1 = stmt.executeQuery();
//				Duyệt từng dòng của rs1
				while (rs1.next()) {
//					Lấy ra tên column và thêm vào mảng listColumnName
					listColumnName.add(rs1.getString(1));
				}
//				Set giá trị tên bảng cho ? thứ 2
				stmt.setString(i, Constant.MST_JAPAN);
//				Thực thi truy vấn
				ResultSet rs2 = stmt.executeQuery();
//				Duyệt từng dòng của rs2
				while (rs2.next()) {
//					Lấy ra tên column và thêm vào mảng listColumnName
					listColumnName.add(rs2.getString(1));
				}
//				Set giá trị tên bảng cho ? thứ 2
				stmt.setString(i, Constant.TBL_DETAIL_USER_JAPAN);
//				Thực thi truy vấn
				ResultSet rs3 = stmt.executeQuery();
//				Duyệt từng dòng của rs2
				while (rs3.next()) {
//					Lấy ra tên column và thêm vào mảng listColumnName
					listColumnName.add(rs3.getString(1));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getColumnNameFromDB " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			closeConnectDatabase();
		}
		return listColumnName;
	}

	/**
	 * phương thức getAllUserByLoginName là phương thức khai báo để lấy ra tài khoản
	 * (cả admin và khách hàng)
	 * 
	 * @param username là tên tài khoản khách hàng
	 * @return trả về mảng đối tượng TblUserEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<TblUserEntity> getAllUserByLoginName(String username) throws SQLException, ClassNotFoundException {
		List<TblUserEntity> listTblUserEntity = new ArrayList<>();
		try {
//			Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
//			    Nối chuỗi 
				sql.append("select u.user_id, u.login_name, u.password, u.salt ");
				sql.append("from tbl_user u ");
				sql.append("where u.login_name =? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//			    Set giá trị cho login_name trong câu sql 
				stmt.setString(++i, username);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				while (rs.next()) {
//					Khởi tạo đối tượng userEntity
					TblUserEntity userEntity = new TblUserEntity();
					int k = 0;
//					Set giá trị cho thuộc tính userID của đối tượng userEntity
					userEntity.setUserID(rs.getInt(++k));
//					Set giá trị cho thuộc tính loginName của đối tượng userEntity
					userEntity.setLoginName(rs.getString(++k));
//				    Set giá trị cho thuộc tính password của đối tượng userEntity
					userEntity.setPassWord(rs.getString(++k));
//				    Set giá trị cho thuộc tính salt của đối tượng userEntity
					userEntity.setSalt(rs.getString(++k));
//					Thêm userEntity vào mảng listTblUserEntity
					listTblUserEntity.add(userEntity);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getAllUserByLoginName " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối với Database
			closeConnectDatabase();
		}
		return listTblUserEntity;
	}

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
	@Override
	public List<TblUserEntity> getUserByEmail(String email, int userID) throws SQLException, ClassNotFoundException {
		List<TblUserEntity> listTblUserEntity = new ArrayList<>();
		try {
//			Tạo kết nối đến Database
			connectDatabase();
//		    Nếu kết nối thành công
			if (conn != null) {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
//			    Nối chuỗi 
				sql.append("select u.user_id, u.email ");
				sql.append("from tbl_user u ");
				sql.append("where u.email =? ");
//				Trường hợp edit thì thêm 
//				Trường hợp edit. Tìm tất cả bản ghi có email bằng email edit, và các bản ghi muốn lấy ra không bao gồm bản ghi edit
				if (userID != 0) {
					sql.append("and u.user_id !=? ");
				}
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//			    Set giá trị cho email trong câu sql 
				stmt.setString(++i, email);
//				Nếu là trường hợp edit thì set thêm cho ? thứ 2
				if (userID != 0) {
					stmt.setInt(++i, userID);
				}
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				while (rs.next()) {
//					Khởi tạo đối tượng tblUserEntity
					TblUserEntity tblUserEntity = new TblUserEntity();
					int k = 0;
//					Set giá trị cho thuộc tính user_id của đối tượng tblUserEntity
					tblUserEntity.setUserID(rs.getInt(++k));
//					Set giá trị cho thuộc tính email của đối tượng tblUserEntity
					tblUserEntity.setEmail(rs.getString(++k));
//					Thêm userEntity vào listTblUserEntity
					listTblUserEntity.add(tblUserEntity);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getUserByEmail " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối với Database
			closeConnectDatabase();
		}
		return listTblUserEntity;
	}

	/**
	 * phương thức insertUser là phương thức thực hiện insert dữ liệu từ đối tượng
	 * tblUserEntity vào bảng tbl_user trong database
	 * 
	 * @param tblUserEntity là đối tượng chứa thông tin của 1 user
	 * @return iDUser là mã của bản ghi được insert
	 * @throws SQLException
	 */
	@Override
	public int insertUser(TblUserEntity tblUserEntity) throws SQLException {
		int iDUser = 0;
//      Kiểm tra kết nối
		if (conn != null) {
			try {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append(
						"insert into tbl_user (group_id, login_name, password, full_name, full_name_kana, email, tel, birthday, rule, salt) ");
				sql.append("value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
//			    Khởi tạo biến i
				int i = 0;
//		        Set giá trị cho group_id
				stmt.setInt(++i, tblUserEntity.getGroupID());
//		        Set giá trị cho login_name
				stmt.setString(++i, tblUserEntity.getLoginName());
//			    Set giá trị cho password
				stmt.setString(++i, tblUserEntity.getPassWord());
//			    Set giá trị cho full_name
				stmt.setString(++i, tblUserEntity.getFullName());
//			    Set giá trị cho full_name_kana
				stmt.setString(++i, tblUserEntity.getFullNameKana());
//			    Set giá trị cho email
				stmt.setString(++i, tblUserEntity.getEmail());
//			    Set giá trị cho tel
				stmt.setString(++i, tblUserEntity.getTel());
//			    Set giá trị cho birthday
				stmt.setString(++i, tblUserEntity.getBirthday());
//			    Set giá trị cho rule
				stmt.setInt(++i, tblUserEntity.getRule());
//			    Set giá trị cho salt
				stmt.setString(++i, tblUserEntity.getSalt());
//			    Thực thi truy vấn
				int value = stmt.executeUpdate();
//			    Nếu insert thành công
				if (value > 0) {
//				Vì user_id được tạo tự động sử dụng getGeneratedKeys()
					ResultSet rs = stmt.getGeneratedKeys();
					if (rs.next()) {
						iDUser = rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				System.out.println("TblUserDaoImpl.java, insertUser " + e.getMessage());
				throw e;
			}
		}
//		Trả về iDUser
		return iDUser;
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
//		Khai báo userInfoEntity
		UserInfoEntity userInfoEntity = null;
		try {
			connectDatabase();
//			Kiểm tra kết nối
			if (conn != null) {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append(
						"select u.login_name, g.group_id, g.group_name, u.full_name, u.full_name_kana, u.birthday, u.email, u.tel, mj.code_level, mj.name_level, j.start_date, j.end_date, j.total ");
				sql.append("from tbl_user u ");
				sql.append("inner join mst_group g using(group_id) ");
				sql.append("left join tbl_detail_user_japan j using(user_id) ");
				sql.append("left join mst_japan mj using(code_level) ");
				sql.append("where u.user_id =? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set giá trị cho dấu ? trong câu sql
				stmt.setInt(++i, iDUserInfor);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				while (rs.next()) {
//                  Khởi tạo đối tượng 
					userInfoEntity = new UserInfoEntity();
//					Khởi tạo k
					int k = 0;
					userInfoEntity.setLoginName(rs.getString(++k));
					userInfoEntity.setGroupId(rs.getInt(++k));
					userInfoEntity.setGroupName(rs.getString(++k));
					userInfoEntity.setFullName(rs.getString(++k));
					userInfoEntity.setFullNameKana(rs.getString(++k));
					userInfoEntity.setBirthday(rs.getString(++k));
					userInfoEntity.setEmail(rs.getString(++k));
					userInfoEntity.setTel(rs.getString(++k));
					userInfoEntity.setCodeLevel(rs.getString(++k));
					userInfoEntity.setNameLevel(rs.getString(++k));
					userInfoEntity.setStartDate(rs.getString(++k));
					userInfoEntity.setEndDate(rs.getString(++k));
					userInfoEntity.setTotal(rs.getString(++k));
//					Set iD cho userInfoEntity
					userInfoEntity.setUserID(iDUserInfor);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl.java, getUserInforById " + e.getMessage());
			throw e;
		} finally {
//          Đóng kết nối
			closeConnectDatabase();
		}
//		Trả về userInfoEntity
		return userInfoEntity;
	}

	/**
	 * phương thức getTblUserEntity là phương thức lấy ra thông tin TblUserEntity
	 * theo iDUserInfor
	 * 
	 * @param iDUserInfor là iD của user trong bảng tbl_user muốn lấy ra
	 * @return TblUserEntity là đối tượng chứa thông tin bản ghi trong bảng tbl_user
	 * @throws ClassNotFoundException
	 */
	@Override
	public TblUserEntity getTblUserEntity(int iDUserInfor) throws ClassNotFoundException, SQLException {
//      Khai báo đối tượng
		TblUserEntity tblUserEntity = null;
		try {
			connectDatabase();
//		    Kiểm tra kết nối
			if (conn != null) {
//		    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("select u.login_name ");
				sql.append("from tbl_user u ");
				sql.append("where u.user_id =? ");
//		        Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//		        Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set giá trị cho dấu ? trong câu sql
				stmt.setInt(++i, iDUserInfor);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				while (rs.next()) {
//					Khởi tạo đối tượng tblUserEntity
					tblUserEntity = new TblUserEntity();
//					Set giá trị thuộc tính LoginName
					tblUserEntity.setLoginName(rs.getString(1));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl.java, getTblUserEntity " + e.getMessage());
			throw e;
		} finally {
//          Đóng kết nối
			closeConnectDatabase();
		}
		return tblUserEntity;
	}

	/**
	 * phương thức updateUser là phương thức cập nhật thông tin user vào bảng
	 * tbl_user
	 * 
	 * @param tblUserEntity là đối tượng chứa thông tin user
	 * @throws SQLException
	 */
	@Override
	public void updateUser(TblUserEntity tblUserEntity) throws SQLException {
		try {
//	        Kiểm tra kết nối
			if (conn != null) {
//	            Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("update tbl_user ");
				sql.append("set group_id =?, full_name =?, full_name_kana =?, email =?, tel =?, birthday =? ");
				sql.append("where user_id =? ");
//	            Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//	            Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//		        Khởi tạo biến i
				int i = 0;
//			    Set group_id
				stmt.setInt(++i, tblUserEntity.getGroupID());
//			    Set full_name
				stmt.setString(++i, tblUserEntity.getFullName());
//			    Set full_name_kana
				stmt.setString(++i, tblUserEntity.getFullNameKana());
//			    Set email
				stmt.setString(++i, tblUserEntity.getEmail());
//			    Set tel	
				stmt.setString(++i, tblUserEntity.getTel());
//			    Set birthday
				stmt.setString(++i, tblUserEntity.getBirthday());
//			    Set user_id
				stmt.setInt(++i, tblUserEntity.getUserID());
//			    Thực hiện update
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("TblUserDaoImpl.java, updateUser " + e.getMessage());
			throw e;
		}
	}

	/**
	 * phương thức getRule là phương thức lấy ra rule của user theo userID
	 * 
	 * @param userID là mã của user
	 * @return giá trị rule
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public int getRule(int userID) throws ClassNotFoundException, SQLException {
		int rule = 1;
		try {
			connectDatabase();
//			Kiểm tra kết nối
			if (conn != null) {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("select u.rule ");
				sql.append("from tbl_user u ");
				sql.append("where u.user_id =? ");
//		        Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//		        Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set giá trị cho dấu ? trong câu sql
				stmt.setInt(++i, userID);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				while (rs.next()) {
//					Set giá trị thuộc tính LoginName
					rule = rs.getInt(1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl.java, getRule " + e.getMessage());
			throw e;
		} finally {
//          Đóng kết nối
			closeConnectDatabase();
		}
		return rule;
	}

	/**
	 * phương thức deleteTblUser là phương thức xóa thông tin user tại bảng tbl_user
	 * (chức năng xóa tại ADM005) theo userID
	 * 
	 * @param userID là mã của user
	 * @throws SQLException
	 */
	@Override
	public void deleteTblUser(int userID) throws SQLException {
//		Kiểm tra kết nối
		if (conn != null) {
			try {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("delete from tbl_user where user_id = ? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set user_id
				stmt.setInt(++i, userID);
//				Thực hiện delete
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("TblUserDaoImpl.java, deleteTblUser " + e.getMessage());
				throw e;
			}
		}
	}

}