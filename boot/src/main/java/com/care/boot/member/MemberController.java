package com.care.boot.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired private MemberService service;
	@Autowired private KakaoService kakaoservice;
	@Autowired private HttpSession session;
	
	@RequestMapping("index")
	public String index() {
		return "member/index";
	}

	@RequestMapping("header")
	public String header() {
		return "default/header";
	}
	@RequestMapping("main")
	public String main() {
		return "default/main";
	}
	@RequestMapping("footer")
	public String footer() {
		return "default/footer";
	}
	/* http://localhost:8086/dbQuiz/login */
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("loginProc")
	public String loginProc(MemberDTO member, Model model, RedirectAttributes ra, String id) {
		String id_check = service.select(id);
		String result = service.loginProc(member);
	
		if(id_check == null || id_check.equals("정보없음")) {
			model.addAttribute("msg", "idCerror");
			return "member/login";
		}
		
		if(result == null || result.equals("로그인을 재시도 하세요.")) { //service 에서 반환자료를 string 선언하고 데이터를 받아온다 그것을 비교
			model.addAttribute("msg", "error");
			return "member/login";
		}
		if(result.equals("로그인 성공")) {
			return "redirect:index";
		} 
		return "member/login";
		
	}
	
	/* http://localhost:8086/dbQuiz/register */
	@GetMapping("register")
	public String register() {
		return "member/register";
	}
	
	@PostMapping("registerProc")
	public String registerProc(MemberDTO member, String confirm) {
		String result = service.registerProc(member, confirm);
		if(result.equals("회원 등록 완료")) {
			return "redirect:index";
		}
		return "member/register";
	}
	
	@RequestMapping("memberInfo")
	public String memberInfo(
			@RequestParam(value="currentPage", required = false)String cp,
			String select, String search, Model model) {
		service.memberInfo(cp, select, search, model);
		return "member/memberInfo";
	}
	
	@RequestMapping("userInfo")
	public String userInfo(String id, 
			@RequestParam(value="currentPage", required = false)String cp, 
			Model model) {
		
		if(session.getAttribute("id") == null ) {
			return "redirect:login";
		}
		MemberDTO member = service.userInfo(id);
		if(member == null) {
			return "redirect:memberInfo?currentPage="+cp;
		}
		model.addAttribute("userInfo", member);
		return "member/userInfo";
	}
	
	@RequestMapping("logout")
	public String logout() {
		session.invalidate();
		kakaoservice.unLink();
		return "forward:index";
	}
	
	/* http://localhost:8086/dbQuiz/update */
	@GetMapping("update")
	public String update() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "member/update";
	}
	@PostMapping("updateProc")
	public String updateProc(MemberDTO member, String confirm) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		member.setId(id);
		String result = service.updateProc(member, confirm);
		if(result.equals("회원 정보 수정 완료")) {
			return "forward:logout";
		}
		return "member/update";
	}
	
	
	/* http://localhost:8086/dbQuiz/delete */
	@GetMapping("delete")
	public String delete() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "member/delete";
	}
	
	@PostMapping("deleteProc")
	public String deleteProc(String pw, String confirmPw, Model model) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		String result = service.deleteProc(id, pw, confirmPw);
		model.addAttribute("msg", result);
		if(result.equals("회원 정보 삭제 완료")) {
			return "forward:logout";
		}
		return "member/delete";
	}
	
	@ResponseBody
	@PostMapping(value="sendEmail", produces="text/plan; charset=utf-8")
	public String sendEmail(@RequestBody(required = false)String email) {
		return service.sendEmail(email);
	}
	
	@ResponseBody
	@PostMapping(value="sendAuth", produces="text/plan; charset=utf-8")
	public String sendAuth(@RequestBody(required = false)String auth) {
		System.out.println("sendAuth()");
		
		return service.sendAuth(auth);
	}
	
	@Autowired private KakaoService kakao;
	@GetMapping("kakaoLogin")
	public String kakaoLogin(String code) {
		System.out.println("code:"+code); 	
		kakao.getAccessToken(code);
		kakao.getUserInfo();
		
		return "redirect:index";
	}
	
}





