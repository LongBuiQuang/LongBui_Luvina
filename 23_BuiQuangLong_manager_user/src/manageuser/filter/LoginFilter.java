/**
 * Copyright(C) 2020 Luvina JSC
 * LoginFilter.java, 18/11/2020 Bui Quang Long
 */
package manageuser.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * class LoginFilter kiểm tra session còn tồn tại
 * 
 * @author Bui Quang Long
 */
public class LoginFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * doFilter để set UTF-8 cho req, và kiểm tra session khi người dùng gọi đến các
	 * controller
	 * 
	 * @param req  chứa dữ liệu được gửi về server
	 * @param resp chứa dữ liệu phản hồi gửi về client
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
//	    Tạo HttpServletRequest, HttpServletResponse
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String contextpath = request.getContextPath();
		try {
			HttpSession session = request.getSession();
//		    Khởi tạo common
			Common common = new Common();
//		    Lấy đường dẫn người dùng nhập
			String url = request.getRequestURI();
//          Nếu tồn tại session
			if (common.checkSession(session)) {
//				Kiểm tra url
				if (url.equals(contextpath + Constant.LOGIN_DO)
						|| url.equals(contextpath + "/" + Constant.PATH_ADM001)) {
					response.sendRedirect(contextpath + Constant.LIST_USER_DO);
				} else {
//					Cho qua filter
					chain.doFilter(request, response);
				}
			} else {
//				Nếu session không tồn tại
				if (url.equals(contextpath + Constant.LOGIN_DO)) {
//					Nếu người dùng nhập vào controller login.do
//					Cho qua filter
					chain.doFilter(request, response);
				} else {
//					Nếu người dùng không nhập vào controller login.do
					response.sendRedirect(contextpath + Constant.LOGIN_DO);
				}
			}
		} catch (Exception e) {
//			Ghi log
			System.out.println("LoginFilter.java, doFilter " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
