/**
 * Copyright(C) 2020 Luvina JSC
 * TblDetailUserJapanLogicImpl.java, 21/12/2020 Bui Quang Long
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.entities.TblDetailUserJapanEntity;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.utils.Constant;

/**
 * 
 * class TblDetailUserJapanLogicImpl có các phương thức gọi đến phương thức ở
 * tầng Dao bảng tbl_detail_user_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	/**
	 * phương thức checkDatailUserJapan là phương thức để kiểm tra có trình độ tiếng
	 * Nhật ứng với userID
	 * 
	 * @param userID là iD của userInfor
	 * @return true nếu có trình độ tiếng nhật ứng với userID, ngược lại trả về
	 *         false
	 * @throws ClassNotFoundException, SQLException
	 */
	@Override
	public boolean checkDatailUserJapan(int userID) throws ClassNotFoundException, SQLException {
		boolean check = false;
		try {
//		    Khởi tạo 
			TblDetailUserJapanDaoImpl tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
//          Lấy ra mảng trình độ tiếng Nhật ứng với userID
			List<TblDetailUserJapanEntity> listTblDetailUserJapanEntity = tblDetailUserJapanDaoImpl
					.getDetailUserJapanEntity(userID);
			if (listTblDetailUserJapanEntity.size() != Constant.ZERO) {
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblDetailUserJapanLogicImpl.java, checkDatailUserJapan " + e.getMessage());
			throw e;
		}
		return check;
	}
}

