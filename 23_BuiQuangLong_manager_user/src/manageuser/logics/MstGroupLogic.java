/**
 * Copyright(C) 2020 Luvina JSC
 * MstGroupLogic.java, 22/11/2020 Bui Quang Long
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntity;

/**
 * 
 * interface MstGroupLogic khai báo các phương thức gọi đến phương thức ở tầng
 * Dao bảng mst_group
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface MstGroupLogic {

	/**
	 * phương thức getAllMstGroup là phương thức lấy ra tất cả group
	 * 
	 * @return mảng đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<MstGroupEntity> getAllMstGroup() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getMstGroupEntity là phương thức lấy group dựa theo groupId
	 * 
	 * @param int groupId
	 * @return đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstGroupEntity getMstGroupEntity(int groupId) throws ClassNotFoundException, SQLException;
}
