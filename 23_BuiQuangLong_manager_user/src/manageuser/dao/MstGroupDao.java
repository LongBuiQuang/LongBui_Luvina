/**
 * Copyright(C) 2020 Luvina JSC
 * MstGroupDao.java, 22/11/2020 Bui Quang Long
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;
import manageuser.entities.MstGroupEntity;

/**
 * 
 * interface MstGroupDao khai báo các phương thức thao tác với bảng mst_group
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface MstGroupDao extends BaseDao {
	/**
	 * phương thức getAllMstGroup là phương thức lấy ra tất cả group từ Database
	 * 
	 * @return mảng đối tượng MstGroupEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstGroupEntity> getAllMstGroup() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getMstGroupEntity là phương thức lấy group từ database dựa theo
	 * groupId
	 * 
	 * @param int groupId
	 * @return đối tượng MstGroupEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstGroupEntity getMstGroupEntity(int groupId) throws SQLException, ClassNotFoundException;
}
