package kr.co.iei.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.board.service.BoardService;
import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardListData;
import kr.co.iei.util.FileUtils;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Value(value="${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping(value="/boardList")
	public String BoardList(Model model, int reqPage) {
		BoardListData bld = boardService.selectBoardList(reqPage);	
		model.addAttribute("boardList",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		return "board/boardlist";
	}
	
	@GetMapping(value="/board")
	public String board() {
		return "board/board";
	}
	
	@GetMapping(value="/boardWriteFrm")
	public String boardWriteFrm(Model model) {
		List category = boardService.selectCategory();
		model.addAttribute("c",category);
		return "board/boardWriteFrm";
	}
	
	@PostMapping(value="/boardWrite")
	public String boardWrite(Board b, MultipartFile boardPhoto, Model model) {
		if(!boardPhoto.isEmpty()) {
			String savepath = root+"/moisoPhoto/";
			String filepath = fileUtils.upload(savepath, boardPhoto);
			b.setBoardPicture(filepath);
		}
		int result = boardService.insertBoard(b);
		System.out.println("result");
		
		return "redirect:/board/boardList?reqPage=1";
	}
}
