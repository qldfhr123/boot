package com.care.boot.board;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.common.PageService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class BoardService {
	@Autowired private BoardMapper boardMapper;
	@Autowired private HttpSession session;
	
	public void boardForm(String cp, Model model) {
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 3; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
	
		ArrayList<BoardDTO> boards = boardMapper.boardForm(begin, end);
		int totalCount = boardMapper.count();
		String url = "boardForm?currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);
		
		model.addAttribute("boards", boards);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	public String boardWriteProc(MultipartHttpServletRequest multi) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		
		BoardDTO board = new BoardDTO();
		board.setId(id);
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setWriteDate(sdf.format(new Date()));
		board.setFileName("");
		
		if(board.getTitle() == null || board.getTitle().isEmpty()) {
			return "제목을 입력하세요.";
		}
		
		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if(file.getSize() != 0) {
			// 파일의 중복을 해결하기 위해 시간의 데이터를 파일이름으로 구성함.
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			board.setFileName(fileName);
			
			// 업로드 파일 저장 경로
			//sudo chown -RH tomcat: /opt/tomcat/tomcat-10/webapps/upload/
			String fileLocation = "/opt/tomcat/tomcat-10/webapps/upload/";
			File save = new File(fileLocation + fileName);
			
			try {
				// 서버가 저장한 업로드 파일은 임시저장경로에 있는데 개발자가 원하는 경로로 이동
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		boardMapper.boardWriteProc(board);
		return "게시글 작성 완료";
	}

	public BoardDTO boardContent(String n) {
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
		BoardDTO board = boardMapper.boardContent(no);
		if(board == null)
			return null;
		
		board.setHits(board.getHits()+1);
		incHit(board.getNo());
		
//		String tmp = "123123-test-v01-123.txt";
//		String[] tmps = tmp.split("-", 2);
//		for(String t : tmps)
//			System.out.println(t);
		if(board.getFileName() != null && board.getFileName().isEmpty() == false) {
			//오라클db은 빈문자는 null저장하는데 mariaDB는 빈문자 그대로 저장한다
			String fn = board.getFileName();
			String[] fileName = fn.split("-", 2);
			board.setFileName(fileName[1]);
		}
		return board;
	}
	
	public void incHit(int no) {
		boardMapper.incHit(no);
	}

	public boolean boardDownload(String n, HttpServletResponse res) {
		int no = 0;
		
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return false;
		}
		
		String fileName = boardMapper.boardDownload(no);
		if(fileName == null)
			return false;
		
		String location = "/opt/tomcat/tomcat-10/webapps/upload/";
		File file = new File(location + fileName);
		
		try {
			String[] original = fileName.split("-", 2);
			res.setHeader("Content-Disposition", 
					"attachment;filename=" + URLEncoder.encode(original[1], "UTF-8"));
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, res.getOutputStream());
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public BoardDTO boardModify(String n) {
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return null;
		}
		
		BoardDTO board = boardMapper.boardContent(no);
		if(board == null)
			return null;

		if(board.getFileName() != null ) {
			String fn = board.getFileName();
			String[] fileName = fn.split("-", 2);
			board.setFileName(fileName[1]);
		}
		return board;
	}
	
	public String boardModifyProc(BoardDTO board) {
		if(board.getTitle() == null || board.getTitle().isEmpty())
			return "제목을 입력하세요.";
		
		boardMapper.boardModifyProc(board);
		return "게시글 수정 완료";
	}

	public String boardDeleteProc(String n) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		
		int no = 0;
		try{
			no = Integer.parseInt(n);
		}catch(Exception e){
			return "게시글 번호에 문제가 생겼습니다.";
		}
		
		BoardDTO board = boardMapper.boardContent(no);
		if(board == null)
			return "게시글 번호에 문제가 생겼습니다.";
		
		if(id.equals(board.getId()) == false)
			return "작성자만 삭제 할 수 있습니다.";
		
		boardMapper.boardDeleteProc(no);
		
		String path = "/opt/tomcat/tomcat-10/webapps/upload/" + board.getFileName();
		File file = new File(path);
		if(file.exists() == true) {
			file.delete();
		}
		
		return "게시글 삭제 완료";
	}
}









