/**
 * Copyright(C) 2020 Luvina JSC
 * ListUserController.java, 17/11/2020 Bui Quang Long
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

import manageuser.entities.MstGroupEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.MessageProperties;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * class ListUserController hiển thị danh sách user tại ADM002
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.LIST_USER_DO })
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi người
	 * dùng đăng nhập tài khoản thành công
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//			Sửa theo review ngày 31/12/2020
//			Lấy ra đối tượng session
			HttpSession session = request.getSession();
//			Khởi tạo đối tượng common
			Common common = new Common();
//			Lấy ra giá trị limit = 5
			int limit = common.getLimit();
//			Khởi tạo đối tượng tblUserLogic để gọi các phương thức getTotalUsers, getListUsers
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
//			Trường hợp default
			String fullName = Constant.EMPTY_STRING;
			int groupId = Constant.ZERO;
			int currentPage = Constant.ONE;
			String sortType = Constant.SORT_TYPE_FULL_NAME;
			String sortByFullName = Constant.ASC;
			String sortByCodeLevel = Constant.ASC;
			String sortByEndDate = Constant.DESC;
//			Lấy ra giá trị action
			String action = request.getParameter(Constant.ACTION);
//			Kiểm tra có phải là trường hợp default
			if (action == null) {
				action = Constant.DEFAULT;
			}
//			Tạo 5 case action
			switch (action) {
			case Constant.DEFAULT:
				break;
			case Constant.SEARCH:
				fullName = request.getParameter(Constant.FULL_NAME);
				groupId = common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.ZERO);
				break;
			case Constant.SORT:
				fullName = request.getParameter(Constant.FULL_NAME);
				groupId = common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.ZERO);
//				Lấy ra chỉ số trang hiện tại
				currentPage = common.convertStringToInt(session.getAttribute(Constant.CURRENT_PAGE).toString(),
						Constant.ONE);
				sortType = request.getParameter(Constant.SORT_TYPE);
//				Kiểm tra sortType có là full_name
				if (Constant.SORT_TYPE_FULL_NAME.equals(sortType)) {
//					Lấy ra trạng thái sortByFullName
					sortByFullName = request.getParameter(Constant.SORT_BY_FULLNAME);
//					Đảo trạng thái
					if (Constant.ASC.equals(sortByFullName)) {
						sortByFullName = Constant.DESC;
					} else {
						sortByFullName = Constant.ASC;
					}
				}
//				Kiểm tra sortType có là code_level
				if (Constant.SORT_TYPE_CODE_LEVEL.equals(sortType)) {
//					Lấy ra trạng thái sortByCodeLevel
					sortByCodeLevel = request.getParameter(Constant.SORT_BY_CODE_LEVEL);
//					Đảo trạng thái
					if (Constant.ASC.equals(sortByCodeLevel)) {
						sortByCodeLevel = Constant.DESC;
					} else {
						sortByCodeLevel = Constant.ASC;
					}
				}
//				Kiểm tra sortType có là end_date
				if (Constant.SORT_TYPE_END_DATE.equals(sortType)) {
//					Lấy ra trạng thái sortByEndDate
					sortByEndDate = request.getParameter(Constant.SORT_BY_END_DATE);
//					Đảo trạng thái
					if (Constant.ASC.equals(sortByEndDate)) {
						sortByEndDate = Constant.DESC;
					} else {
						sortByEndDate = Constant.ASC;
					}
				}
				break;
			case Constant.PAGING:
//				Lấy ra chỉ số trang hiện tại
				currentPage = common.convertStringToInt(request.getParameter(Constant.CURRENT_PAGE), Constant.ONE);
//				Lấy giá trị fullname
				fullName = request.getParameter(Constant.FULL_NAME);
//				Lấy giá trị groupId
				groupId = common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.ZERO);
//				Lấy giá trị sortType
				sortType = request.getParameter(Constant.SORT_TYPE);
//				Kiểm tra giá trị sortType. Chỉ lấy ra giá trị của sortBy... tương ứng với sortType. Hai sortBy... còn lại để mặc định
				if (Constant.SORT_TYPE_FULL_NAME.equals(sortType)) {
					sortByFullName = request.getParameter(Constant.SORT_BY_FULLNAME);
				}
				if (Constant.SORT_TYPE_CODE_LEVEL.equals(sortType)) {
					sortByCodeLevel = request.getParameter(Constant.SORT_BY_CODE_LEVEL);
				}
				if (Constant.SORT_TYPE_END_DATE.equals(sortType)) {
					sortByEndDate = request.getParameter(Constant.SORT_BY_END_DATE);
				}
				break;
			case Constant.BACK:
				currentPage = common.convertStringToInt(session.getAttribute(Constant.CURRENT_PAGE).toString(),
						Constant.ONE);
				fullName = session.getAttribute(Constant.FULL_NAME).toString();
				groupId = common.convertStringToInt(session.getAttribute(Constant.GROUP_ID).toString(), Constant.ZERO);
				sortType = session.getAttribute(Constant.SORT_TYPE).toString();
				sortByFullName = session.getAttribute(Constant.SORT_BY_FULLNAME).toString();
				sortByCodeLevel = session.getAttribute(Constant.SORT_BY_CODE_LEVEL).toString();
				sortByEndDate = session.getAttribute(Constant.SORT_BY_END_DATE).toString();
				break;
			}
			int totalUser = tblUserLogic.getTotalUsers(groupId, fullName);
			int totalPage = common.getTotalPage(totalUser, limit);
//			Nếu currentPage > totalPage thì currentPage = totalPage
			if (currentPage > totalPage) {
				currentPage = totalPage;
			}
//			Nếu tổng số bản ghi lớn hơn 5 thì mới phân trang
			if (totalUser > limit) {
				List<Integer> listPaging = common.getListPaging(totalUser, limit, currentPage);
				request.setAttribute(Constant.TOTAL_PAGE, totalPage);
				request.setAttribute(Constant.LIST_PAGING, listPaging);
				request.setAttribute(Constant.CURRENT_PAGE, currentPage);
			}
			if (totalUser > Constant.ZERO) {
				int offset = common.getOffset(currentPage, limit);
				List<UserInfoEntity> listUserInfoEntity = tblUserLogic.getListUsers(offset, limit, groupId, fullName,
						sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
				request.setAttribute(Constant.LIST_USER_INFOR_ENTITY, listUserInfoEntity);
			} else {
				String message = MessageProperties.getValueByKey(Constant.MSG005_CANNOT_FIND_USER);
				request.setAttribute(Constant.MESSAGE, message);
			}
//		    Khởi tạo đối tượng mstGroupLogicImpl
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
//			Lấy ra danh sách group trong listMstGroupEntity
			List<MstGroupEntity> listMstGroupEntity = mstGroupLogicImpl.getAllMstGroup();
			request.setAttribute(Constant.LIST_MST_GROUP_ENTITY, listMstGroupEntity);
			request.setAttribute(Constant.FULL_NAME, fullName);
			request.setAttribute(Constant.GROUP_ID, groupId);
			request.setAttribute(Constant.SORT_TYPE, sortType);
			request.setAttribute(Constant.SORT_BY_FULLNAME, sortByFullName);
			request.setAttribute(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
			request.setAttribute(Constant.SORT_BY_END_DATE, sortByEndDate);

			session.setAttribute(Constant.FULL_NAME, fullName);
			session.setAttribute(Constant.GROUP_ID, groupId);
			session.setAttribute(Constant.CURRENT_PAGE, currentPage);
			session.setAttribute(Constant.SORT_TYPE, sortType);
			session.setAttribute(Constant.SORT_BY_FULLNAME, sortByFullName);
			session.setAttribute(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
			session.setAttribute(Constant.SORT_BY_END_DATE, sortByEndDate);

//			lấy đường dẫn đến file ADM002.jsp
			String pathADM002 = Constant.PATH_ADM002;
//			Tạo đối tượng RequestDispatcher
			RequestDispatcher rd = request.getRequestDispatcher(pathADM002);
//			Thực hiện forward request, response sang file ADM002.jsp
			rd.forward(request, response);
		} catch (Exception e) {
//			Ghi log
			System.out.println("File ListUserController.java, doGet " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}
}