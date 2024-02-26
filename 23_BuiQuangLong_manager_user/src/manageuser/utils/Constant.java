/**
 * Copyright(C) 2020 Luvina JSC
 * Constant.java, 17/11/2020 Bui Quang Long
 */
package manageuser.utils;

import manageuser.properties.ConfigProperties;
import manageuser.properties.DatabaseProperties;

/**
 * class Constant khai báo các hằng số
 * 
 * @author LA-PM Bui Quang Long
 *
 */
public class Constant {
//	Đường dẫn các màn hình 
	public static final String PATH_ADM001 = "view/jsp/ADM001.jsp";
	public static final String PATH_ADM002 = "view/jsp/ADM002.jsp";
	public static final String PATH_ADM003 = "view/jsp/ADM003.jsp";
	public static final String PATH_ADM004 = "view/jsp/ADM004.jsp";
	public static final String PATH_ADM005 = "view/jsp/ADM005.jsp";
	public static final String PATH_ADM006 = "view/jsp/ADM006.jsp";
	public static final String PATH_SYSTEM_ERROR = "view/jsp/System_Error.jsp";
//	Các annotation
	public static final String LOGIN_DO = "/login.do";
	public static final String LOGOUT_DO = "/logout.do";
	public static final String LIST_USER_DO = "/listUser.do";
	public static final String ADD_USER_INPUT_DO = "/addUserInput.do";
	public static final String ADD_USER_VALIDATE_DO = "/addUserValidate.do";
	public static final String ADD_USER_CONFIRM_DO = "/AddUserConfirm.do";
	public static final String ADD_USER_OK_DO = "/AddUserOK.do";
	public static final String VIEW_USERINFOR_DO = "/ViewUserInfor.do";
	public static final String SUCCESS_DO = "/Success.do";
	public static final String EDIT_USER_INPUT_DO = "/editUserInput.do";
	public static final String CONFIRM_VALIDATE_DO = "/confirmValidate.do";
	public static final String EDIT_USER_CONFIRM_DO = "/EditUserConfirm.do";
	public static final String EDIT_USER_OK_DO = "/EditUserOK.do";
	public static final String DELETE_USERINFOR_DO = "/deleteUserInfor.do";
	public static final String SUCCESS = "/Success.do";
	public static final String ERROR = "/Error.do";
//  Màn hình ADM001
	public static final String LOGIN_NAME = "loginName";
	public static final String PASSWORD = "passWord";
//	Màn hình ADM002
	public static final String ACTION = "action";
	public static final String SORT_TYPE = "sortType";
	public static final String DEFAULT = "default";
	public static final String SEARCH = "search";
	public static final String SORT = "sort";
	public static final String PAGING = "paging";
	public static final String BACK = "back";
	public static final String SORT_TYPE_FULL_NAME = "full_name";
	public static final String SORT_TYPE_CODE_LEVEL = "code_level";
	public static final String SORT_TYPE_END_DATE = "end_date";
	public static final String FULL_NAME = "fullname";
	public static final String GROUP_ID = "groupId";
	public static final String SORT_BY_FULLNAME = "sortByFullName";
	public static final String SORT_BY_CODE_LEVEL = "sortByCodeLevel";
	public static final String SORT_BY_END_DATE = "sortByEndDate";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String TOTAL_PAGE = "totalPage";
	public static final String LIST_PAGING = "listPaging";
	public static final String LIST_USER_INFOR_ENTITY = "listUserInfoEntity";
	public static final String MESSAGE = "message";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String LIMIT = "LIMIT";
	public static final String NUMBER_PAGE_LIMIT = "NUMBER_PAGE_LIMIT";
//	Màn hình ADM003
	public static final String USER_INFOR_ENTITY = "userInfoEntity";
	public static final String KEY_FROM_ADM003_ADD = "keyFromADM003Add";
	public static final String FROM_ADM003_ADD = "fromADM003Add";
	public static final String KEY_USER = "keyUser";
	public static final String LIST_MST_JAPAN_ENTITY = "listMstJapanEntity";
	public static final String LIST_YEAR = "listYear";
	public static final String LIST_NEXT_YEAR = "listNextYear";
	public static final String LIST_MONTH = "listMonth";
	public static final String LIST_DAY = "listDay";
	public static final String FULLNAME = "fullName";
	public static final String FULLNAME_KANA = "fullNameKana";
	public static final String YEAR_BD = "yearBD";
	public static final String MONTH_BD = "monthBD";
	public static final String DAY_BD = "dayBD";
	public static final String EMAIL = "email";
	public static final String TEL = "tel";
	public static final String RE_PASSWORD = "rePassWord";
	public static final String KYU_ID = "kyu_id";
	public static final String YEAR_SD = "yearSD";
	public static final String MONTH_SD = "monthSD";
	public static final String DAY_SD = "daySD";
	public static final String YEAR_ED = "yearED";
	public static final String MONTH_ED = "monthED";
	public static final String DAY_ED = "dayED";
	public static final String TOTAL = "total";
	public static final String ID_USER_INFOR = "iDUserInfor";
	public static final String KEY_FROM_ADM003_EDIT = "keyFromADM003Edit";
	public static final String FROM_ADM003_EDIT = "fromADM003Edit";
	public static final String TYPE = "type";
	public static final int DAY = 31;
	public static final int MONTH = 12;
	public static final int FROM_YEAR = 1990;
	public static final String CONFIRM = "confirm";
//	Màn hình ADM006
	public static final String MSG001 = "MSG001";
	public static final String MSG002 = "MSG002";
	public static final String MSG003 = "MSG003";
//	Màn hình System error
	public static final String ER013 = "ER013";
	public static final String ER014 = "ER014";
	public static final String ER015 = "ER015";
	public static final String ER020 = "ER020";
//  filter
	public static final String UTF_8 = "UTF-8";
//	Dùng chung
	public static final String LIST_MESSAGE = "listMessage";
	public static final String LIST_MST_GROUP_ENTITY = "listMstGroupEntity";
	public static final String EMPTY_STRING = "";
	public static final String ZERO_STRING = "0";
	public static final int ONE = 1;
	public static final int ZERO = 0;
//	Đường dẫn đến file properties 
	public static final String PROPERTIES_MESSAGE_ERROR_PATH = "manageuser/properties/message_error_ja.properties";
	public static final String PROPERTIES_MESSAGE_PATH = "manageuser/properties/message_ja.properties";
	public static final String PROPERTIES_DATABASE_PATH = "manageuser/properties/database_properties.properties";
	public static final String PROPERTIES_CONFIG_PATH = "manageuser/properties/config.properties";
//	Database
	public static final String DB_URL = DatabaseProperties.getValueByKey("DB_URL");
	public static final String DB_USER_NAME = DatabaseProperties.getValueByKey("USER_NAME");
	public static final String DB_PASSWORD = DatabaseProperties.getValueByKey("PASSWORD");
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DATABASENAME = "23_buiquanglong_manageuser";
	public static final String TBL_USER = "tbl_user";
	public static final String MST_JAPAN = "mst_japan";
	public static final String TBL_DETAIL_USER_JAPAN = "tbl_detail_user_japan";
//	Rule
	public static final String RULE_ADMIN = "0";
	public static final String RULE_USER = "1";
//	Các message ADM001
	public static final String ER001_USERNAME_EMPTY = "ER001_USERNAME_EMPTY";
	public static final String ER001_PASSWORD_EMPTY = "ER001_PASSWORD_EMPTY";
	public static final String ER016_WRONG_INFORMATION = "ER016_WRONG_INFORMATION";
//	Các message ADM002
	public static final String MSG005_CANNOT_FIND_USER = "MSG005_CANNOT_FIND_USER";
//	Các message ADM003
	public static final String ER001_LOGINNAME = "ER001_LOGINNAME";
	public static final String ER019_LOGINNAME = "ER019_LOGINNAME";
	public static final String ER007_LOGINNAME = "ER007_LOGINNAME";
	public static final String ER003_LOGINNAME = "ER003_LOGINNAME";
	public static final String ER002_GROUPNAME = "ER002_GROUPNAME";
	public static final String ER004_GROUPNAME = "ER004_GROUPNAME";
	public static final String ER001_FULLNAME = "ER001_FULLNAME";
	public static final String ER006_FULLNAME = "ER006_FULLNAME";
	public static final String ER009_FULLNAMEKANA = "ER009_FULLNAMEKANA";
	public static final String ER006_FULLNAMEKANA = "ER006_FULLNAMEKANA";
	public static final String ER011_BIRTHDAY = "ER011_BIRTHDAY";
	public static final String ER001_EMAIL = "ER001_EMAIL";
	public static final String ER006_EMAIL = "ER006_EMAIL";
	public static final String ER005_EMAIL = "ER005_EMAIL";
	public static final String ER003_EMAIL = "ER003_EMAIL";
	public static final String ER001_TEL = "ER001_TEL";
	public static final String ER006_TEL = "ER006_TEL";
	public static final String ER005_TEL = "ER005_TEL";
	public static final String ER001_PASSWORD = "ER001_PASSWORD";
	public static final String ER008_PASSWORD = "ER008_PASSWORD";
	public static final String ER007_PASSWORD = "ER007_PASSWORD";
	public static final String ER017_REPASSWORD = "ER017_REPASSWORD";
	public static final String ER004_CODELEVEL = "ER004_CODELEVEL";
	public static final String ER011_STARTDATE = "ER011_STARTDATE";
	public static final String ER011_ENDDATE = "ER011_ENDDATE";
	public static final String ER012_ENDDATE = "ER012_ENDDATE";
	public static final String ER001_TOTAL = "ER001_TOTAL";
	public static final String ER018_TOTAL = "ER018_TOTAL";
	public static final String ER021_TOTAL = "ER021_TOTAL";
//	Regex
	public static final String FORMAT_LOGIN_NAME = ConfigProperties.getValueByKey("FORMAT_LOGIN_NAME");
	public static final String FORMAT_FULL_NAME_KANA = ConfigProperties.getValueByKey("FORMAT_FULL_NAME_KANA");
	public static final String FORMAT_EMAIL = ConfigProperties.getValueByKey("FORMAT_EMAIL");
	public static final String FORMAT_TEL = ConfigProperties.getValueByKey("FORMAT_TEL");
	public static final String FORMAT_TOTAL = ConfigProperties.getValueByKey("FORMAT_TOTAL");
}