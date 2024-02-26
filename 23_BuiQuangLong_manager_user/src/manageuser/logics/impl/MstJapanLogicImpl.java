/**
 * Copyright(C) 2020 Luvina JSC
 * MstJapanLogicImpl.java, 7/12/2020 Bui Quang Long
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapanEntity;
import manageuser.logics.MstJapanLogic;

/**
 * 
 * class MstJapanLogicImpl có các phương thức gọi đến phương thức ở tầng Dao
 * bảng mst_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	/**
	 * phương thức getAllMstJapan là phương thức lấy ra tất cả MstJapanEntity
	 * 
	 * @return mảng đối tượng MstJapanEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<MstJapanEntity> getAllMstJapan() throws SQLException, ClassNotFoundException {
//		Khai báo mảng listMstJapanEntity
		List<MstJapanEntity> listMstJapanEntity = new ArrayList<>();
		try {
//      Khởi tạo đối tượng
			MstJapanDao mstJapanDaoImpl = new MstJapanDaoImpl();
//			Lấy ra mảng listMstJapanEntity
			listMstJapanEntity = mstJapanDaoImpl.getAllMstJapan();
		} catch (ClassNotFoundException | SQLException e) {
//			ghi log
			System.out.println("MstJapanLogicImpl.java, getAllMstJapan " + e.getMessage());
//			ném ra ngoại lệ
			throw e;
		}
//		Trả về mảng listMstJapanEntity
		return listMstJapanEntity;
	}

	/**
	 * phương thức getMstJapanEntity là phương thức lấy ra đối tượng MstJapanEntity
	 * 
	 * @param codeLevel là mã trình độ tiếng nhật
	 * @return đối tượng MstJapanEntity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public MstJapanEntity getMstJapanEntity(String codeLevel) throws ClassNotFoundException, SQLException {
		MstJapanEntity mstJapanEntity = null;
		try {
//		    Khởi tạo đối tượng mstJapanDaoImpl
			MstJapanDao mstJapanDaoImpl = new MstJapanDaoImpl();
//		    Lấy ra đối tượng mstJapanEntity
			mstJapanEntity = mstJapanDaoImpl.getMstJapanEntity(codeLevel);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("MstJapanLogicImpl.java, getMstJapanEntity " + e.getMessage());
			throw e;
		}
		return mstJapanEntity;
	}
}
