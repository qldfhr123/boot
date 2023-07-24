package com.care.boot.board;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired private BoardService service;
	@Autowired private HttpSession session;
	
	@RequestMapping("boardForm")
	public String boardForm(
			@RequestParam(value="currentPage", required = false)String cp,
			Model model) {
		System.out.println("호출되는거야?");
		service.boardForm(cp, model);
		return "board/boardForm";
	}
	@GetMapping("boardWrite")
	public String boardWrite() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "board/boardWrite";
	}
	
	@PostMapping("boardWriteProc")
	public String boardWriteProc(Model model, MultipartHttpServletRequest multi) {
		String msg = service.boardWriteProc(multi);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("게시글 작성 완료"))
			return "redirect:boardForm";
		
		model.addAttribute("msg", msg);
		return "board/boardWrite";
	}
	
	@RequestMapping("boardContent")
	public String boardContent(
			@RequestParam(value="no", required = false)String n, Model model) {
		BoardDTO board = service.boardContent(n);
		if(board == null) {
			System.out.println("boardContent 게시글 번호 : " + n);
			return "redirect:boardForm";
		}
		model.addAttribute("board", board);
		return "board/boardContent";
	}

	@RequestMapping("boardDownload")
	public void boardDownload(
			@RequestParam(value="no", required = false)String n, 
			HttpServletResponse res) throws IOException{
		
		service.boardDownload(n, res);
		
//		boolean result = service.boardDownload(n, res);
//		if(result == false)
//			return "redirect:boardForm";
//	
//		return "forward:boardContent"; 
	}
	
	@GetMapping("boardModify")
	public String boardModify(
			@RequestParam(value="no", required = false)String n,
			Model model) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		BoardDTO board = service.boardModify(n);
		if(board == null)
			return "redirect:boardForm";
		
		if(id.equals(board.getId()) == false)
			return "redirect:boardContent?no="+n;
	
		model.addAttribute("board", board);
		return "board/boardModify";
	}
	
	@PostMapping("boardModifyProc")
	public String boardModifyProc(BoardDTO board) {
//		System.out.println("no : " + board.getNo());
//		System.out.println("title : " + board.getTitle());
//		System.out.println("content : " + board.getContent());
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		String msg = service.boardModifyProc(board);
		if(msg.equals("게시글 수정 완료"))
			return "redirect:boardContent?no="+board.getNo();
		
		return "redirect:boardModify?no="+board.getNo();
	}

	@RequestMapping("boardDeleteProc")
	public String boardDeleteProc(@RequestParam(value="no", required = false)String n) {
		String msg = service.boardDeleteProc(n);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("작성자만 삭제 할 수 있습니다.")) {
			System.out.println("게시글 번호 : " + n);
			return "forward:boardContent";
		}
		
		return "redirect:boardForm";
	}
}























