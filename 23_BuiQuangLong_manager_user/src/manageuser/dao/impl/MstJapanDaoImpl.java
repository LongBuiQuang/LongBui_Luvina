/**
 * Copyright(C) 2020 Luvina JSC
 * MstJapanDaoImpl.java, 7/12/2020 Bui Quang Long
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapanEntity;

/**
 * 
 * class MstJapanDaoImpl chứa các phương thức thao tác với bảng mst_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/**
	 * phương thức getAllMstJapan là phương thức lấy ra tất cả MstJapanEntity từ
	 * Database
	 * 
	 * @return mảng đối tượng MstJapanEntity
	 * @throws SQLException
	 */
	@Override
	public List<MstJapanEntity> getAllMstJapan() throws SQLException, ClassNotFoundException {
		List<MstJapanEntity> listMstJapanEntity = new ArrayList<>();
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//			Nếu kết nối thành công
			if (conn != null) {
//		        Tạo chuỗi sql 
				StringBuilder sql = new StringBuilder();
//		        Nối chuỗi
				sql.append("select * from mst_japan");
//				Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Vòng lặp để lấy hết bản ghi trả về
				while (rs.next()) {
//					Khởi tạo đối tượng mstJapanEntity
					MstJapanEntity mstJapanEntity = new MstJapanEntity();
//					Set giá trị cho thuộc tính code_level của đối tượng mstJapanEntity
					mstJapanEntity.setCodeLevel(rs.getString(1));
//					Set giá trị cho thuộc tính name_level của đối tượng mstJapanEntity
					mstJapanEntity.setNameLevel(rs.getString(2));
//					Thêm đối tượng mstJapanEntity vào mảng listMstJapanEntity
					listMstJapanEntity.add(mstJapanEntity);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("MstJapanDaoImpl.java, getAllMstJapan " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			closeConnectDatabase();
		}
//      Trả về listMstJapanEntity
		return listMstJapanEntity;
	}

	/**
	 * phương thức getMstJapanEntity là phương thức lấy ra đối tượng MstJapanEntity
	 * từ database
	 * 
	 * @param codeLevel là mã trình độ tiếng nhật
	 * @return đối tượng MstJapanEntity
	 * @throws SQLException, ClassNotFoundException
	 */
	@Override
	public MstJapanEntity getMstJapanEntity(String codeLevel) throws SQLException, ClassNotFoundException {
//		Khai báo đối tượng mstJapanEntity
		MstJapanEntity mstJapanEntity = null;
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//			Nếu kết nối thành công
			if (conn != null) {
//		        Tạo chuỗi sql 
				StringBuilder sql = new StringBuilder();
//		        Nối chuỗi
				sql.append("select * from mst_japan mj where mj.code_level = ?");
//				Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//				set giá trị cho dấu ?
				stmt.setString(1, codeLevel);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu tồn tại bản ghi trả về
				if (rs.next()) {
//					Khởi tạo đối tượng mstJapanEntity
					mstJapanEntity = new MstJapanEntity();
//					Set giá trị cho thuộc tính code_level của đối tượng mstJapanEntity
					mstJapanEntity.setCodeLevel(rs.getString(1));
//					Set giá trị cho thuộc tính name_level của đối tượng mstJapanEntity
					mstJapanEntity.setNameLevel(rs.getString(2));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl.java, getMstJapanEntity " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			closeConnectDatabase();
		}
		return mstJapanEntity;
	}
}