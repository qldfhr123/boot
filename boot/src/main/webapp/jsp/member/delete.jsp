<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete</title>
</head>
<body>
	<c:import url="/header"></c:import>
	<div align="center">
		<h1>회원 탈퇴</h1>
		<div align ="center">
			${msg }
		</div>
		<form action="deleteProc" method="post">
			<input type="text" name="id" id="id" value="${sessionScope.id }"><br> <%--el 표현방식으로 지정 --%>
			<input type="password" name="pw" id="pw" placeholder="비밀번호"><br>
			<input type="password" name="confirmPw" id="confirmPw" placeholder="비밀번호 확인"><br>
			<input type="submit" value="회원 탈퇴" >
			<input type="button" value="취소" onclick="location.href='index'">
		</form>

</div>
<c:import url="/footer"></c:import>
</body>
</html>







