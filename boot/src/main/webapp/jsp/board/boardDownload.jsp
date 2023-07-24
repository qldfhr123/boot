<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String no = request.getParameter("no");
	String fileName = request.getParameter("fileName");
	
	if(id == null || id.isEmpty()){
		response.sendRedirect("boardContent.jsp?no="+no);
		return;
	}	
	if(fileName == null || fileName.isEmpty()){
		response.sendRedirect("boardContent.jsp?no="+no);
		return;
	}
	
	String saveDir = "C:\\javas\\upload\\" + id + "\\" + fileName;
	File f = new File(saveDir);
	if(f.exists() == false){
		response.sendRedirect("boardContent.jsp?no="+no);
		return;
	}
	
	/*
	response.getOutputStream(); 을 사용해서 파일의 데이터를 클라이언트에게 전달.
	하지만 jsp를 java로 변환 후 실행 시 java에서 response.getOutputStream(); 을 사용하기에 충돌이 발생함.
	
	out.clear();
	out = pageContext.pushBody();
	위 두 코드로 out 내장객체를 초기화 후 현재페이지(pageContext)에서만 사용할 out 객체를 생성해서 
	클라이언트에게 데이터 전송
	*/
	out.clear();
	out = pageContext.pushBody();
	OutputStream os = response.getOutputStream();
	
	response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	FileInputStream fis = new FileInputStream(f);
	byte[] b = new byte[1024];
	while(true){
		int currentSize = fis.read(b, 0, b.length);
		if(currentSize == -1)
			break;
		
		os.write(b, 0, currentSize);
	}
	
	fis.close();
%>








