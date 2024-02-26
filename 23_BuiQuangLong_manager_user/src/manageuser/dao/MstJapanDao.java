/**
 * Copyright(C) 2020 Luvina JSC
 * MstJapanDao.java, 7/12/2020 Bui Quang Long
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;
import manageuser.entities.MstJapanEntity;

/**
 * 
 * interface MstJapanDao khai báo các phương thức thao tác với bảng mst_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface MstJapanDao extends BaseDao {
	/**
	 * phương thức getAllMstJapan là phương thức lấy ra tất cả MstJapanEntity từ
	 * Database
	 * 
	 * @return mảng đối tượng MstJapanEntity
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstJapanEntity> getAllMstJapan() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getMstJapanEntity là phương thức lấy ra đối tượng MstJapanEntity
	 * từ database
	 * 
	 * @param codeLevel là mã trình độ tiếng nhật
	 * @return đối tượng MstJapanEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstJapanEntity getMstJapanEntity(String codeLevel) throws SQLException, ClassNotFoundException;
}
