/**
 * Copyright(C) 2020 Luvina JSC
 * EditUserInputController.java, 21/12/2020 Bui Quang Long
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInfoEntity;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * class EditUserInputController xử lý thông tin tại ADM003
 * 
 * @author LA-PM Bui Quang Long
 *
 */
@WebServlet(urlPatterns = { Constant.EDIT_USER_INPUT_DO, Constant.CONFIRM_VALIDATE_DO })
public class EditUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * phương thức doGet là phương thức được phương thức service() gọi khi người
	 * dùng click vào button edit trên ADM005
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextpath = request.getContextPath();
		try {
//			Khởi tạo common
			Common common = new Common();
//			Lấy ra iDUserInfor 
			int iDUserInfor = common.convertStringToInt(request.getParameter(Constant.ID_USER_INFOR), Constant.ZERO);
//          Khởi tạo tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//          Khai báo biến check
			boolean check = tblUserLogicImpl.checkTblUser(iDUserInfor);
//			Kiểm tra tồn tại User
			if (check) {
//              Gọi hàm setDataLogic
				common.setDataLogic(request);
//		        Tạo đối tượng userInfoEntity
				UserInfoEntity userInfoEntity = getDefaultValue(request);
//				Set userInfoEntity lên request
				request.setAttribute(Constant.USER_INFOR_ENTITY, userInfoEntity);
//			    Tạo đối tượng requestDispatcher để chuyển tiếp sang file ADM003.jsp
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM003);
//			    Chuyển tiếp đối tượng request, response sang file ADM003.jsp
				requestDispatcher.forward(request, response);
			} else {
//				Tạo đối tượng requestDispatcher để chuyển tiếp sang file System error.jsp
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER013);
			}
		} catch (Exception e) {
//			Ghi log
			System.out.println("EditUserInputController.java, doGet " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}
	}

	/**
	 * phương thức doPost là phương thức được phương thức service() gọi khi người
	 * dùng click vào button submit trên màn hình ADM003 trường hợp edit
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
//		    Khởi tạo đối tượng common
			Common common = new Common();
//          Lấy ra iDUserInfor 
			int iDUserInfor = common.convertStringToInt(request.getParameter(Constant.ID_USER_INFOR), Constant.ZERO);
//		    Khởi tạo đối tượng tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//		    Kiểm tra user có tồn tại
			boolean check = tblUserLogicImpl.checkTblUser(iDUserInfor);
//			Nếu user tồn tại và iDUserInfor truyền về hợp lệ (là chuỗi số nguyên dương, khác "") 
			if (check == true && iDUserInfor != 0) {
//				Lấy giá trị cho userInfoEntity
				UserInfoEntity userInfoEntity = getDefaultValue(request);
//              Khởi tạo validateUser
				ValidateUser validateUser = new ValidateUser();
//				Gọi hàm validateUserInfor
				List<String> listMessage = validateUser.validateUserInfor(userInfoEntity);
//				Nếu nhập đúng
				if (listMessage.size() == Constant.ZERO) {
//					Gán key xác nhận từ ADM003 -> ADM004
					session.setAttribute(Constant.KEY_FROM_ADM003_EDIT, Constant.FROM_ADM003_EDIT);
//	                Lấy ra thời gian hiện tại
					long millis = common.creatSalt();
//					Gán giá trị userInfoEntity vào session với key là chuỗi giá trị millis
					session.setAttribute(millis + "", userInfoEntity);
//					Gọi sendRedirect("EditUserConfirm.do") và gán giá trị keyUser lên request
					response.sendRedirect("EditUserConfirm.do?" + Constant.KEY_USER + "=" + millis);
				} else {
//					Gọi hàm setDataLogic
					common.setDataLogic(request);
//					Set mảng listMessage lên request
					request.setAttribute(Constant.LIST_MESSAGE, listMessage);
//					set lại password và rePassword về ""
					userInfoEntity.setPassWord(Constant.EMPTY_STRING);
					userInfoEntity.setRePassWord(Constant.EMPTY_STRING);
//					Set userInfoEntity lên request
					request.setAttribute(Constant.USER_INFOR_ENTITY, userInfoEntity);
//	                Forword sang ADM003.jsp
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.PATH_ADM003);
					requestDispatcher.forward(request, response);
				}
			} else {
//				Tạo đối tượng requestDispatcher để chuyển tiếp sang file System error.jsp
				response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER013);
			}
		} catch (Exception e) {
			System.out.println("EditUserInputController.java, doPost " + e.getMessage());
//          Hiển thị màn hình lỗi
			response.sendRedirect(contextpath + Constant.ERROR + "?type=" + Constant.ER015);
		}

	}

	/**
	 * phương thức getDefaultValue là phương thức lấy ra đối tượng UserInfoEntity
	 * chứa các thông tin nhập tại màn hình ADM003 trường hợp Edit
	 * 
	 * @param request là đối tượng chứa thông tin yêu cầu
	 * @throws NumberFormatException, ClassNotFoundException,
	 */
	private UserInfoEntity getDefaultValue(HttpServletRequest request)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		String action = request.getParameter(Constant.ACTION);
//      Khai báo đối tượng userInfoEntity
		UserInfoEntity userInfoEntity = null;
//		Kiểm tra action == null 
		if (action == null) {
			action = Constant.DEFAULT;
		}
//	    Khởi tạo đối tượng common
		Common common = new Common();
		if (Constant.DEFAULT.equals(action)) {
//          Lấy ra iDUserInfor 
			int iDUserInfor = Integer.parseInt(request.getParameter(Constant.ID_USER_INFOR));
//		    Khởi tạo tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//		    Lấy giá trị cho userInfoEntity
			userInfoEntity = tblUserLogicImpl.getUserInforById(iDUserInfor);
//			Set iDUserInfor vào userInfoEntity
			userInfoEntity.setUserID(iDUserInfor);
//			Khi userInfoEntity không có trình độ tiếng nhật thì code_level = null, name_level = null, start_date = null, end_date = null
//			Set lại start_date = ngày hiện tại end_date = ngày hiện tại để hiển thị trên ADM003 start_date, end_date là ngày hiện tại
			if (userInfoEntity.getCodeLevel() == null) {
//				Lấy ra năm hiện tại
				int currentYear = common.getCurrentYear();
//				Lấy ra tháng hiện tại
				int currentMonth = common.getCurrentMonth();
//				Lấy ra ngày hiện tại
				int currentDay = common.getCurrentDay();
//				Lấy ra năm, sau năm hiện tại 
				int nextYear = currentYear + 1;
//	            Tạo chuỗi startDate			
				String startDate = currentYear + "-" + currentMonth + "-" + currentDay;
//	            Tạo chuỗi endDate					
				String endDate = nextYear + "-" + currentMonth + "-" + currentDay;
//				Set startDate vào userInfoEntity
				userInfoEntity.setStartDate(startDate);
//				Set endDate vào userInfoEntity
				userInfoEntity.setEndDate(endDate);
			}
		}
//      Trường hợp confirm
		if (Constant.CONFIRM.equals(action)) {
//	        Khởi tạo đối tượng userInfoEntity
			userInfoEntity = new UserInfoEntity();
//			Set giá trị các thuộc tính vào đối tượng userInfoEntity 
//          Set userID 
			userInfoEntity.setUserID(Integer.parseInt(request.getParameter(Constant.ID_USER_INFOR)));
//			Set loginName
			userInfoEntity.setLoginName(request.getParameter(Constant.LOGIN_NAME));
//			Set groupId			
			int groupId = Integer.parseInt(request.getParameter(Constant.GROUP_ID));
			userInfoEntity.setGroupId(groupId);
//          Set fullName
			userInfoEntity.setFullName(request.getParameter(Constant.FULLNAME));
//			Set fullNameKana
			userInfoEntity.setFullNameKana(request.getParameter(Constant.FULLNAME_KANA));
//		    Set birthday
			String yearBD = request.getParameter(Constant.YEAR_BD);
			String monthBD = request.getParameter(Constant.MONTH_BD);
			String dayBD = request.getParameter(Constant.DAY_BD);
			String birthday = yearBD + "-" + monthBD + "-" + dayBD;
			userInfoEntity.setBirthday(birthday);
//          Set email
			userInfoEntity.setEmail(request.getParameter(Constant.EMAIL));
//			Set tel
			userInfoEntity.setTel(request.getParameter(Constant.TEL));
//			Set password
			userInfoEntity.setPassWord(request.getParameter(Constant.PASSWORD));
//		    Set rePassword
			userInfoEntity.setRePassWord(request.getParameter(Constant.RE_PASSWORD));
//          Set codeLevel
			String codeLevel = request.getParameter(Constant.KYU_ID);
			userInfoEntity.setCodeLevel(codeLevel);
//			Set startDate
			String yearSD = request.getParameter(Constant.YEAR_SD);
			String monthSD = request.getParameter(Constant.MONTH_SD);
			String daySD = request.getParameter(Constant.DAY_SD);
			String startDate = yearSD + "-" + monthSD + "-" + daySD;
			userInfoEntity.setStartDate(startDate);
//			Set endDate
			String yearED = request.getParameter(Constant.YEAR_ED);
			String monthED = request.getParameter(Constant.MONTH_ED);
			String dayED = request.getParameter(Constant.DAY_ED);
			String endDate = yearED + "-" + monthED + "-" + dayED;
			userInfoEntity.setEndDate(endDate);
//			Set total
			userInfoEntity.setTotal(request.getParameter(Constant.TOTAL));
		}
//		Trường hợp back
		if (Constant.BACK.equals(action)) {
//		    Lấy ra đối tượng session
			HttpSession session = request.getSession();
//			Lấy ra keyUser được truyền về
			String keyUser = request.getParameter(Constant.KEY_USER);
//			Tạo đối tượng userInfoEntity
			userInfoEntity = (UserInfoEntity) session.getAttribute(keyUser);
//			Xóa session lưu đối tượng userInfoEntity
			session.removeAttribute(keyUser);
		}
		return userInfoEntity;
	}
}