<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/view/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/ADM003.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
		<header><jsp:include page="Header.jsp"/></header>
    <!-- End vung header -->	

<!-- Begin vung input-->
    <c:if test="${userInfoEntity.userID == '0'}">	
	<form action="addUserValidate.do?action=confirm" method="post" name="inputform">
	</c:if>
	<c:if test="${userInfoEntity.userID != '0'}">	
	<form action="confirmValidate.do?action=confirm" method="post" name="inputform">
	<input value="${userInfoEntity.userID}" name="iDUserInfor" style="display: none" />
	</c:if>	
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>		
		<tr>
			<td class="errMsg">
				<div style="padding-left:120px">
				    <c:forEach items="${listMessage}" var="message">
				        <c:out value="${message}"/>
				        <br/>
				    </c:forEach>
					&nbsp;
				</div>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">	
							<c:if test="${userInfoEntity.userID == '0'}">			 
							<input class="txBox" type="text" name="loginName" value="<c:out value="${userInfoEntity.loginName}"/>"
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
							</c:if>
							<c:if test="${userInfoEntity.userID != '0'}">			 
							<input class="txBox" type="text" name="loginName" value="<c:out value="${userInfoEntity.loginName}"/>"
							size="15" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" readonly/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
							<select name="groupId">
								<option value="0">選択してください</option>
								<c:forEach items="${listMstGroupEntity}" var="group">					
								<option value="${group.groupId}" <c:if test="${group.groupId == userInfoEntity.groupId}">selected="selected"</c:if>><c:out value="${group.groupName}"/></option>
								</c:forEach>	
							</select>							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullName" value="<c:out value="${userInfoEntity.fullName}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />					
						</td>
					</tr>
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullNameKana" value="<c:out value="${userInfoEntity.fullNameKana}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />					
						</td>
					</tr>	
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 生年月日:</td>
						<td align="left">
						        <c:set var="dateBD" value="${fn:split(userInfoEntity.birthday, '-')}"/>
						        <select name = "yearBD">
						            <c:forEach items="${listYear}" var="year">
									<option value="${year}" <c:if test="${year == dateBD[0]}">selected="selected"</c:if>><c:out value="${year}"/></option>
									</c:forEach>
					            </select>年
								<select name = "monthBD">
									<c:forEach items="${listMonth}" var="month">
									<option value="${month}" <c:if test="${month == dateBD[1]}">selected="selected"</c:if>><c:out value="${month}"/></option>
									</c:forEach>
								</select>月
								<select name = "dayBD">
									<c:forEach items="${listDay}" var="day">
									<option value="${day}" <c:if test="${day == dateBD[2]}">selected="selected"</c:if>><c:out value="${day}"/></option>
									</c:forEach>
								</select>日							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name="email" value="<c:out value="${userInfoEntity.email}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />					
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name="tel" value="<c:out value="${userInfoEntity.tel}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />				
						</td>
					</tr>
					<c:if test="${userInfoEntity.userID == '0'}">		
					<tr>
						<td class="lbl_left"><font color = "red">*</font> パスワード:</td>
						<td align="left">						 
							<input class="txBox" type="password" name="passWord" value="<c:out value="${userInfoEntity.passWord}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />											
						</td>
					</tr>
					<tr>
						<td class="lbl_left">パスワード（確認）:</td>
						<td align="left">
							<input class="txBox" type="password" name="rePassWord" value="<c:out value="${userInfoEntity.rePassWord}"/>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />				
						</td>
					</tr>
					</c:if>
					<tr>
						<th align="left" colspan = "2" >							
								<a href = "#" onclick="hideform()">日本語能力</a>
						</th>			
					</tr>
					
					<tbody id="link_Japanese_Level" style="display: none">
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select name="kyu_id">				  
								<option value="0">選択してください</option>	
								<c:forEach items="${listMstJapanEntity}" var="MstJapanEntity">					
								<option value="${MstJapanEntity.codeLevel}" <c:if test="${MstJapanEntity.codeLevel == userInfoEntity.codeLevel}">selected="selected"</c:if>><c:out value="${MstJapanEntity.nameLevel}"/></option>
								</c:forEach>	
							</select>							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日: </td>
						<td align="left">
						        <c:set var="dateSD" value="${fn:split(userInfoEntity.startDate, '-')}"/>
							    <select name = "yearSD">
									<c:forEach items="${listYear}" var="year">
									<option value="${year}" <c:if test="${year == dateSD[0]}">selected="selected"</c:if>><c:out value="${year}"/></option>
									</c:forEach>
								</select>年
								<select name = "monthSD">
									<c:forEach items="${listMonth}" var="month">
									<option value="${month}" <c:if test="${month == dateSD[1]}">selected="selected"</c:if>><c:out value="${month}"/></option>
									</c:forEach>
								</select>月
								<select name = "daySD">
									<c:forEach items="${listDay}" var="day">
									<option value="${day}" <c:if test="${day == dateSD[2]}">selected="selected"</c:if>><c:out value="${day}"/></option>
									</c:forEach>
								</select>日							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">失効日: </td>
						<td align="left">
						    <c:set var="dateED" value="${fn:split(userInfoEntity.endDate, '-')}"/>
							    <select name = "yearED">
									<c:forEach items="${listNextYear}" var="year">
									<option value="${year}" <c:if test="${year == dateED[0]}">selected="selected"</c:if>><c:out value="${year}"/></option>
									</c:forEach>
								</select>年
								<select name = "monthED">
									<c:forEach items="${listMonth}" var="month">
									<option value="${month}" <c:if test="${month == dateED[1]}">selected="selected"</c:if>><c:out value="${month}"/></option>
									</c:forEach>
								</select>月
								<select name = "dayED">
									<c:forEach items="${listDay}" var="day">
									<option value="${day}" <c:if test="${day == dateED[2]}">selected="selected"</c:if>><c:out value="${day}"/></option>
									</c:forEach>
								</select>日							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">点数: </td>
						<td align="left">		    
							<input class="txBox" type="text" name="total" 
							value="<c:out value="${userInfoEntity.total}"/>"	
							size="5" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />				
						</td>
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
					<input class="btn" type="submit" value="確認"/>					
				</td>	
				<td>
					<input class="btn" type="button" onclick="location.href='${pageContext.request.contextPath}/listUser.do?action=back'"
					 value="戻る"/>					
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