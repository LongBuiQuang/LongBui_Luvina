/**
 * Copyright(C) 2020 Luvina JSC
 * TblDetailUserJapanLogic.java, 21/12/2020 Bui Quang Long
 */
package manageuser.logics;

import java.sql.SQLException;

/**
 * 
 * interface TblDetailUserJapanLogic khai báo các phương thức gọi đến phương
 * thức ở tầng Dao bảng tbl_detail_user_japan
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public interface TblDetailUserJapanLogic {

	/**
	 * phương thức checkDatailUserJapan là phương thức để kiểm tra có trình độ tiếng
	 * Nhật ứng với userID
	 * 
	 * @param userID là iD của userInfor
	 * @return true nếu có trình độ tiếng nhật ứng với userID, ngược lại trả về
	 *         false
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkDatailUserJapan(int userID) throws ClassNotFoundException, SQLException;
}
