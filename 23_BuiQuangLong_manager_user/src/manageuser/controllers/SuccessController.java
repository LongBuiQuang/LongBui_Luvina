/**
 * Copyright(C) 2020 Luvina JSC
 * SuccessController.java, 15/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.properties.MessageProperties;
import manageuser.utils.Constant;

/**
 * class SuccessController xử lý câu thông báo và hiển thị màn hình ADM006
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.SUCCESS_DO })
public class SuccessController extends HttpServlet {
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
		if (Constant.MSG001.equals(type)) {
			String message = MessageProperties.getValueByKey(Constant.MSG001);
			request.setAttribute(Constant.MESSAGE, message);
		} else if (Constant.MSG002.equals(type)) {
			String message = MessageProperties.getValueByKey(Constant.MSG002);
			request.setAttribute(Constant.MESSAGE, message);
		} else if (Constant.MSG003.equals(type)) {
			String message = MessageProperties.getValueByKey(Constant.MSG003);
			request.setAttribute(Constant.MESSAGE, message);
		}
//		Forward sang ADM006
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM006);
		requestDispatcher.forward(request, response);
	}
}
