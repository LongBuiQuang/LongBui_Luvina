/**
 * Copyright(C) 2020 Luvina JSC
 * DeleteUserController.java, 24/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * class DeleteUserController xóa userInfor tại ADM005
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.DELETE_USERINFOR_DO })
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doPost là phương thức được phương thức service() gọi khi người
	 * dùng ấn xác nhận xóa trên màn hình ADM005
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		boolean checkDeleteUser = false;
		try {
//		    Khởi tạo common
			Common common = new Common();
			int userID = common.convertStringToInt(request.getParameter(Constant.ID_USER_INFOR), Constant.ZERO);
//		    Khởi tạo 
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//		    Kiểm tra tồn tại của user
			boolean checkUser = tblUserLogicImpl.checkTblUser(userID);
//			Nếu tồn tại user
			if (checkUser) {
//              Kiểm tra Admin
				boolean checkAdmin = tblUserLogicImpl.checkAdmin(userID);
//				Nếu không là Admin
				if (!checkAdmin) {
//					Xóa userInfor
					checkDeleteUser = tblUserLogicImpl.deleteUser(userID);
				} else {
//                  Hiển thị thông báo không thể xóa admin
					response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER020);
				}
			} else {
//              Hiển thị thông báo không tồn tại user
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER014);
			}
//			Nếu xóa user thành công
			if (checkDeleteUser) {
				response.sendRedirect(contextpath + Constant.SUCCESS + "?type=" + Constant.MSG003);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("DeleteUserController.java, doPost " + e.getMessage());
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}
}