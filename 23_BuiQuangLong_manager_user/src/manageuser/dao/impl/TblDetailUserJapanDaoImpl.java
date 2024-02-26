/**
 * Copyright(C) 2020 Luvina JSC
 * TblDetailUserJapanDaoImpl.java, 7/12/2020 Bui Quang Long
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapanEntity;

/**
 * 
 * class TblDetailUserJapanDaoImpl chứa các phương thức thao tác với bảng
 * tbl_detail_user_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/**
	 * phương thức insertDetailUserJapan là phương thức thực hiện insert dữ liệu từ
	 * đối tượng tblDetailUserJapanEntity vào bảng tbl_detail_user_japan
	 * 
	 * @param tblDetailUserJapanEntity là đối tượng chứa thông tin trình độ tiếng
	 *                                 nhật
	 * @throws SQLException
	 */
	@Override
	public void insertDetailUserJapan(TblDetailUserJapanEntity tblDetailUserJapanEntity) throws SQLException {
//		Kiểm tra kết nối
		if (conn != null) {
			try {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("insert into tbl_detail_user_japan (user_id, code_level, start_date, end_date, total) ");
				sql.append("value (?, ?, ?, ?, ?) ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set giá trị cho user_id
				stmt.setInt(++i, tblDetailUserJapanEntity.getUserId());
//				Set giá trị cho code_level
				stmt.setString(++i, tblDetailUserJapanEntity.getCodeLevel());
//				Set giá trị cho start_date
				stmt.setString(++i, tblDetailUserJapanEntity.getStartDate());
//				Set giá trị cho end_date
				stmt.setString(++i, tblDetailUserJapanEntity.getEndDate());
//				Set giá trị cho total
				stmt.setString(++i, tblDetailUserJapanEntity.getTotal());
//			    Thực thi truy vấn
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("TblDetailUserJapanDaoImpl.java, insertDetailUserJapan " + e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * phương thức getDetailUserJapanEntity là phương thức lấy trình độ tiếng nhật
	 * trong bảng tbl_detail_user_japan ứng với userID
	 * 
	 * @param userID là mã của user
	 * @return mảng TblDetailUserJapanEntity
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public List<TblDetailUserJapanEntity> getDetailUserJapanEntity(int userID)
			throws ClassNotFoundException, SQLException {
		List<TblDetailUserJapanEntity> listTblDetailUserJapanEntity = new ArrayList<>();
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//	        Nếu kết nối thành công
			if (conn != null) {
//		        Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
//		        Nối chuỗi
				sql.append("select j.detail_user_japan_id, j.user_id ");
				sql.append("from tbl_detail_user_japan j ");
				sql.append("where j.user_id =? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Gán giá trị cho ?
				stmt.setInt(++i, userID);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu có bản ghi trả về
				if (rs.next()) {
//				    Khởi tạo biến k
					int k = 0;
//                  Khởi tạo tblDetailUserJapanEntity
					TblDetailUserJapanEntity tblDetailUserJapanEntity = new TblDetailUserJapanEntity();
//					Set detail_user_japan_id
					tblDetailUserJapanEntity.setDetailUserJapanId(rs.getInt(++k));
//					Set user_id
					tblDetailUserJapanEntity.setUserId(rs.getInt(++k));
//					Thêm vào mảng listTblDetailUserJapanEntity
					listTblDetailUserJapanEntity.add(tblDetailUserJapanEntity);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblDetailUserJapanDaoImpl.java, getDetailUserJapanEntity " + e.getMessage());
			throw e;
		}
		return listTblDetailUserJapanEntity;
	}

	/**
	 * phương thức updateDetailUserJapan là phương thức thực hiện update dữ liệu từ
	 * đối tượng tblDetailUserJapanEntity vào hàng tương ứng của bảng
	 * tbl_detail_user_japan
	 * 
	 * @param tblDetailUserJapanEntity là đối tượng chứa thông tin trình độ tiếng
	 *                                 nhật
	 * @throws SQLException
	 */
	@Override
	public void updateDetailUserJapan(TblDetailUserJapanEntity tblDetailUserJapanEntity) throws SQLException {
//		Kiểm tra kết nối
		if (conn != null) {
			try {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("update tbl_detail_user_japan ");
				sql.append("set code_level=?, start_date=?, end_date=?, total=? ");
				sql.append("where user_id =? ");
//			    Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Khởi tạo biến i
				int i = 0;
//				Set giá trị cho code_level
				stmt.setString(++i, tblDetailUserJapanEntity.getCodeLevel());
//				Set giá trị cho start_date
				stmt.setString(++i, tblDetailUserJapanEntity.getStartDate());
//				Set giá trị cho end_date
				stmt.setString(++i, tblDetailUserJapanEntity.getEndDate());
//				Set giá trị cho total
				stmt.setString(++i, tblDetailUserJapanEntity.getTotal());
//				Set giá trị cho user_id
				stmt.setInt(++i, tblDetailUserJapanEntity.getUserId());
//			    Thực thi truy vấn
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("TblDetailUserJapanDaoImpl.java, insertDetailUserJapan " + e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * phương thức deleteDetailUserJapan là phương thức thực hiện delete dữ liệu
	 * trong bảng tbl_detail_user_japan tương ứng user_id
	 * 
	 * @param userID là mã của user
	 * @throws SQLException
	 */
	@Override
	public void deleteDetailUserJapan(int userID) throws SQLException {
//		Kiểm tra kết nối
		if (conn != null) {
			try {
//			    Viết câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("delete from tbl_detail_user_japan where user_id = ? ");
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
				System.out.println("TblDetailUserJapanDaoImpl.java, deleteDetailUserJapan " + e.getMessage());
				throw e;
			}
		}
	}
}