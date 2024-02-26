/**
 * Copyright(C) 2020 Luvina JSC
 * MstGroupDaoImpl.java, 22/11/2020 Bui Quang Long
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroupEntity;

/**
 * 
 * class MstGroupDaoImpl chứa các phương thức thao tác với bảng mst_group
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/**
	 * phương thức getAllMstGroup là phương thức lấy ra tất cả group từ Database
	 * 
	 * @return mảng đối tượng MstGroupEntity
	 * @throws SQLException
	 */
	@Override
	public List<MstGroupEntity> getAllMstGroup() throws SQLException, ClassNotFoundException {
//		Khai báo mảng listGroup 
		List<MstGroupEntity> listGroup = new ArrayList<>();
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//			Nếu kết nối thành công
			if (conn != null) {
//		        Tạo chuỗi sql 
				StringBuilder sql = new StringBuilder();
//		        Nối chuỗi
				sql.append("select * from mst_group");
//				Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Vòng lặp để lấy hết bản ghi trả về
				while (rs.next()) {
//					Khởi tạo đối tượng mstGroupEntity
					MstGroupEntity mstGroupEntity = new MstGroupEntity();
//					Set giá trị cho thuộc tính groupId của đối tượng mstGroupEntity
					mstGroupEntity.setGroupId(rs.getInt(1));
//					Set giá trị cho thuộc tính groupName của đối tượng mstGroupEntity
					mstGroupEntity.setGroupName(rs.getString(2));
//					Thêm đối tượng mstGroupEntity vào mảng listGroup
					listGroup.add(mstGroupEntity);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl.java, getAllMstGroup " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			closeConnectDatabase();
		}
//		Trả về listGroup
		return listGroup;
	}

	/**
	 * phương thức getMstGroupEntity là phương thức lấy group từ database dựa theo
	 * groupId
	 * 
	 * @param int groupId
	 * @return đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public MstGroupEntity getMstGroupEntity(int groupId) throws SQLException, ClassNotFoundException {
//		Khai báo đối tượng mstGroupEntity
		MstGroupEntity mstGroupEntity = null;
		try {
//		    Tạo kết nối đến Database
			connectDatabase();
//			Nếu kết nối thành công
			if (conn != null) {
//		        Tạo chuỗi sql 
				StringBuilder sql = new StringBuilder();
//		        Nối chuỗi
				sql.append("select * from mst_group g where g.group_id = ?");
//				Khai báo một đối tượng PreparedStatement
				PreparedStatement stmt;
//			    Khởi tạo đối tượng stmt
				stmt = (PreparedStatement) conn.prepareStatement(sql.toString());
//				set giá trị cho dấu ?
				stmt.setInt(1, groupId);
//			    Thực thi truy vấn, tạo đối tượng ResultSet
				ResultSet rs = stmt.executeQuery();
//			    Nếu tồn tại bản ghi trả về
				if (rs.next()) {
//					Khởi tạo đối tượng mstGroupEntity
					mstGroupEntity = new MstGroupEntity();
//					Set giá trị cho thuộc tính groupId của đối tượng mstGroupEntity
					mstGroupEntity.setGroupId(rs.getInt(1));
//					Set giá trị cho thuộc tính groupName của đối tượng mstGroupEntity
					mstGroupEntity.setGroupName(rs.getString(2));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl.java, getMstGroupEntity " + e.getMessage());
			throw e;
		} finally {
//			Đóng kết nối
			closeConnectDatabase();
		}
		return mstGroupEntity;
	}
}