package kr.co.iei.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.board.service.BoardService;
import kr.co.iei.board.vo.Board;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value="/boardList")
	public String boardList() {
		return "board/boardList";
	}
	
	@GetMapping(value="/board")
	public String board() {
		return "board/board";
	}
	
	@GetMapping(value="/boardWriteFrm")
	public String boardWrtieFrm() {
		return "board/boardWriteFrm";
	}
	
	@PostMapping(value="/boardWrite")
	public String boardWrtie(Board b) {
		int result = boardService.insertBoard(b);
		System.out.println("result");
		if(result == 1) {
			System.out.println("완료");
		}else {
			System.out.println("실패");
		}
		return "board/board";
	}
}
