/**
 * Copyright(C) 2020 Luvina JSC
 * ViewUserInforController.java, 19/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.UserInfoEntity;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * class ViewUserInforController xử lý hiển thị thông tin user tại ADM005
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.VIEW_USERINFOR_DO })
public class ViewDetailUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi người
	 * dùng click vào link iD user trên màn hình ADM002
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//		    Khởi tạo đối tượng common
			Common common = new Common();
//          Lấy iDUserInfor
			int iDUserInfor = common.convertStringToInt(request.getParameter(Constant.ID_USER_INFOR), Constant.ZERO);
//          Khởi tạo tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//          Khai báo biến check
			boolean checkUser = tblUserLogicImpl.checkTblUser(iDUserInfor);
//          Kiểm tra Admin
			boolean checkAdmin = tblUserLogicImpl.checkAdmin(iDUserInfor);
//			Nếu tồn tại User và không là admin
			if (checkUser && !checkAdmin) {
				UserInfoEntity userInfoEntity = tblUserLogicImpl.getUserInforById(iDUserInfor);
				request.setAttribute(Constant.USER_INFOR_ENTITY, userInfoEntity);
//				Khởi tạo requestDispatcher
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM005);
//	            Điều hướng sang ADM005.jsp
				requestDispatcher.forward(request, response);
			} else {
//				Hiển thị thông báo ER013
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER013);
			}
		} catch (Exception e) {
			System.out.println("ViewUserInforController.java, doGet " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}
}