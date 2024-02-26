/**
 * Copyright(C) 2020 Luvina JSC
 * ValidateUser.java, 17/11/2020 Bui Quang Long
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.entities.MstGroupEntity;
import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.MessageErrorProperties;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * class ValidateUser có chức năng validate dữ liệu nhập tại ADM003
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class ValidateUser {

	/**
	 * phương thức validateLogin là phương thức để lấy ra chuỗi thông báo khi người
	 * dùng ấn submit form
	 * 
	 * @param username là username người dùng nhập
	 * @param password là password người dùng nhập
	 * @return listMessage mảng chứa các chuỗi thông báo
	 */
	public List<String> validateLogin(String username, String password)
			throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
//      Tạo một mảng listMessage 
		List<String> listMessage = new ArrayList<>();
//		Nếu người dùng không nhập username
		if (Constant.EMPTY_STRING.equals(username)) {
//			Lưu chuỗi thông báo lỗi người dùng không nhập username vào biến usernameEmpty
			String usernameEmpty = MessageErrorProperties.getValueByKey(Constant.ER001_USERNAME_EMPTY);
//			Thêm thông báo vào mảng listMessage
			listMessage.add(usernameEmpty);
		}
//		Nếu người dùng không nhập password 
		if (Constant.EMPTY_STRING.equals(password)) {
//			Lưu chuỗi thông báo lỗi người dùng không nhập password vào biến passwordEmpty
			String passwordEmpty = MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD_EMPTY);
//			Thêm thông báo vào mảng listMessage
			listMessage.add(passwordEmpty);
		}
//		Nếu người dùng nhập đủ username và password
		if ((!Constant.EMPTY_STRING.equals(username)) && (!Constant.EMPTY_STRING.equals(password))) {
//			Khởi tạo đối tượng tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//			Gọi phương thức checkExistLogin để kiểm tra người dùng nhập đúng tài khoản không
			boolean checkInvalid = tblUserLogicImpl.checkExistLogin(username, password);
//			Nếu người dùng nhập sai tài khoản
			if (!checkInvalid) {
				String usernameOrPasswordWrong = MessageErrorProperties.getValueByKey(Constant.ER016_WRONG_INFORMATION);
				listMessage.add(usernameOrPasswordWrong);
			}
		}
		return listMessage;
	}

	/**
	 * phương thức validateUserInfor là phương thức để kiểm tra tính hợp lệ của dữ
	 * liệu người dùng nhập tại màn hình ADM003
	 * 
	 * @param userInfoEntity là đối tượng chứa thông tin người dùng nhập tại màn
	 *                       hình ADM003
	 * @return listMessage mảng chứa các chuỗi thông báo
	 * @throws ClassNotFoundException, SQLException
	 */
	public List<String> validateUserInfor(UserInfoEntity userInfoEntity) throws ClassNotFoundException, SQLException {
//      Khởi tạo mảng listMessage để lưu các thông báo lỗi
		List<String> listMessage = new ArrayList<>();
		try {
//		Lấy giá trị các thuộc tính từ userInfoEntity
			int userID = userInfoEntity.getUserID();
			String loginName = userInfoEntity.getLoginName();
			int groupId = userInfoEntity.getGroupId();
			String fullName = userInfoEntity.getFullName();
			String fullNameKana = userInfoEntity.getFullNameKana();
			String birthday = userInfoEntity.getBirthday();
			String email = userInfoEntity.getEmail();
			String tel = userInfoEntity.getTel();
			String password = userInfoEntity.getPassWord();
			String rePassword = userInfoEntity.getRePassWord();
			String codeLevel = userInfoEntity.getCodeLevel();

//			Chỉ validateLoginName trường hợp Add
			if (userID == 0) {
//          Kiểm tra validateLoginName
				String messageLoginName = validateLoginName(loginName);
				if (!Constant.EMPTY_STRING.equals(messageLoginName)) {
					listMessage.add(messageLoginName);
				}
			}
//	        Kiểm tra validateGroupName
			String messageGroupName = validateGroupName(groupId);
			if (!Constant.EMPTY_STRING.equals(messageGroupName)) {
				listMessage.add(messageGroupName);
			}
//			Kiểm tra validateFullName
			String messageFullName = validateFullName(fullName);
			if (!Constant.EMPTY_STRING.equals(messageFullName)) {
				listMessage.add(messageFullName);
			}
//			Kiểm tra validateFullNameKana
			String messageFullNameKana = validateFullNameKana(fullNameKana);
			if (!Constant.EMPTY_STRING.equals(messageFullNameKana)) {
				listMessage.add(messageFullNameKana);
			}
//			Kiểm tra validateBirthday
			String messageBirthday = validateBirthday(birthday);
			if (!Constant.EMPTY_STRING.equals(messageBirthday)) {
				listMessage.add(messageBirthday);
			}
//			Kiểm tra validateEmail
			String messageEmail = validateEmail(email, userID);
			if (!Constant.EMPTY_STRING.equals(messageEmail)) {
				listMessage.add(messageEmail);
			}
//			Kiểm tra validateTel
			String messageTel = validateTel(tel);
			if (!Constant.EMPTY_STRING.equals(messageTel)) {
				listMessage.add(messageTel);
			}
//			Chỉ validatePassword, validateRePassword trường hợp Add
			if (userID == Constant.ZERO) {
//			    Kiểm tra validatePassword
				String messagePassword = validatePassword(password);
				if (!Constant.EMPTY_STRING.equals(messagePassword)) {
					listMessage.add(messagePassword);
				}
//			    Kiểm tra validateRePassword
				String messageRepassword = validateRePassword(password, rePassword);
				if (!Constant.EMPTY_STRING.equals(messageRepassword)) {
					listMessage.add(messageRepassword);
				}
			}
//			Nếu đã chọn trình độ tiếng Nhật thì validate vùng trình độ tiếng Nhật
			if (!Constant.ZERO_STRING.equals(codeLevel)) {
				String startDate = userInfoEntity.getStartDate();
				String endDate = userInfoEntity.getEndDate();
				String total = userInfoEntity.getTotal();
//			    Kiểm tra validateCodeLevel
				String messageCodeLevel = validateCodeLevel(codeLevel);
				if (!Constant.EMPTY_STRING.equals(messageCodeLevel)) {
					listMessage.add(messageCodeLevel);
				}
//			    Kiểm tra validateStartDate
				String messageStartDate = validateStartDate(startDate);
				if (!Constant.EMPTY_STRING.equals(messageStartDate)) {
					listMessage.add(messageStartDate);
				}
//			    Kiểm tra validateEndDate
				String messageEndDate = validateEndDate(endDate, startDate);
				if (!Constant.EMPTY_STRING.equals(messageEndDate)) {
					listMessage.add(messageEndDate);
				}
//			    Kiểm tra validateTotal
				String messageTotal = validateTotal(total);
				if (!Constant.EMPTY_STRING.equals(messageTotal)) {
					listMessage.add(messageTotal);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
//			Ghi log
			System.out.println("ValidateUser.java, validateUserInfor " + e.getMessage());
			throw e;
		}
		return listMessage;
	}

	/**
	 * phương thức validateLoginName là phương thức kiểm tra tính hợp lệ của
	 * loginName
	 * 
	 * @param loginName là tên tài khoản người dùng nhập
	 * @return message là thông báo lỗi
	 */
	public String validateLoginName(String loginName) throws ClassNotFoundException, SQLException {
		String message = Constant.EMPTY_STRING;
		try {
//		    Khởi tạo đối tượng common
			Common common = new Common();
//          Nếu loginName là chuỗi rỗng
			if (Constant.EMPTY_STRING.equals(loginName)) {
				message = MessageErrorProperties.getValueByKey(Constant.ER001_LOGINNAME);
			} else if (!common.checkFormatLoginName(loginName)) {
//			    Nếu người dùng nhập sai format	
				message = MessageErrorProperties.getValueByKey(Constant.ER019_LOGINNAME);
			} else if (loginName.length() < 4 || loginName.length() > 15) {
//              Nếu người dùng nhập sai sô lượng kí tự
				message = MessageErrorProperties.getValueByKey(Constant.ER007_LOGINNAME);
			} else {
//              Khởi tạo đối tượng
				TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//				Khai báo và lấy giá trị cho mảng listTblUserEntity
				List<TblUserEntity> listTblUserEntity = tblUserLogicImpl.getAllUserByLoginName(loginName);
//              Nếu đã tồn tại tài khoản trùng với loginName
				if (listTblUserEntity.size() != Constant.ZERO) {
					message = MessageErrorProperties.getValueByKey(Constant.ER003_LOGINNAME);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.java, validateLoginName " + e.getMessage());
			throw e;
		}
		return message;
	}

	/**
	 * phương thức validateGroupName là phương thức kiểm tra tính hợp lệ của
	 * groupName
	 * 
	 * @param groupId là mã nhóm
	 * @return message là thông báo lỗi
	 */
	public String validateGroupName(int groupId) throws ClassNotFoundException, SQLException {
		String message = Constant.EMPTY_STRING;
		try {
//		    Nếu người dùng không chọn 
			if (groupId == Constant.ZERO) {
				message = MessageErrorProperties.getValueByKey(Constant.ER002_GROUPNAME);
			} else {
//              Khởi tạo đối tượng mstGroupLogicImpl
				MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
//			    Khai báo và lấy giá trị cho
				MstGroupEntity mstGroupEntity = mstGroupLogicImpl.getMstGroupEntity(groupId);
//				Kiểm tra mstGroupEntity không tồn tại
				if (mstGroupEntity == null) {
					message = MessageErrorProperties.getValueByKey(Constant.ER004_GROUPNAME);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.java, validateGroupName " + e.getMessage());
			throw e;
		}
		return message;
	}

	/**
	 * phương thức validateFullName là phương thức kiểm tra tính hợp lệ của FullName
	 * 
	 * @param fullName là tên người dùng
	 * @return message là thông báo lỗi
	 */
	public String validateFullName(String fullName) {
		String message = Constant.EMPTY_STRING;
		if (Constant.EMPTY_STRING.equals(fullName)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER001_FULLNAME);
		} else if (fullName.length() > 255) {
			message = MessageErrorProperties.getValueByKey(Constant.ER006_FULLNAME);
		}
		return message;
	}

	/**
	 * phương thức validateFullNameKana là phương thức kiểm tra tính hợp lệ của
	 * fullNameKana
	 * 
	 * @param fullNameKana là tên katakana người dùng
	 * @return message là thông báo lỗi
	 */
	public String validateFullNameKana(String fullNameKana) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
//		Khi người dùng nhập kí tự thì mới validate fullNameKana. Nếu không nhập thì message = "", không validate
		if (!Constant.EMPTY_STRING.equals(fullNameKana)) {
//		Nếu fullNameKana chứa kí tự không là chữ katakana
			if (!common.checkKana(fullNameKana)) {
				message = MessageErrorProperties.getValueByKey(Constant.ER009_FULLNAMEKANA);
			} else if (fullNameKana.length() > 255) {
				message = MessageErrorProperties.getValueByKey(Constant.ER006_FULLNAMEKANA);
			}
		}
		return message;
	}

	/**
	 * phương thức validateBirthday là phương thức kiểm tra tính hợp lệ của birthday
	 * 
	 * @param birthday là ngày sinh người dùng nhập
	 * @return message là thông báo lỗi
	 */
	public String validateBirthday(String birthday) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
//		Nếu birthday không hợp lệ
		if (!common.checkDate(birthday)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER011_BIRTHDAY);
		}
		return message;
	}

	/**
	 * phương thức validateEmail là phương thức kiểm tra tính hợp lệ của email
	 * 
	 * @param email là địa chỉ thư
	 * @return message là thông báo lỗi
	 * @throws ClassNotFoundException, SQLException
	 */
	public String validateEmail(String email, int userID) throws ClassNotFoundException, SQLException {
		String message = Constant.EMPTY_STRING;
		try {
//		    Khởi tạo đối tượng common
			Common common = new Common();
//		    Nếu không nhập email
			if (Constant.EMPTY_STRING.equals(email)) {
				message = MessageErrorProperties.getValueByKey(Constant.ER001_EMAIL);
			} else if (email.length() > 100) {
				message = MessageErrorProperties.getValueByKey(Constant.ER006_EMAIL);
			} else if (!common.checkFormatEmail(email)) {
				message = MessageErrorProperties.getValueByKey(Constant.ER005_EMAIL);
			} else {
//              Khởi tạo đối tượng
				TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
				boolean checkEmail = tblUserLogicImpl.checkExitEmail(email, userID);
				if (checkEmail) {
					message = MessageErrorProperties.getValueByKey(Constant.ER003_EMAIL);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.java, validateEmail " + e.getMessage());
			throw e;
		}
		return message;
	}

	/**
	 * phương thức validateTel là phương thức kiểm tra tính hợp lệ của tel
	 * 
	 * @param tel là số điện thoại
	 * @return message là thông báo lỗi
	 */
	public String validateTel(String tel) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
//		Nếu người dùng không nhập số điện thoại
		if (Constant.EMPTY_STRING.equals(tel)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER001_TEL);
		} else if (tel.length() > 14 || tel.length() < 5) {
//			Nếu độ dài tel > 14
			message = MessageErrorProperties.getValueByKey(Constant.ER006_TEL);
		} else if (!common.checkFormatTel(tel)) {
//			Nếu chuỗi tel sai format 
			message = MessageErrorProperties.getValueByKey(Constant.ER005_TEL);
		}
		return message;
	}

	/**
	 * phương thức validatePassword là phương thức kiểm tra tính hợp lệ của password
	 * 
	 * @param password là mật khẩu
	 * @return message là thông báo lỗi
	 */
	public String validatePassword(String password) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
		if (Constant.EMPTY_STRING.equals(password)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD);
		} else if (!common.checkOneBytePassword(password)) {
//        Nếu password không thỏa mãn gồm các kí tự 1 byte
			message = MessageErrorProperties.getValueByKey(Constant.ER008_PASSWORD);
		} else if (password.length() < 5 || password.length() > 15) {
//        Nếu password không có độ dài trong khoảng 5-15 
			message = MessageErrorProperties.getValueByKey(Constant.ER007_PASSWORD);
		}
		return message;
	}

	/**
	 * phương thức validateRePassword là phương thức kiểm tra tính hợp lệ của
	 * rePassword
	 * 
	 * @param password   là mật khẩu
	 * @param rePassword là mật khẩu nhập lại
	 * @return message là thông báo lỗi
	 */
	public String validateRePassword(String password, String rePassword) {
		String message = Constant.EMPTY_STRING;
//		Nếu password khác rePassword
		if (!password.equals(rePassword)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER017_REPASSWORD);
		}
		return message;
	}

	/**
	 * phương thức validateCodeLevel là phương thức kiểm tra tính hợp lệ của
	 * codeLevel
	 * 
	 * @param codeLevel là mã trình độ tiếng Nhật
	 * @return message là thông báo lỗi
	 * @throws ClassNotFoundException, SQLException
	 */
	public String validateCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		String message = Constant.EMPTY_STRING;
		try {
//		    Khởi tạo đối tượng
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
//			Nếu không có bản ghi trong database
			if (mstJapanLogicImpl.getMstJapanEntity(codeLevel) == null) {
				message = MessageErrorProperties.getValueByKey(Constant.ER004_CODELEVEL);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.java, validateCodeLevel " + e.getMessage());
			throw e;
		}
		return message;
	}

	/**
	 * phương thức validateStartDate là phương thức kiểm tra tính hợp lệ của
	 * startDate
	 * 
	 * @param startDate là ngày bắt đầu
	 * @return message là thông báo lỗi
	 */
	public String validateStartDate(String startDate) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
		if (!common.checkDate(startDate)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER011_STARTDATE);
		}
		return message;
	}

	/**
	 * phương thức validateEndDate là phương thức kiểm tra tính hợp lệ của endDate
	 * 
	 * @param endDate   là ngày kết thúc
	 * @param startDate là ngày bắt đầu
	 * @return message là thông báo lỗi
	 */
	public String validateEndDate(String endDate, String startDate) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
		if (!common.checkDate(endDate)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER011_ENDDATE);
		} else if (!checkCompareDate(endDate, startDate)) {
//			Nếu ngày cấp chứng chỉ lớn hơn ngày hết hạn
			message = MessageErrorProperties.getValueByKey(Constant.ER012_ENDDATE);
		}
		return message;
	}

	/**
	 * phương thức checkCompareDate là phương thức kiểm tra endDate có sau startDate
	 * 
	 * @param endDate   là ngày kết thúc
	 * @param startDate là ngày bắt đầu
	 * @return true nếu endDate sau startDate (người dùng nhập đúng)
	 */
	public boolean checkCompareDate(String endDate, String startDate) {
//		khởi tạo biến kiểm tra
		boolean checkDate = false;
//		Cắt chuỗi endDate 
		String[] DateEnd = endDate.split("-");
//		Cắt chuỗi startDate 
		String[] DateStart = startDate.split("-");
//		Kiểm tra năm của endDate > năm của startDate
		if (Integer.parseInt(DateEnd[0]) > Integer.parseInt(DateStart[0])) {
			checkDate = true;
		} else if (Integer.parseInt(DateEnd[0]) == Integer.parseInt(DateStart[0])) {
//			Kiểm tra năm của endDate = năm của startDate	
//			Kiểm tra tháng của endDate > tháng của startDate	
			if (Integer.parseInt(DateEnd[1]) > Integer.parseInt(DateStart[1])) {
				checkDate = true;
			} else if (Integer.parseInt(DateEnd[1]) == Integer.parseInt(DateStart[1])) {
//				Kiểm tra tháng của endDate = tháng của startDate	
//				Kiểm tra ngày của endDate > ngày của startDate
				if (Integer.parseInt(DateEnd[2]) > Integer.parseInt(DateStart[2])) {
					checkDate = true;
				}
			}
		}
		return checkDate;
	}

	/**
	 * phương thức validateTotal là phương thức kiểm tra tính hợp lệ của total
	 * 
	 * @param total là điểm
	 * @return message là thông báo lỗi
	 */
	public String validateTotal(String total) {
		String message = Constant.EMPTY_STRING;
//		Khởi tạo đối tượng common
		Common common = new Common();
		if (Constant.EMPTY_STRING.equals(total)) {
			message = MessageErrorProperties.getValueByKey(Constant.ER001_TOTAL);
		} else if (!common.checkHalfSize(total)) {
//		Nếu không là số halfsize
			message = MessageErrorProperties.getValueByKey(Constant.ER018_TOTAL);
		} else if (Integer.parseInt(total) < 1 || Integer.parseInt(total) > 180) {
			message = MessageErrorProperties.getValueByKey(Constant.ER021_TOTAL);
		}
		return message;
	}

	/**
	 * phương thức validateBeforeInsert là phương thức validate loginame và email
	 * của user tại ADM004 trước khi insert
	 * 
	 * @param userInfoEntity là đối tượng chứa thông tin của user thêm mới
	 * @return listMessage là mảng chứa thông báo lỗi
	 * @throws ClassNotFoundException, SQLExceptio
	 */
	public List<String> validateBeforeInsert(UserInfoEntity userInfoEntity)
			throws ClassNotFoundException, SQLException {
		List<String> listMessage = new ArrayList<>();
		try {
//		    Lấy ra loginName và email từ userInfoEntity
			String loginName = userInfoEntity.getLoginName();
			String email = userInfoEntity.getEmail();
			int userID = userInfoEntity.getUserID();
//          Kiểm tra validateLoginName
			String messageLoginName;
			messageLoginName = validateLoginName(loginName);
			if (!Constant.EMPTY_STRING.equals(messageLoginName)) {
				listMessage.add(messageLoginName);
			}
//			Kiểm tra validateEmail // 
			String messageEmail = validateEmail(email, userID);
			if (!Constant.EMPTY_STRING.equals(messageEmail)) {
				listMessage.add(messageEmail);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.java, validateBeforeInsert " + e.getMessage());
			throw e;
		}
		return listMessage;
	}
}