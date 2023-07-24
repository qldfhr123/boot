<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header"></c:import>
<div align="center">
	<h1>로그인</h1>
	<table ><tr><td>
	<form action="loginProc" method="post" id="f">
		<div align="center">
			<input type="text" name="id" placeholder="아이디" id="id"><br>
			<input type="password" name="pw" placeholder="비밀번호" id="pw"><br>
			<label id="label" ></label><br>
			<input type="button" value="로그인" onclick="loginCheck()">
			<input type="button" value="취소" onclick="location.href='index'"><br>
		</div>
		
		<c:if test="${msg == 'error'}">
		 <div align="center" style="color:red;"> 아이디 또는 비밀번호가 일치하지 않습니다.
		 </div>
		 </c:if>
		 <c:if test="${msg == 'idCerror'}">
		 <div align="center" style="color:red;"> 등록된 정보가없습니다.
		 </div>
		 </c:if>
	</form>
	</td></tr>
	
	<tr align="center">
		<td>
			<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&
			client_id=231dcfa0f6c9f5d6db11c3d33c9b755c&
			redirect_uri=http://localhost/kakaoLogin">	
				<img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg">
			</a>
		</td>
	</tr>
	
	
	</table>
</div>

<c:import url="/footer"></c:import>


