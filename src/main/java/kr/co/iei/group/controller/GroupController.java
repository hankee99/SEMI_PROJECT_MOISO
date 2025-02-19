package kr.co.iei.group.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.group.model.service.GroupService;


@Controller
@RequestMapping(value="/group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	
	@GetMapping(value="/groupList")
	public String groupList(Model model) {
		List categoryList = groupService.selectCategoryList();
		
		model.addAttribute("categoryList", categoryList);
		
		return "group/groupList";
	}
	
	@ResponseBody
	@GetMapping("/totalCount")
	public int selectGroupTotalCount(int categoryNo) {
		int totalCount = groupService.selectGroupTotalCount(categoryNo);
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("/categoryGroup")
	public List categoryGroup(int categoryNo, int start, int amount) {
		List groupList = groupService.selectGroupList(categoryNo,start,amount);
		return groupList;
	}
	
	
}
