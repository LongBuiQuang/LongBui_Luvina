/**
 * Copyright(C) 2020 Luvina JSC
 * MstJapanLogic.java, 7/12/2020 Bui Quang Long
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapanEntity;

/**
 *
 * interface MstJapanLogic khai báo các phương thức gọi đến phương thức ở tầng
 * Dao bảng mst_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface MstJapanLogic {
	/**
	 * phương thức getAllMstJapan là phương thức lấy ra tất cả MstJapanEntity
	 * 
	 * @return mảng đối tượng MstJapanEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<MstJapanEntity> getAllMstJapan() throws SQLException, ClassNotFoundException;

	/**
	 * phương thức getMstJapanEntity là phương thức lấy ra đối tượng MstJapanEntity
	 * 
	 * @param codeLevel là mã trình độ tiếng nhật
	 * @return đối tượng MstJapanEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstJapanEntity getMstJapanEntity(String codeLevel) throws ClassNotFoundException, SQLException;
}
