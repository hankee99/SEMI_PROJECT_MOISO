package kr.co.iei.board.controller;

import java.io.File;
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
import kr.co.iei.board.vo.BoardComment;
import kr.co.iei.board.vo.BoardListData;
import kr.co.iei.notice.model.vo.Notice;
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
	public String board(int boardNo, Model model, String memberNickname) {
		Board b = boardService.selectOneBoard(boardNo, memberNickname);
		
		if(b == null) {
			model.addAttribute("title", "게시글 조회 실패");
			model.addAttribute("text", "존재하지 않는 게시물 입니다.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/board/boardList?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("b", b);
			return "board/board";
		}
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
		//사진 삭제
		int result = boardService.insertBoard(b);
		System.out.println("result");
		
		model.addAttribute("title", "작성 완료");
		model.addAttribute("text", "글쓰기가 등록되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/board/boardList?reqPage=1");
		return "common/msg";
	}

	@GetMapping(value="/updateFrmBoard")
	public String updateFrmBoard(int boardNo,Model model, String memberNicknam) {
		Board n = boardService.selectOneBoard(boardNo, memberNicknam);
		model.addAttribute("n", n);
		return "notice/updateFrm";
	}
	
	@GetMapping(value="/deleteBoard")
	public String deleteBoard(int boardNo, Model modele) {
		Board b = boardService.deleteBoard(boardNo);
		String savepath = root+"/moisoPhoto/";
		
		//사진삭제
		File delFile = new File(savepath+b.getBoardPicture());
		delFile.delete();
		
		model.addAttribute("title","게시글 삭제 완료");
		model.addAttribute("text","게시글이 삭제되었습니다.");
		model.addAttribute("icon","success");
		model.addAttribute("loc","/board/boardList?reqPage=1");
		return "common/msg";
	}
	
	
	
	@PostMapping(value="/insertComment")
	public String insertComment(BoardComment bc) {
		int result = boardService.insertBoardComment(bc);
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
	
	@PostMapping(value="/updateComment")
	public String updateComment(BoardComment bc) {
		int result = boardService.updateBoardComment(bc);
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
	
	@GetMapping(value="deleteComment")
	public String deleteComment(BoardComment bc) {
		int result = boardService.deleteComment(bc.getCommentNo());
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
}
