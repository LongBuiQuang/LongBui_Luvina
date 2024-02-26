/**
 * Copyright(C) 2020 Luvina JSC
 * AddUserConfirmController.java, 9/11/2020 Bui Quang Long
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
import manageuser.entities.MstJapanEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * class AddUserConfirmController để hiển thị màn hình ADM004 trường hợp Add
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.ADD_USER_CONFIRM_DO, Constant.ADD_USER_OK_DO })
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi
	 * AddUserInputController.java sendRedirect sang
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//			Lấy ra đối tượng session
			HttpSession session = request.getSession();
			if (session.getAttribute(Constant.KEY_FROM_ADM003_ADD) != null
					&& Constant.FROM_ADM003_ADD.equals(session.getAttribute(Constant.KEY_FROM_ADM003_ADD).toString())) {
//              Xóa giá trị "keyFromADM003Add" trên session. Tránh nhập link trực tiếp đến màn hình ADM004
				session.removeAttribute(Constant.KEY_FROM_ADM003_ADD);
//				Lấy keyUser từ request. Tránh bị ghi đè dữ liệu userInfoEntity
				String keyUser = request.getParameter(Constant.KEY_USER).toString();
//		        Tạo đối tượng userInfoEntity
				UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(keyUser);
//				Khởi tạo đối tượng
				MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
				int groupId = userInfoEntity.getGroupId();
//				Lấy ra mstGroupEntity
				MstGroupEntity mstGroupEntity = mstGroupLogicImpl.getMstGroupEntity(groupId);
				String groupName = mstGroupEntity.getGroupName();
				userInfoEntity.setGroupName(groupName);
//              Lấy ra codeLevel
				String codeLevel = userInfoEntity.getCodeLevel();
				if (!Constant.ZERO_STRING.equals(codeLevel)) {
//					Khởi tạo đối tượng
					MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
//					Lấy ra mstJapanEntity
					MstJapanEntity mstJapanEntity = mstJapanLogicImpl.getMstJapanEntity(codeLevel);
					String nameLevel = mstJapanEntity.getNameLevel();
					userInfoEntity.setNameLevel(nameLevel);
//	                Set lại total. Tránh nhập nhập total = "012" và hiển thị total = "012" ở ADM004. Sau khi xác nhận total hợp lệ (đã validate) 
					String total = Integer.parseInt(userInfoEntity.getTotal()) + "";
//					Set lại total vào userInfoEntity
					userInfoEntity.setTotal(total);
				}
//				Gán đối tượng userInfoEntity lên request
				request.setAttribute(Constant.USER_INFOR_ENTITY, userInfoEntity);
//				Gán giá trị keyUser lên request
				request.setAttribute(Constant.KEY_USER, keyUser);
//				Tạo đối tượng requestDispatcher để chuyển tiếp sang file ADM004.jsp
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM004);
//				Chuyển tiếp đối tượng request, response sang file ADM004.jsp
				requestDispatcher.forward(request, response);
			} else {
//				Tạo đối tượng requestDispatcher để chuyển tiếp sang file System error.jsp
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
			}
		} catch (Exception e) {
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
//          Ghi log
			System.out.println("AddUserConfirmController.java, doGet " + e.getMessage());
		}
	}

	/**
	 * phương thức doPost là phương thức được phương thức service() gọi người dùng
	 * ấn button OK
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//			Lấy ra đối tượng session
			HttpSession session = request.getSession();
//			Lấy keyUser
			String keyUser = request.getParameter(Constant.KEY_USER);
//			Lấy userInfoEntity từ session
			UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(keyUser);
//			Khởi tạo đối tượng tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//			Khởi tạo đối tượng validateUser
			ValidateUser validateUser = new ValidateUser();
//			Kiểm tra validate loginName và email trước khi insert
			List<String> listMessage = validateUser.validateBeforeInsert(userInfoEntity);
//			Kiểm tra listMessage
			if (listMessage.size() == Constant.ZERO) {
//			Gọi phương thức createUser()
				if (tblUserLogicImpl.createUser(userInfoEntity)) {
//				Xóa session chứa dữ liệu userInfoEntity, sau khi insert thành công
					session.removeAttribute(keyUser);
					response.sendRedirect(contextpath + Constant.SUCCESS + "?type=" + Constant.MSG001);
				}
			} else {
//              Nếu listMessage có phần tử
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
			}
		} catch (Exception e) {
//          Ghi log
			System.out.println("AddUserConfirmController.java, doPost " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}
}