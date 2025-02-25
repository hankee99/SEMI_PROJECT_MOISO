package kr.co.iei.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.board.service.MainPageService;
import kr.co.iei.group.model.service.GroupService;
import kr.co.iei.group.model.vo.Group;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value="/mainPage")
public class MainPageController {
	@Autowired
	private MainPageService mainPageService;
	@Autowired
	private GroupService groupService;
	
	@ResponseBody
	@GetMapping("/readCountTotalCount")
	public int selectReadTotalCount() {
		int totalCount = mainPageService.selectReadCountTotalCount();
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("/readCountGroup")
	public List selectReadCountGroup(int start, int amount) {
		List list = mainPageService.selectReadCountGroup(start,amount);
		return list;
	}
	
	
	@ResponseBody
	@GetMapping("/dateTotalCount")
	public int selectDateTotalCount(String date) {
		int totalCount = mainPageService.selectDateTotalCount(date);
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("/dateGroup")
	public List selectDateGroup(String date, int dateStart, int amount) {
		List list = mainPageService.selectDateGroup(date, dateStart,amount);
		System.out.println(list);
		return list;
	}
	
	@ResponseBody
	@GetMapping("/recentGroup")
	public List selectRecentGroup(int recentStart, int amount, HttpSession session) {
		List list = null;
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member"); 
			int memberNo = member.getMemberNo();
			list = mainPageService.selectRecentGroup(memberNo,recentStart,amount);
		}
		
		System.out.println(list);
		return list;
	}
	
	
	@GetMapping(value="toGroupSearchList")
	public String toGroupSearchList(String groupSearch, Model model) {
		model.addAttribute("groupSearch",groupSearch);
		return "groupSearchList";
	}
	
	//모임 검색 리스트
	@ResponseBody
	@GetMapping(value="/groupSearchList")
	public List groupSearchList(String groupSearch, int start, int amount) {
		List groupSearchList = mainPageService.selectGroupSearchList(groupSearch,start,amount);
		return groupSearchList;
	}
}
