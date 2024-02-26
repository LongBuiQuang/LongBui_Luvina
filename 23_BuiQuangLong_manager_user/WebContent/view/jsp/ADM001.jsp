<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${contextPath}/view/css/style.css" rel="stylesheet"
	type="text/css" />
<title>ログイン</title>
</head>
<body align="center">
	<form action="/23_BuiQuangLong_manager_user/login.do" method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tr>
					<th width="120px">&nbsp;</th>
					<th></th>
				</tr>
				<tr>
					<th colspan="2" align="left">アカウント名およびパスワードを入力してください</th>
				</tr>

				<tr>
					<td class="errMsg" colspan="2"><c:forEach
							items="${listMessage}" var="messError">
							<p style="color: red;"><c:out value="${messError}"/></p>
						</c:forEach></td>
				</tr>
				<tr align="left">
					<td class="lbl_left">アカウント名:</td>
					<td align="left"><input class="txBox" type="text"
						name="loginName" value="<c:out value="${loginName}"/>" size="20"
						maxlength="255"> </input></td>
				</tr>
				<tr>
					<td class="lbl_left">パスワード:</td>
					<td align="left"><input class="txBox" type="password"
						name="passWord" value="" size="20" maxlength="15"></input></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="ログイン"></input></td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>