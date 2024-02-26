/**
 * Copyright(C) 2020 Luvina JSC
 * Common.java, 17/11/2020 Bui Quang Long
 */
package manageuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroupEntity;
import manageuser.entities.MstJapanEntity;
import manageuser.entities.TblDetailUserJapanEntity;
import manageuser.entities.TblUserEntity;
import manageuser.entities.UserInfoEntity;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.ConfigProperties;

/**
 *
 * class Common chứa các phương thức chung
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class Common {
	/**
	 * phương thức encryptPassword là phương thức khai báo để mã hóa password người
	 * dùng nhập và salt lấy từ Database Database
	 * 
	 * @param password chính là password người dùng nhập vào
	 * @param salt     là dữ liệu lấy từ Database
	 * @return trả về chuỗi đã được mã hóa
	 */

	public String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
//		Khởi tạo chuỗi sha1 bằng rỗng
		String sha1 = Constant.EMPTY_STRING;
		try {
//			Tạo ra chuỗi value
			String value = password + salt;
//			Khởi tạo đối tượng MessageDigest có kiểu mã hóa là SHA-1
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(value.getBytes());
			// byte[] to base64 string
			sha1 = Base64.getEncoder().encodeToString(digest.digest());
		} catch (Exception e) {
			System.out.println("Lỗi mã hóa");
			throw e;
		}
		return sha1;
	}

	/**
	 * phương thức compareString là phương thức khai báo để so khới password người
	 * dùng nhập vào và password lấy ra trong Database
	 * 
	 * @param password1 là password người dùng nhập vào
	 * @param password2 là password lấy từ trong Database
	 * @return trả về true nếu hai chuỗi giống nhau, ngược lại trả về false
	 */
	public boolean compareString(String password1, String password2) {
//		Biến check để kiểm tra xem 2 chuỗi có bằng nhau
		boolean check = false;
//		Nếu chuỗi password1 bằng chuỗi password2
		if (password1.equals(password2)) {
//			Gán giá trị cho check
			check = true;
		}
//		Trả về kết quả so sánh
		return check;
	}

	/**
	 * phương thức getListPaging là phương thức lấy chuỗi paging cần hiển thị
	 * 
	 * @param totalUser   là tổng số bản ghi được lấy ra
	 * @param limit       là số bản ghi tối đa
	 * @param currentPage là chỉ số page hiện tại
	 * @return chuỗi paging cần hiển thị
	 */
	public List<Integer> getListPaging(int totalUser, int limit, int currentPage) {
		List<Integer> listPaging = new ArrayList<>();
//      Tính tổng số trang
		int totalPage;
//		Nếu totalUser chia hết cho limit
		if (totalUser % limit == Constant.ZERO) {
//		    Tính giá trị tổng số trang và lưu vào totalPage
			totalPage = totalUser / limit;
		} else {
//			Nếu totalUser không chia hết cho limit
//			Tính giá trị tổng số trang và lưu vào totalPage
			totalPage = totalUser / limit + 1;
		}
//		Nếu tổng số trang lớn hơn giá trị trang hiện tại
		if (totalPage >= currentPage) {
//		    Tính chỉ số bắt đầu của listPaging
			int begin;
//		    Vì listPaging là 3 số liên tiếp 
//		    numberPageLimit = 3
			int numberPageLimit = Integer.parseInt(ConfigProperties.getValueByKey(Constant.NUMBER_PAGE_LIMIT));
//		    Nếu currentPage chia hết cho 3
			if (currentPage % numberPageLimit == Constant.ZERO) {
				begin = currentPage - (numberPageLimit - 1);
			} else {
//			    Nếu currentPage không chia hết cho 3
				begin = (currentPage / numberPageLimit) * numberPageLimit + 1;
			}
//		    Duyệt vòng for để lưu vào mảng listPaging
			for (int i = begin; i <= begin + (numberPageLimit - 1); i++) {
//			    Kiểm tra i <= totalPage
				if (i <= totalPage) {
//				    Thêm chỉ số vào listPaging
					listPaging.add(i);
				}
			}
		}
//		Trả về listPaging
		return listPaging;
	}

	/**
	 * phương thức getOffset là phương thức tính giá trị offset
	 * 
	 * @param currentPage là chỉ số trang hiện tại
	 * @param limit       là số bản ghi tối đa
	 * @return vị trí bản ghi muốn lấy trong database
	 */
	public int getOffset(int currentPage, int limit) {
//		Khai báo biến offset
		int offset;
//		Tính giá trị offset và lưu vào biến x
		offset = limit * currentPage - limit;
//		Trả về kết quả
		return offset;
	}

	/**
	 * phương thức getLimit là phương thức lấy ra giá trị limit
	 * 
	 * @return giá trị số bản ghi tối đa
	 */
	public int getLimit() {
//		Khai báo biến limit
		int limit = Constant.ZERO;
		try {
//			Lấy ra số lượng bản ghi tối đa hiển thị tại 1 trang
			limit = Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT));
		} catch (Exception e) {
			System.out.println("Common.java, getLimit " + e.getMessage());
		}
		return limit;
	}

	/**
	 * phương thức getTotalPage là phương thức tính tổng số page
	 * 
	 * @param totalUser là tổng số bản ghi được lấy ra
	 * @param limit     là số bản ghi tối đa
	 * @return tổng số page
	 */
	public int getTotalPage(int totalUser, int limit) {
//		Khai báo biến totalPage lưu tổng số page
		int totalPage;
//		Nếu totalUser chia hết cho limit
		if (totalUser % limit == Constant.ZERO) {
//		    Tính giá trị tổng số trang và lưu vào totalPage
			totalPage = totalUser / limit;
		} else {
//			Nếu totalUser không chia hết cho limit
//			Tính giá trị tổng số trang và lưu vào totalPage
			totalPage = totalUser / limit + 1;
		}
//		Trả về tổng số trang
		return totalPage;
	}

	/**
	 * phương thức checkSession là phương thức kiểm tra admin đã đăng nhập chưa
	 * 
	 * @param session là đối tượng lấy ra từ request
	 * @return true nếu admin đã đăng nhập
	 * @throws ClassNotFoundException, SQLException
	 */
	public boolean checkSession(HttpSession session) throws ClassNotFoundException, SQLException {
//		Khai báo biến check và check = false
		boolean check = false;
		try {
//		    Khởi tạo đối tượng tblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
//			Lấy tài khoản admin nhập 
			String loginName = (String) session.getAttribute(Constant.LOGIN_NAME);
//		    Nếu session khác null và tài khoản còn tồn tại trong Database
			if (loginName != null && tblUserLogicImpl.getUserByLoginName(loginName) != null) {
//			    Gán check = true
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
//			Ghi log
			System.out.println("Common.java, checkSession " + e.getMessage());
			throw e;
		}
//		Trả về kết quả kiểm tra
		return check;
	}

	/**
	 * phương thức encodingXSS là phương thức để chuyển các kí tự " & ' / < > thành
	 * các mã kí tự. Các kí tự còn lại giữ nguyên
	 * 
	 * @param value là chuỗi cần chuyển
	 * @return chuỗi đã chuyển
	 */
	public String encodingXSS(String value) {
		// thay đổi tất cả chuỗi con "&" trong chuỗi value bằng chuỗi "&amp;"
		value = value.replaceAll("&", "&amp;");
		// thay đổi tất cả chuỗi con "'" trong chuỗi value bằng chuỗi "&#x27;"
		value = value.replaceAll("'", "&#x27;");
		// thay đổi tất cả chuỗi con "\"" trong chuỗi value bằng chuỗi "&quot;"
		value = value.replaceAll("\"", "&quot;");
		// thay đổi tất cả chuỗi con "/" trong chuỗi value bằng chuỗi "&#x2F;"
		value = value.replaceAll("/", "&#x2F;");
		// thay đổi tất cả chuỗi con "<" trong chuỗi value bằng chuỗi "&lt;"
		value = value.replaceAll("<", "&lt;");
		// thay đổi tất cả chuỗi con ">" trong chuỗi value bằng chuỗi "&gt;"
		value = value.replaceAll(">", "&gt;");
		return value;
	}

	/**
	 * phương thức wildCard là phương thức thay thế kí tự đặc biệt để thao tác với
	 * Database
	 * 
	 * @param value là chuỗi String
	 * @return chuỗi đã chuyển
	 */
	public String replaceWildCard(String value) {
		value = value.replace("\\", "\\\\");
		value = value.replace("_", "\\_");
		value = value.replace("%", "\\%");
		return value;
	}

	/**
	 * phương thức convertStringToInt là phương thức chuyển chuỗi sang định dạng số
	 * nguyên
	 * 
	 * @param sortType là tên trường muốn sort
	 * @return true nếu trường muốn sort tồn tại
	 */
	public int convertStringToInt(String value, int numberDefault) {
//		Khởi tạo biến check
		boolean check = true;
		int result = numberDefault;
//		Khi value khác rỗng thì mới duyệt vòng for
		if (!Constant.EMPTY_STRING.equals(value)) {
//		    Kiểm tra value có phải toàn là kí tự số
			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) < '0' || value.charAt(i) > '9') {
					check = false;
					break;
				}
			}
			if (check) {
//			    gán result bằng giá trị chuyển đổi
				result = Integer.parseInt(value);
			}
		}
//      Nếu value bằng "" thì result = numberDefault
		return result;
	}

	/**
	 * phương thức getListDay là phương thức trả về mảng ngày
	 * 
	 * @return listDay mảng lưu giá trị từ ngày 1 đến ngày 31
	 */
	public List<Integer> getListDay() {
		List<Integer> listDay = new ArrayList<>();
		for (int i = 1; i <= Constant.DAY; i++) {
			listDay.add(i);
		}
		return listDay;
	}

	/**
	 * phương thức getListMonth là phương thức trả về mảng tháng
	 * 
	 * @return listMonth mảng lưu giá trị từ tháng 1 đến tháng 12
	 */
	public List<Integer> getListMonth() {
		List<Integer> listMonth = new ArrayList<>();
		for (int i = 1; i <= Constant.MONTH; i++) {
			listMonth.add(i);
		}
		return listMonth;
	}

	/**
	 * phương thức getListYear là phương thức trả về mảng năm
	 * 
	 * @return listYear mảng lưu giá trị từ fromYear đến toYear
	 */
	public List<Integer> getListYear(int fromYear, int toYear) {
		List<Integer> listYear = new ArrayList<>();
		for (int i = fromYear; i <= toYear; i++) {
			listYear.add(i);
		}
		return listYear;
	}

	/**
	 * phương thức getCurrentYear là phương thức lấy ra năm hiện tại
	 * 
	 * @return giá trị năm hiện tại
	 */
	public int getCurrentYear() {
		int currentYear;
		// Khởi tạo Calendar
		Calendar now = Calendar.getInstance();
		// lấy giá trị năm hiện tại
		currentYear = now.get(Calendar.YEAR);
		return currentYear;
	}

	/**
	 * phương thức getCurrentMonth là phương thức lấy ra tháng hiện tại
	 * 
	 * @return giá trị tháng hiện tại
	 */
	public int getCurrentMonth() {
		int currentMonth;
		// khởi tạo Calendar
		Calendar now = Calendar.getInstance();
		// lấy giá trị năm hiện tại
		currentMonth = now.get(Calendar.MONTH) + 1;
		return currentMonth;
	}

	/**
	 * phương thức getCurrentDay là phương thức lấy ra ngày hiện tại
	 * 
	 * @return giá trị ngày hiện tại
	 */
	public int getCurrentDay() {
		int currentDay;
		// Khởi tạo Calendar
		Calendar now = Calendar.getInstance();
		// lấy giá trị ngày hiện tại
		currentDay = now.get(Calendar.DATE);
		return currentDay;
	}

	/**
	 * phương thức checkFormatLoginName là phương thức để kiểm tra login_name có
	 * đúng định format của requirement
	 * 
	 * @param loginName chuỗi tên đăng nhập người dùng nhập
	 * @return check là true nếu người dùng nhập đúng, false nếu người dùng nhập sai
	 *         format
	 */
	public boolean checkFormatLoginName(String loginName) {
		boolean check = loginName.matches(Constant.FORMAT_LOGIN_NAME);
		return check;
	}

	/**
	 * phương thức creatSalt là phương thức lấy ra chuỗi số ngẫu nhiên theo thời
	 * gian hiện tại tính theo milliseconds
	 * 
	 * @return millis là thời gian hiện tại
	 */
	public long creatSalt() {
		long millis = System.currentTimeMillis();
		return millis;
	}

	/**
	 * phương thức checkKana là phương thức để kiểm tra fullNameKana có tất cả các
	 * kí tự là kí tự Kana
	 * 
	 * @param fullNameKana chuỗi người dùng nhập
	 * @return check là true nếu người dùng nhập toàn là kí tự kana, false nếu người
	 *         dùng nhập sai
	 */
	public boolean checkKana(String fullNameKana) {
		boolean check = fullNameKana.matches(Constant.FORMAT_FULL_NAME_KANA);
		return check;
	}

	/**
	 * phương thức checkDate là phương thức để kiểm tra date có hợp lệ
	 * 
	 * @param date là ngày
	 * @return check là true nếu hợp lệ, false nếu không hợp lệ
	 */
	public boolean checkDate(String date) {
		String dateFormat = "yyyy-MM-dd";
		boolean check = true;
		try {
//			Khởi tạo đối tượng DateFormat
			DateFormat df = new SimpleDateFormat(dateFormat);
//			Chỉ định bắn ra exception khi parse ngày không hợp lệ
			df.setLenient(false);
//			chuyển chuỗi date thành ngày
			df.parse(date);
		} catch (ParseException e) {
			check = false;
		}
		return check;
	}

	/**
	 * phương thức checkFormatEmail là phương thức để kiểm tra format email có hợp
	 * lệ
	 * 
	 * @param email là chuỗi địa chỉ mail
	 * @return check là true nếu hợp lệ, false nếu không hợp lệ
	 */
	public boolean checkFormatEmail(String email) {
		boolean check = email.matches(Constant.FORMAT_EMAIL);
		return check;
	}

	/**
	 * phương thức checkFormatTel là phương thức để kiểm tra format tel có hợp lệ
	 * 
	 * @param tel là chuỗi số điện thoại
	 * @return check là true nếu hợp lệ, false nếu không hợp lệ
	 */
	public boolean checkFormatTel(String tel) {
		boolean check = tel.matches(Constant.FORMAT_TEL);
		return check;
	}

	/**
	 * phương thức checkOneBytePassword là phương thức để kiểm tra password có các
	 * kí tự là kí tự 1 byte
	 * 
	 * @param password là mật khẩu
	 * @return check là true nếu hợp lệ, false nếu không hợp lệ
	 */
	public boolean checkOneBytePassword(String password) {
		boolean check = true;
		for (int i = 0; i < password.length(); i++) {
//			Nếu giá trị của kí tự > 255 . Kí tự không là kí tự 1 byte
			if ((int) password.charAt(i) > 255) {
				check = false;
				break;
			}
		}
		return check;
	}

	/**
	 * phương thức checkHalfSize là phương thức để kiểm tra total có là số halfsize
	 * 
	 * @param total là điểm
	 * @return check là true nếu hợp lệ, false nếu không hợp lệ
	 */
	public boolean checkHalfSize(String total) {
		boolean check = total.matches(Constant.FORMAT_TOTAL);
		return check;
	}

	/**
	 * phương thức getTblUserEntity là phương thức lấy ra đối tượng tblUserEntity
	 * 
	 * @param userInfoEntity chứa thông tin của user và trình độ tiếng Nhật
	 * @return tblUserEntity chứa thông tin của user trong bảng tbl_user
	 * @throws NoSuchAlgorithmException
	 */
	public TblUserEntity getTblUserEntity(UserInfoEntity userInfoEntity) throws NoSuchAlgorithmException {
		TblUserEntity tblUserEntity = null;
		try {
//		    Khởi tạo đối tượng tblUserEntity
			tblUserEntity = new TblUserEntity();
//		    Set giá trị thuộc tính tblUserEntity
			tblUserEntity.setUserID(userInfoEntity.getUserID());
			tblUserEntity.setGroupID(userInfoEntity.getGroupId());
			tblUserEntity.setLoginName(userInfoEntity.getLoginName());
			String salt = creatSalt() + "";
			tblUserEntity.setSalt(salt);
			tblUserEntity.setPassWord(encryptPassword(userInfoEntity.getPassWord(), salt));
			tblUserEntity.setFullName(userInfoEntity.getFullName());
			tblUserEntity.setFullNameKana(userInfoEntity.getFullNameKana());
			tblUserEntity.setEmail(userInfoEntity.getEmail());
			tblUserEntity.setTel(userInfoEntity.getTel());
			tblUserEntity.setBirthday(userInfoEntity.getBirthday());
//		    Set rule của đối tượng tblUserEntity là 1
			tblUserEntity.setRule(Integer.parseInt(Constant.RULE_USER));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Common.java, getTblUserEntity " + e.getMessage());
			throw e;
		}
		return tblUserEntity;
	}

	/**
	 * phương thức getTblDetailUserJapanEntity là phương thức lấy ra thông tin trình
	 * độ tiếng Nhật của user
	 * 
	 * @param userInfoEntity chứa thông tin của user và trình độ tiếng Nhật
	 * @param iDUser         mã của user trong bảng tbl_user
	 * @return chứa thông tin của trình độ tiếng Nhật
	 */
	public TblDetailUserJapanEntity getTblDetailUserJapanEntity(UserInfoEntity userInfoEntity, int iDUser) {
//		Khởi tạo đối tượng tblDetailUserJapanEntity
		TblDetailUserJapanEntity tblDetailUserJapanEntity = new TblDetailUserJapanEntity();
//		Set userId cho tblDetailUserJapanEntity
		tblDetailUserJapanEntity.setUserId(iDUser);
//		Set codeLevel cho tblDetailUserJapanEntity
		tblDetailUserJapanEntity.setCodeLevel(userInfoEntity.getCodeLevel());
//		Set startDate cho tblDetailUserJapanEntity
		tblDetailUserJapanEntity.setStartDate(userInfoEntity.getStartDate());
//		Set endDate cho tblDetailUserJapanEntity
		tblDetailUserJapanEntity.setEndDate(userInfoEntity.getEndDate());
//		Set total cho tblDetailUserJapanEntity
		tblDetailUserJapanEntity.setTotal(userInfoEntity.getTotal());
		return tblDetailUserJapanEntity;
	}

	/**
	 * phương thức setDataLogic là phương thức set list giá trị cho các slectbox màn
	 * hình ADM003 phần EDIT
	 * 
	 * @param request  là đối tượng chứa thông tin yêu cầu
	 * @param response là đối tượng chứa thông tin phản hồi
	 */
	public void setDataLogic(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		try {
//		    Khởi tạo đối tượng common
			Common common = new Common();
//		    Lấy giá trị năm hiện tại
			int currentYear = common.getCurrentYear();
//		    Khai báo và lấy giá trị mảng năm
			List<Integer> listYear = common.getListYear(Constant.FROM_YEAR, currentYear);
//			Khai báo và lấy giá trị mảng năm cho endDate
			List<Integer> listNextYear = common.getListYear(Constant.FROM_YEAR, currentYear + 1);
//		    Khai báo và lấy giá trị mảng tháng
			List<Integer> listMonth = common.getListMonth();
//		    Khai báo và lấy giá trị mảng ngày
			List<Integer> listDay = common.getListDay();
//		    Khởi tạo đối tượng mstJapanLogicImpl
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
//			Khởi tạo đối tượng mstGroupLogicImpl
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
//		    Khai báo mảng đối tượng listMstJapanEntity
			List<MstJapanEntity> listMstJapanEntity = mstJapanLogicImpl.getAllMstJapan();
//			Khai báo mảng đối tượng 
			List<MstGroupEntity> listMstGroupEntity = mstGroupLogicImpl.getAllMstGroup();
//			Set lên request
			request.setAttribute("listMstJapanEntity", listMstJapanEntity);
			request.setAttribute("listMstGroupEntity", listMstGroupEntity);
			request.setAttribute("listYear", listYear);
			request.setAttribute("listNextYear", listNextYear);
			request.setAttribute("listMonth", listMonth);
			request.setAttribute("listDay", listDay);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Common.java, setDataLogic " + e.getMessage());
			throw e;
		}
	}

}