package kr.co.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.member.model.dao.MemberDao;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping(value="/loginFrm")
	public String loginFrm() {
		return "member/login";
	}
}
