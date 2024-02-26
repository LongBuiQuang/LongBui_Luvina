/**
 * Copyright(C) 2020 Luvina JSC
 * MstGroupLogicImpl.java, 22/11/2020 Bui Quang Long
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroupEntity;
import manageuser.logics.MstGroupLogic;

/**
 * 
 * class MstGroupLogicImpl có các phương thức gọi đến phương thức ở tầng
 * Dao bảng mst_group
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/**
	 * phương thức getAllMstGroup là phương thức lấy ra tất cả group
	 * 
	 * @return mảng đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<MstGroupEntity> getAllMstGroup() throws SQLException, ClassNotFoundException {
//		Khai báo mảng listMstGroup
		List<MstGroupEntity> listGroup = new ArrayList<>();
		try {
// 		    Khởi tạo đối tượng mstGroupDaoImpl
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
// 		    Lấy ra mảng listMstGroup
			listGroup = mstGroupDaoImpl.getAllMstGroup();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("MstGroupLogicImpl.java, getAllMstGroup " + e.getMessage());
			throw e;
		}
//		Trả về mảng listMstGroup
		return listGroup;
	}

	/**
	 * phương thức getMstGroupEntity là phương thức lấy group dựa theo groupId
	 * 
	 * @param int groupId
	 * @return đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public MstGroupEntity getMstGroupEntity(int groupId) throws ClassNotFoundException, SQLException {
//		Khai báo đối tượng mstGroupEntity
		MstGroupEntity mstGroupEntity = null;
		try {
//		    Khởi tạo đối tượng 
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
//		    Lấy giá trị cho đối tượng mstGroupEntity
			mstGroupEntity = mstGroupDaoImpl.getMstGroupEntity(groupId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("MstGroupLogicImpl.java, getMstGroupEntity " + e.getMessage());
			throw e;
		}
		return mstGroupEntity;
	}
}