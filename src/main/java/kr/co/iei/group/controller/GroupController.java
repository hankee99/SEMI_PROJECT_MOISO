package kr.co.iei.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GroupController {
	@GetMapping(value="/groupList")
	public String groupList() {
		return "group/groupList";
	}
	
}
