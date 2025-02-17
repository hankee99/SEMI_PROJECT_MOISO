package kr.co.iei.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class BoardController {
	@GetMapping(value="/boardList")
	public String boardList() {
		return "board/boardList";
	}
	
	@GetMapping(value="/board")
	public String boardWrtie() {
		return "board/board";
	}
}
