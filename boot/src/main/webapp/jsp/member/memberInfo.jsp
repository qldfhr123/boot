<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
<style type="text/css">
	a {text-decoration: none; color:black}
</style>
</head>
<body>
<c:import url="/header"></c:import>
<div align="center" >
	<c:choose>
		<c:when test="${members.isEmpty() }">
			<h3>등록 후 이용하세요.</h3>
		</c:when>
		<c:otherwise>
			
			<table border=1>
				<tr>
					<th>아이디</th>
					<th>이름</th>
					<th>주소</th>
					<th>전화번호</th>
				</tr>
				<c:forEach var="member" items="${ members}">
							<tr>
								<td onclick="location.href='userInfo?id=${member.id }&currentPage=${currentPage }'">
									${member.id }
								</td>
								<td>${member.userName }</td>
								<td>${member.address }</td>
								<td>${member.mobile }</td>
							</tr>
						</c:forEach>
			</table>
			
			<div>${result }</div>
			
			<form action="memberInfo">
				<select name="select">
					<option value="">전체</option>
					<option value="id">아이디</option>
					<option value="mobile">전화번호</option>
				</select>
				<input type="text" name="search" />
				<input type="submit" value="검색" />
			</form>
		</c:otherwise>
	</c:choose>
	</div>
<c:import url="/footer"></c:import>
</body>
</html>
