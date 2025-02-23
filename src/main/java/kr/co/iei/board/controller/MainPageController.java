package kr.co.iei.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.board.service.MainPageService;

@Controller
@RequestMapping(value="/mainPage")
public class MainPageController {
	@Autowired
	private MainPageService mainPageService;
	
	@ResponseBody
	@GetMapping("/readCountGroup")
	public List readCountGroup(int start, int amount) {
		List groupList = mainPageService.readCountGroup(start,amount);
		return groupList;
	}
}
