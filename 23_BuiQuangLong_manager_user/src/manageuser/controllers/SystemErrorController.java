/**
 * Copyright(C) 2020 Luvina JSC
 * SystemErrorController.java, 15/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.properties.MessageErrorProperties;
import manageuser.utils.Constant;

/**
 * class SystemErrorController hiển thị câu thông báo lỗi tại màn hình System
 * error
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.ERROR })
public class SystemErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi được
	 * controller khác sendRedirect sang
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Lấy ra type 
		String type = request.getParameter(Constant.TYPE);
//		Kiểm tra type
		if (Constant.ER015.equals(type)) {
			String message = MessageErrorProperties.getValueByKey(Constant.ER015);
			request.setAttribute(Constant.MESSAGE, message);
		} else if (Constant.ER013.equals(type)) {
			String message = MessageErrorProperties.getValueByKey(Constant.ER013);
			request.setAttribute(Constant.MESSAGE, message);
		} else if (Constant.ER020.equals(type)) {
			String message = MessageErrorProperties.getValueByKey(Constant.ER020);
			request.setAttribute(Constant.MESSAGE, message);
		} else if (Constant.ER014.equals(type)) {
			String message = MessageErrorProperties.getValueByKey(Constant.ER014);
			request.setAttribute(Constant.MESSAGE, message);
		}
//      Forward sang System error
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_SYSTEM_ERROR);
		requestDispatcher.forward(request, response);
	}
}
