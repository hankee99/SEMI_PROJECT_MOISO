package kr.co.iei.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping(value="/boardList")
	public String about() {
		return "board/boardList";
	}
}
