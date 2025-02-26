package kr.co.iei.member.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.GroupList;
import kr.co.iei.member.model.vo.ManagerPageStat;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.MypaymentData;
import kr.co.iei.util.EmailSender;
import kr.co.iei.util.FileUtils;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EmailSender emailSender;
	
	@Value(value="${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
	
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
	
	@ResponseBody
	@PostMapping(value="/idSelect")
	public List idSelect(String memberEmail) {
		List list = memberService.idSelect(memberEmail);
		return list;
	}
	
	@ResponseBody
	@GetMapping(value="/sendCode")
	public String sendCode(String receiver) {
		//인증메일 제목 생성
		String emailTitle = "모이소 인증메일입니다.";
		//인증메일용 인증코드 생성
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<6;i++) {
			//숫자(0~9) : r.nextInt(10)
			//대문자(A~Z) : r.nextInt(26)+65
			//소문자(a~z) : r.nextInt(26)+97
			
			int flag = r.nextInt(3);	//0,1,2 -> 숫자,대문자,소문자 어떤거 사용할지 랜덤으로 결정
			
			if(flag==0) {
				int randomCode = r.nextInt(10);
				sb.append(randomCode);
			}else if(flag ==1) {
				char randomCode = (char)(r.nextInt(26)+65);
				sb.append(randomCode);
			}else {
				char randomCode = (char)(r.nextInt(26)+97);
				sb.append(randomCode);
			}
		}
		
		String emailContent = "<h1>안녕하세요. 모이소입니다<h1>"
								+"<h3>인증번호는"
								+"[<span style='color:red';>"
								+sb.toString()
								+"</span>]"
								+"입니다.</h3>";
		
		emailSender.sendMail(emailTitle, receiver, emailContent);
		return sb.toString();
	}
	
	@GetMapping(value="/mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	@GetMapping(value="/updateMypage")
	public String updateMypage() {
		return "member/updateMypage";
	}
	
	//나의 그룹 조회
	@GetMapping(value="/mygroup")
	public String mygroup(Model model, @SessionAttribute(required=false) Member member) {
		int memberNo = 0;
		if(member != null) {
			memberNo = member.getMemberNo();
		}
		GroupList list = memberService.mygroup(memberNo);
		model.addAttribute("mygroup", list.getMygroup());
		model.addAttribute("myHostGroup", list.getMyHostGroup());
		return "member/mygroup";
	}
	
	@GetMapping(value="/managerPage")
	public String managerPage(Model model) {
		ManagerPageStat mps = memberService.managerPageStat();
		model.addAttribute("list", mps.getList());		//오퍼리스트
		model.addAttribute("ts", mps.getTotalStat());	//토탈스탯 객체
		return "member/managerPage";
	}	
	
	
	@PostMapping(value="/update")
	private String mypageUpdate(Member m, MultipartFile upfile, Model model, @SessionAttribute Member member, String defaultProfileUse) {
		String memberId = member.getMemberId();
		m.setMemberId(memberId);
		String profileImg = member.getProfileImg();
		if(defaultProfileUse != null) {
			//check가 defaultProfileUse(null아님)면, 기본이미지니까 데이터에 null을 넣어줘야 하고,
			m.setProfileImg(null);
		}else {
			//null이면 기본이미지 사용 안하니까 기존 사진이거나 새로운 사진이거나!
			if (!upfile.isEmpty()) {  
				String savepath = root + "/profile/";
				
				String filename = upfile.getOriginalFilename();
				String filepath = fileUtils.upload(savepath, upfile);
				// 이미지파일경로를 loginUser에 저장	        
				m.setProfileImg(filepath);
			}else {
				m.setProfileImg(profileImg);
			}
		}	 
		int result = memberService.updateMypage(m);
		if(result > 0) {
			member.setMemberAddr(m.getMemberAddr());
			if(!m.getMemberGender().equals("")) {
				member.setMemberGender(m.getMemberGender());
			}
			member.setMemberIntro(m.getMemberIntro());
			member.setMemberMbti(m.getMemberMbti());
			member.setMemberNickname(m.getMemberNickname());
			member.setMemberPhone(m.getMemberPhone());
			member.setMemberPw(m.getMemberPw());
			member.setProfileImg(m.getProfileImg());
			model.addAttribute("title", "작성완료");
			model.addAttribute("text", "마이페이지가 수정되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/member/mypage");
			return "common/msg";	
		}else {
			model.addAttribute("title", "수정실패");
			model.addAttribute("text", "다시입력해보세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/member/mypage");
			return "common/msg";
		}	
	}
	
	
	
	@ResponseBody
	@GetMapping(value="/sido")
	public List sido() {
		List list = memberService.selectSido();
		return list;
	}
	
	@ResponseBody
	@GetMapping(value="/sigungu")
	public List sigungu(String sido) {
		List list = memberService.selectSigungu(sido);
		return list;
	}
	
	
	@GetMapping(value="/groupLevelManage")
	public String hostLevelMng(Model model) {
		List list = memberService.selectGroupMemebr();
		model.addAttribute("list", list);
		return "member/groupLevelManage";
	}
	
	@RequestMapping(value="/loginMsg")
	public String loginMsg(Model model) {
		model.addAttribute("title", "로그인 확인");
		model.addAttribute("text", "로그인 후 이용 가능합니다");
		model.addAttribute("icon", "info");
		model.addAttribute("loc", "/member/loginFrm");
		return "common/msg";
	}
	
	
	
	
}
























