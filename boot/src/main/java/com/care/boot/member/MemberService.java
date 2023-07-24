package com.care.boot.member;

import java.util.ArrayList;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.care.boot.common.PageService;
import jakarta.servlet.http.HttpSession;


@Service
public class MemberService {
	@Autowired private MemberMapper memberMapper;
	@Autowired private HttpSession session;
	
	public String loginProc(MemberDTO member) {
		if(member.getId() == null || member.getId().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		
		if(member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		MemberDTO result = memberMapper.loginProc(member.getId());
		if(result != null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
			
			if(bpe.matches(member.getPw(), result.getPw())) {
				session.setAttribute("id", result.getId());
				session.setAttribute("userName", result.getUserName());
				session.setAttribute("address", result.getAddress());
				session.setAttribute("mobile", result.getMobile());
				return "로그인 성공";
			}
		}
		
		return "로그인을 재시도 하세요.";
	}

	public String registerProc(MemberDTO member, String confirm) {
		if(member.getId() == null || member.getId().isEmpty()) {
			return "아이디를 입력하세요.";
		}
		
		if(member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		if(member.getPw().equals(confirm) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		
		if(member.getUserName() == null || member.getUserName().isEmpty()) {
			return "이름을 입력하세요.";
		}
		/*
		System.out.println("암호화된 비밀번호 : " + cryptPassword);
		System.out.println("암호화된 비밀번호 길이 : " + cryptPassword.length());
		System.out.println("평문 비밀번호 : " + member.getPw());
		
		 암호화된 비밀번호 : $2a$10$.EOushkIDT8Gnb33i6NOSuS32ymKWipIvLCKeVlwGR20UWJYRYWEm
		 암호화된 비밀번호 길이 : 60
		 평문 비밀번호 : 1111
		 ALTER TABLE session_quiz MODIFY pw varchar2(60);
		 */
		MemberDTO result = memberMapper.loginProc(member.getId());
		if(result == null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
			String cryptPassword = bpe.encode(member.getPw());
			member.setPw(cryptPassword);
			memberMapper.registerProc(member);
			return "회원 등록 완료";
		}
		
		return "이미 가입된 아이디 입니다.";
	}

	public void memberInfo(String cp, String select, String search, Model model) {
		if(select == null){
			select = "";
		}
		
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}
		
		int pageBlock = 3; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호
	
		ArrayList<MemberDTO> members = memberMapper.memberInfo(begin, end, select, search);
		int totalCount = memberMapper.count(select, search);
		String url = "memberInfo?select="+select+"&search="+search+"&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);
		
		model.addAttribute("members", members);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	public MemberDTO userInfo(String id) {
		if(id == null || id.isEmpty()) {
			return null;
		}
		
		String sessionId = (String)session.getAttribute("id");
		if(sessionId.equals(id) == false && sessionId.equals("admin") == false)
			return null;
		
//		MemberDTO result = memberMapper.loginProc(id);
		return memberMapper.loginProc(id);
	}

	public String updateProc(MemberDTO member, String confirm) {
		if(member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		if(member.getPw().equals(confirm) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		
		if(member.getUserName() == null || member.getUserName().isEmpty()) {
			return "이름을 입력하세요.";
		}
		
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);
		
		int result = memberMapper.updateProc(member);
		if(result == 1)
			return "회원 정보 수정 완료";
		return "회원 정보 수정 실패";
	}

	public String deleteProc(String id, String pw, String confirmPw) {
		if(pw == null || pw.isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		if(pw.equals(confirmPw) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		MemberDTO member = memberMapper.loginProc(id);
		if(member != null && bpe.matches(pw, member.getPw())) {
			memberMapper.delete(id);
			return "회원 정보 삭제 완료";
		}
		return "비밀번호를 확인 후 다시 시도하세요.";
	}

	@Autowired private MailService mailService;
	public String sendEmail(String email) {
		if(email == null || email.isEmpty())
			return "이메일을 확인 후 다시 입력하세요.";
		Random r = new Random();
		// 1,000,000 
		// 100 000 , 001234
	
		content = String.format("%06d", r.nextInt(1000000));
		System.out.println("인증번호 : " + content);
		String msg = mailService.sendMail(email, "인증번호가 도착했습니다", content);
		if(msg.equals("입력한 이메일에서 인증번호를 확인하세요.") == false) {
			content = "";
		}
		return msg;
	}
	private String content;
	
	public String sendAuth(String auth) {
		if(auth == null || auth.isEmpty())
			return "인증번호를 입력 후 다시 시도하세요.";
		
		if(content == null || content.isEmpty())
			return "인증번호를 입력 후 다시 시도하세요.";
		
		if(auth.equals(content)) {
			return "인증 성공";
		}
		
		return "인증 실패";
	}

	public String select(String id) {
		String userid = memberMapper.select(id);
		if(userid != null) {
			return"userid";
		}
		else {
			return "정보없음";
		}
	}
}




