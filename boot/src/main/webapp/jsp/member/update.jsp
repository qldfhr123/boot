<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<c:import url="/header"></c:import>

<div align="center">
	<h1>회원 수정</h1>
	<table ><tr><td>
	<form action="updateProc" method="post" id="f">
		<input type="text" name="id" id="id" value="${sessionScope.id }"><br>
		<input type="password" name="pw" id="pw" placeholder="변경할 비밀번호"><br>
		<input type="password" name="confirmPw" id="confirmPw" placeholder="비밀번호 확인"onchange="pwCheck()">
		<label id="label" ></label><br>
		<input type="text" name="userName" id="userName" value="${sessionScope.userName }"placeholder="이름"><br>
		<input type="text" name="addres" id="addres" value="${sessionScope.addres }"placeholder="주소"><br>
		<input type="text" name="mobile" id="mobile" value="${sessionScope.mobile }" placeholder="번호"/><br>
		<input type="button" value="회원 수정" onclick="updateButton()">
		<input type="button" value="취소" onclick="cancelButton();">
	</form>
	</td></tr></table>
</div>

<c:import url="/footer"></c:import>



