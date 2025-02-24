package kr.co.iei.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.board.service.MainPageService;
import kr.co.iei.group.model.vo.Group;

@Controller
@RequestMapping(value="/mainPage")
public class MainPageController {
	@Autowired
	private MainPageService mainPageService;
	
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
}
