package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmailSender emailSender;
	
	@GetMapping(value="/loginFrm")
	public String loginFrm() {
		return "member/login";
	}
	
	@PostMapping(value="/login")
	public String login(Member m, Model model, HttpSession session) {
		Member member = memberService.selectOneMember(m);
		if(member == null) {
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("text", "아이디 또는 비밀번호를 확인하세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc","/member/loginFrm");
			return "common/msg";
		}else {
				session.setAttribute("member", member);
				return "redirect:/";
		}
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}
	
	@PostMapping(value="/join")
	public String join(Member m, Model model) {
		int result = memberService.inserMember(m);
			model.addAttribute("title", "회원가입 완료!");
			model.addAttribute("text", "어서오세용!!!!!");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/member/loginFrm");
			return "common/msg";
		
	}
	
	@GetMapping(value="/findAccountFrm")
	public String fineAccountFrm() {
		return "member/findAccount";
	}
	
	@ResponseBody
	@GetMapping(value="/ajaxCheckId")
	public boolean ajaxCheckId(String memberId) {
		Member m = memberService.selectOneMember(memberId);
		if(m == null){
		 	return true;
		 }else{
		 	return false;
		 } 
	}
	
	//메소드이름 같고, 파라미터도 문자열 하나로 같으면 오버로딩 불가능(같은 이름의 메소드 사용하려면 매개변수 타입이나 개수가 달라야 함)
	@ResponseBody
	@GetMapping(value="/ajaxCheckNickname")
	public boolean ajaxCheckNickname(String memberNickname) {
		Member m = memberService.selectOneMemberNickname(memberNickname);
		return m==null;
	}
	
	@PostMapping(value="/sendMail")
	public String sendMail(String emailTitle, String receiver, String emailContent) {
		System.out.println("제목: "+emailTitle);
		System.out.println("받는사람: "+receiver);
		System.out.println("내용: "+emailContent);
		
		emailSender.sendMail(emailTitle, receiver, emailContent);
		
		return "redirect:/member/findAccountFrm";
	}
}
























