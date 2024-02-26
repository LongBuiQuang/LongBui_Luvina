/**
 * Copyright(C) 2020 Luvina JSC
 * LogoutController.java, 17/11/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * class LogoutController thoát ra màn hình ADM001
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.LOGOUT_DO })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi người
	 * dùng click vào nút logout
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//		    Lấy ra session
			HttpSession session = request.getSession();
//          Hủy toàn bộ session
			session.invalidate();
//	        Chuyển hướng sang LoginController.java
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_DO);
		} catch (Exception e) {
//			Ghi log
			System.out.println("LogoutController.java, doGet " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}
}
