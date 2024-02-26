<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/view/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/ADM004.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
		<header><jsp:include page="Header.jsp"/></header>
    <!-- End vung header -->	

<!-- Begin vung input-->
    <c:if test="${userInfoEntity.userID == 0}">
	<form action="AddUserOK.do" method="post" name="inputform">	
	</c:if>
	<c:if test="${userInfoEntity.userID != 0}">
	<form action="EditUserConfirm.do" method="post" name="inputform">	
	</c:if>
		
	<input value="${keyUser}" name="keyUser"
				style="display: none" />
	<table  class="tbl_input" border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					情報確認<br>
					入力された情報をＯＫボタンクリックでＤＢへ保存してください
				</div>
				<div style="padding-left:100px;">&nbsp;</div>
			</th>			
		</tr>				
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left" >アカウント名:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.loginName}"/></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.groupName}"/></td>
					</tr>
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.fullName}"/></td>
					</tr>	
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.fullNameKana}"/></td>
					</tr>
					<tr>
						<td class="lbl_left">生年月日:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${fn:replace(userInfoEntity.birthday, '-', '/')}"/></td>
					</tr>				
					<tr>
						<td class="lbl_left">メールアドレス:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.email}"/></td>
					</tr>
					<tr>
						<td class="lbl_left">電話番号:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.tel}"/></td>
					</tr>	
					<tr>
						<th colspan = "2"><a href = "#" onclick="hideform()">日本語能力</a></th>
					</tr>
					<tbody id="link_Japanese_Level" style="display: none">
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left" style="word-break: break-word; width: 499px;"><c:out value="${userInfoEntity.nameLevel}"/></td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日:</td>
						<td align="left" style="word-break: break-word; width: 499px;">
						<c:if test="${userInfoEntity.codeLevel != '0'}">
						<c:out value="${fn:replace(userInfoEntity.startDate, '-', '/')}"/></td>
						</c:if> 
						<c:if test="${userInfoEntity.codeLevel == '0'}">
						<c:out value=""/>
						</c:if> 
					</tr>
					<tr>
						<td class="lbl_left">失効日:</td>
						<td align="left">
						<c:if test="${userInfoEntity.codeLevel != '0'}">
						<c:out value="${fn:replace(userInfoEntity.endDate, '-', '/')}"/>
						</c:if> 
						<c:if test="${userInfoEntity.codeLevel == '0'}">
						<c:out value=""/>
						</c:if> 
						
						</td>
					</tr>	
					<tr>
						<td class="lbl_left">点数:</td>
						<c:if test="${userInfoEntity.codeLevel == '0'}">
						<td align="left"></td>
						</c:if>
						<c:if test="${userInfoEntity.codeLevel != '0'}">
						<td align="left"><c:out value="${userInfoEntity.total}"/></td>
						</c:if>
					</tr>
					</tbody>												
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:45px;">
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="OK" />						
				</td>	
				<td>
				    <c:if test="${userInfoEntity.userID == 0}">	
					<input class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/addUserInput.do?action=back&keyUser=${keyUser}'" value="戻る" />		
					</c:if>	
					<c:if test="${userInfoEntity.userID != 0}">	
					<input class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/editUserInput.do?action=back&keyUser=${keyUser}&iDUserInfor=${userInfoEntity.userID}'" value="戻る" />		
					</c:if>				
				</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<!-- End vung input -->

<!-- Begin vung footer -->
<footer><jsp:include page="Footer.jsp"/></footer>
<!-- End vung footer -->
</body>

</html>