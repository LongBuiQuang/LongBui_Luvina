<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/view/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<header><jsp:include page="Header.jsp"/></header>
	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="listUser.do" method="get" name="mainform">
		<input value="search" name="action" style="display: none" />
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left" >
							    <input class="txBox" type="text"
								name="fullname" value="<c:out value = "${fullname}"/>" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />
							</td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px">
							<select name="groupId" style="max-width: 200px;">
							<option value="0" <c:if test="${groupId == '0'}">selected="selected"</c:if>>全て</option>			
							<c:forEach items="${listMstGroupEntity}" var="group">										
								<option value="${group.groupId}" <c:if test="${group.groupId == groupId}">selected="selected"</c:if> style="word-break: hyphenate;">							       
								<c:out value="${group.groupName}"/> 
								</option>					
							</c:forEach>					
							</select>
							</td>
							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button"
								 onclick="location.href='${pageContext.request.contextPath}/addUserInput.do?action=default'"
								 value="新規追加" />
						    </td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<c:if test="${!empty message}">
		<label><c:out value="${message}" /></label>
	</c:if>
	<c:if test="${empty message}">
		<!-- Begin vung hien thi danh sach user -->
		<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
			width="80%">
			<tr class="tr2">
				<th align="center" width="20px">ID</th>
				
				
				<th align="left">氏名 
				<c:url value="/listUser.do" var="urlSortByFullName">
				   <c:param name="action" value="sort"/>
				   <c:param name="sortType" value="full_name"/>
				   <c:param name="fullname" value="${fullname}"/>
				   <c:param name="groupId" value="${groupId}"/>
				   <c:param name="sortByFullName" value="${sortByFullName}"/>
				</c:url>	
				<a href="${urlSortByFullName}">				
				<c:if test="${sortByFullName == 'ASC'}"> ▲▽</c:if> 
				<c:if test="${sortByFullName == 'DESC'}"> △▼</c:if>
				</a>
				</th>
				
							
				<th align="left">生年月日</th>
				<th align="left">グループ</th>
				<th align="left">メールアドレス</th>
				<th align="left" width="70px">電話番号</th>
							
				
				<th align="left">日本語能力 
				<c:url value="/listUser.do" var="urlSortByCodeLevel">
				   <c:param name="action" value="sort"/>
				   <c:param name="sortType" value="code_level"/>
				   <c:param name="fullname" value="${fullname}"/>
				   <c:param name="groupId" value="${groupId}"/>
				   <c:param name="sortByCodeLevel" value="${sortByCodeLevel}"/>
				</c:url>	
				<a href="${urlSortByCodeLevel}">				
				<c:if test="${sortByCodeLevel == 'ASC'}"> ▲▽</c:if> 
				<c:if test="${sortByCodeLevel == 'DESC'}"> △▼</c:if>
				</a>	
				</th>
				
						
				<th align="left">失効日 
				<c:url value="/listUser.do" var="urlSortByEndDate">
				   <c:param name="action" value="sort"/>
				   <c:param name="sortType" value="end_date"/>
				   <c:param name="fullname" value="${fullname}"/>
				   <c:param name="groupId" value="${groupId}"/>
				   <c:param name="sortByEndDate" value="${sortByEndDate}"/>
				</c:url>	
				<a href="${urlSortByEndDate}">				
				<c:if test="${sortByEndDate == 'ASC'}"> ▲▽</c:if> 
				<c:if test="${sortByEndDate == 'DESC'}"> △▼</c:if>
				</a>	
				</th>
				
				
				<th align="left">点数</th>		
			</tr>
			
			
			<c:forEach items="${listUserInfoEntity}" var="userInfoEntity">
				<tr>
					<td align="right" style="word-break: break-word; width: 10px;"><a href="ViewUserInfor.do?&iDUserInfor=${userInfoEntity.userID}"><c:out value="${userInfoEntity.userID}"/></a></td>
					<td style="word-break: break-word; width: 245px;"><c:out value="${userInfoEntity.fullName}"/></td>
					<td align="center" style="word-break: break-word; width: 123px;"><c:out value="${fn:replace(userInfoEntity.birthday, '-', '/')}"/></td>
					<td style="word-break: break-word; width: 109px;"><c:out value="${userInfoEntity.groupName}"/></td>
					<td style="word-break: break-word; width: 198px;"><c:out value="${userInfoEntity.email}"/></td>
					<td style="word-break: break-word; width: 79px;"><c:out value="${userInfoEntity.tel}"/></td>
					<td style="word-break: break-word; width: 252px;"><c:out value="${userInfoEntity.nameLevel}"/></td>
					<td align="center" style="word-break: break-word; width: 129px;"><c:out value="${fn:replace(userInfoEntity.endDate, '-', '/')}"/></td>
					<td align="right" style="word-break: break-word; width: 63px;">
					<c:if test="${userInfoEntity.total == 0}">
					<c:out value=""/>
					</c:if>
					<c:if test="${userInfoEntity.total != 0}">
					<c:out value="${userInfoEntity.total}"/>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<!-- End vung hien thi danh sach user -->
		
		
		
		
		
		<!-- Begin vung paging -->
		<table>

			<tr>
				<td class="lbl_paging">
				
				
				<c:if test="${listPaging.get(0) > 1}">
				<c:url value="/listUser.do" var="urlBack">
				    <c:param name="action" value="paging"/>
				    <c:param name="sortType" value="${sortType}"/>
				    <c:param name="fullname" value="${fullname}"/>
				    <c:param name="groupId" value="${groupId}"/>
				    <c:param name="sortByFullName" value="${sortByFullName}"/>
				    <c:param name="sortByCodeLevel" value="${sortByCodeLevel}"/>
				    <c:param name="sortByEndDate" value="${sortByEndDate}"/>
				    <c:param name="currentPage" value="${listPaging.get(0) - 1}"/>
				</c:url>	
				<a href="${urlBack}"><<</a>&nbsp;			
				</c:if> 
						
				
				<c:forEach items="${listPaging}" var="paging">
				<c:if test="${currentPage != paging}">
				    <c:url value="/listUser.do" var="numberLinkPaging">
				        <c:param name="action" value="paging"/>
				        <c:param name="sortType" value="${sortType}"/>
				        <c:param name="fullname" value="${fullname}"/>
				        <c:param name="groupId" value="${groupId}"/>
				        <c:param name="sortByFullName" value="${sortByFullName}"/>
				        <c:param name="sortByCodeLevel" value="${sortByCodeLevel}"/>
				        <c:param name="sortByEndDate" value="${sortByEndDate}"/>
				        <c:param name="currentPage" value="${paging}"/>
				    </c:url>
					<a href="${numberLinkPaging}">${paging}</a> &nbsp;
				</c:if>
					
				<c:if test="${currentPage == paging}">
					${paging}&nbsp;
				</c:if>
				</c:forEach> 
				
							
				<c:if test="${listPaging.get(listPaging.size() - 1) < totalPage}">
				<c:url value="/listUser.do" var="urlNext">
				<c:param name="action" value="paging"/>
				<c:param name="sortType" value="${sortType}"/>
				<c:param name="fullname" value="${fullname}"/>
				<c:param name="groupId" value="${groupId}"/>
				<c:param name="sortByFullName" value="${sortByFullName}"/>
				<c:param name="sortByCodeLevel" value="${sortByCodeLevel}"/>
				<c:param name="sortByEndDate" value="${sortByEndDate}"/>
				<c:param name="currentPage" value="${listPaging.get(listPaging.size() - 1) + 1}"/>
				</c:url>
				<a href="${urlNext}">>></a>
				</c:if>
				
				
				</td>
			</tr>
		</table>
		<!-- End vung paging -->
	</c:if>
	
	
	
	<!-- Begin vung footer -->
	<footer><jsp:include page="Footer.jsp"/></footer>
	<!-- End vung footer -->
</body>

</html>