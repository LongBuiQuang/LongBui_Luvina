/**
 * Copyright(C) 2020 Luvina JSC
 * EditUserConfirmController.java, 22/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;

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

/**
 * class EditUserInputController xử lý thông tin user tại ADM004
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.EDIT_USER_CONFIRM_DO, Constant.EDIT_USER_OK_DO })
public class EditUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi
	 * EditUserInputController sendRedirect sang
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//		    Lấy ra đối tượng session
			HttpSession session = request.getSession();
//			Nếu có key xác nhận từ ADM003 sang
			if (session.getAttribute(Constant.KEY_FROM_ADM003_EDIT) != null && Constant.FROM_ADM003_EDIT
					.equals(session.getAttribute(Constant.KEY_FROM_ADM003_EDIT).toString())) {
//              Xóa giá trị "keyFromADM003Edit" trên session. Tránh nhập link trực tiếp đến màn hình ADM004
				session.removeAttribute(Constant.KEY_FROM_ADM003_EDIT);
//			    Lấy keyUser từ request. Tránh bị ghi đè dữ liệu userInfoEntity
				String keyUser = request.getParameter(Constant.KEY_USER).toString();
//	            Tạo đối tượng userInfoEntity
				UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(keyUser);
//			    Khởi tạo đối tượng
				MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
				int groupId = userInfoEntity.getGroupId();
//			    Lấy ra mstGroupEntity
				MstGroupEntity mstGroupEntity = mstGroupLogicImpl.getMstGroupEntity(groupId);
				String groupName = mstGroupEntity.getGroupName();
				userInfoEntity.setGroupName(groupName);
//              Lấy ra codeLevel
				String codeLevel = userInfoEntity.getCodeLevel();
				if (!Constant.ZERO_STRING.equals(codeLevel)) {
//				    Khởi tạo đối tượng
					MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
//				    Lấy ra mstJapanEntity
					MstJapanEntity mstJapanEntity;
					mstJapanEntity = mstJapanLogicImpl.getMstJapanEntity(codeLevel);
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
			System.out.println("EditUserConfirmController.java, doGet " + e.getMessage());
		}
	}

	/**
	 * phương thức doPost là phương thức được phương thức service() gọi khi click
	 * button OK màn hình ADM004 trường hợp edit
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//		    Lấy ra đối tượng session
			HttpSession session = request.getSession();
//		    Lấy keyUser
			String keyUser = request.getParameter(Constant.KEY_USER);
//		    Lấy userInfoEntity từ session
			UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(keyUser);
//		    Khởi tạo đối tượng tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//          Lấy ra iDUserInfor 
			int iDUserInfor = userInfoEntity.getUserID();
//          Nếu tồn tại user thì mới thực hiện edit
			boolean checkUser = tblUserLogicImpl.checkTblUser(iDUserInfor);
//			Lấy email
			String email = userInfoEntity.getEmail();
//			checkEmail = true nếu có user (user có email = email và userID != userID)
			boolean checkEmail = tblUserLogicImpl.checkExitEmail(email, iDUserInfor);
//			Nếu tồn tại user và email không tồn tại trong Database (không tính email của user trong Database)
			if (checkUser && !checkEmail) {
//				Nếu updateUser thành công
				if (tblUserLogicImpl.updateUser(userInfoEntity)) {
//					Xóa session chứa dữ liệu userInfoEntity, sau khi updateUser thành công
					session.removeAttribute(keyUser);
					response.sendRedirect(contextpath + Constant.SUCCESS + "?type=" + Constant.MSG002);
				}
			} else {
//				Hiển thị màn hình System error với câu thông báo ER015
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
			}
		} catch (Exception e) {
			System.out.println("EditUserConfirmController.java, doPost " + e.getMessage());
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}

}