package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.co.iei.member.model.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
}
