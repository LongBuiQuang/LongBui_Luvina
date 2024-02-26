/**
 * Copyright(C) 2020 Luvina JSC
 * LoginController.java, 17/11/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * class LoginController xử lý hiển thị, nhập thông tin tại ADM001
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.LOGIN_DO })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi người
	 * dùng gõ đường dẫn
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		lấy đường dẫn đến file ADM001.jsp
		String pathADM001 = Constant.PATH_ADM001;
//		Tạo đối tượng RequestDispatcher
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(pathADM001);
//		Thực hiện forward request, response sang file ADM001.jsp
		requestDispatcher.forward(request, response);
	}

	/**
	 * phương thức doPost là phương thức được phương thức service() gọi khi người
	 * dùng submit form
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu từ phía máy client gửi về
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//	        Lấy ra loginName người dùng nhập vào được gửi về
			String loginName = request.getParameter(Constant.LOGIN_NAME);
//	        Lấy ra password người dùng nhập vào được gửi về
			String password = request.getParameter(Constant.PASSWORD);
//			Khởi tạo đối tượng ValidateUser
			ValidateUser validateUser = new ValidateUser();
//			Gọi phương thức validateLogin để trả về các message
			List<String> listMessage = validateUser.validateLogin(loginName, password);
//			Nếu mảng listMessage có size = 0 chứng tỏ người dùng nhập đúng
			if (listMessage.size() == Constant.ZERO) {
//				Khởi tạo session
				HttpSession session = request.getSession();
//				Gán giá trị loginName vào session
				session.setAttribute(Constant.LOGIN_NAME, loginName);
//				Gọi sendRedirect("listuser") để chuyển hướng sang servlet ListUserController
				response.sendRedirect(request.getContextPath() + Constant.LIST_USER_DO);
			} else {
//				Ngược lại nếu kích cỡ mảng listMessage khác 0 
//				Gán giá trị loginName vào đối tượng request
				request.setAttribute(Constant.LOGIN_NAME, loginName);
//				Gán mảng listMessage vào đối tượng request
				request.setAttribute(Constant.LIST_MESSAGE, listMessage);
//				Tạo đối tượng requestDispatcher để chuyển tiếp sang file ADM001.jsp
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM001);
//				Chuyển tiếp đối tượng request, response sang file ADM001.jsp
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
//			Ghi log
			System.out.println("File LoginController.java, doPost " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}

	}

}