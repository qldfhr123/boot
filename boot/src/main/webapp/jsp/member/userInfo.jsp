<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userInfo</title>
</head>
<body>
<c:import url="/header"></c:import>

		<div align="center">
			<h1>개인 정보</h1>
			
			아이디 : ${userInfo.id } <br> 
			비밀번호 : ${userInfo.pw }<br>
			이름 : ${userInfo.userName }<br>
			주소 : ${userInfo.address } <br>
			전화번호 : ${userInfo.mobile } <br><br>
			<button type="button" onclick="location.href='update'">회원 수정</button>
			<button type="button" onclick="location.href='delete'">회원 삭제</button>
		</div>	

<c:import url="/footer"></c:import>
</body>
</html>
