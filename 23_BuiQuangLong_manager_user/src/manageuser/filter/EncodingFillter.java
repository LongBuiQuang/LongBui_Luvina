/**
 * Copyright(C) 2020 Luvina JSC
 * EncodingFillter.java, 28/12/2020 Bui Quang Long
 */
package manageuser.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import manageuser.utils.Constant;

/**
 * class EncodingFillter Set UTF-8 cho request
 * 
 * @author Bui Quang Long
 */
public class EncodingFillter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * doFilter để set UTF-8 cho request
	 * 
	 * @param req  chứa dữ liệu được gửi về server
	 * @param resp chứa dữ liệu phản hồi gửi về client
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//	    set UTF-8 cho req
		request.setCharacterEncoding(Constant.UTF_8);
//		Cho qua filter
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}