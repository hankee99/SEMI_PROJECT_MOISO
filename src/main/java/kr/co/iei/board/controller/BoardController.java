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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.board.service.BoardService;
import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardComment;
import kr.co.iei.board.vo.BoardListData;
import kr.co.iei.member.model.vo.Member;
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
	
	//게시글 리스트
	@GetMapping(value="/boardList")
	public String BoardList(Model model, int reqPage, @SessionAttribute(required=false) Member member) {
		String memberNickname = null;
		if(member != null) {
			memberNickname = member.getMemberNickname();
		}
		List noticeList = boardService.selectBoardNoticeList(memberNickname);			
		int noticeCount = noticeList.size();
		if(reqPage == 1) {
			model.addAttribute("noticeList",noticeList);
		}
		BoardListData bld = boardService.selectBoardList(reqPage, memberNickname, noticeCount);	
		model.addAttribute("boardList",bld.getList());
		model.addAttribute("pageNavi",bld.getPageNavi());
		return "board/boardlist";
	}
	
	//게시글
	@GetMapping(value="/board")
	public String board(int boardNo, Model model, String check, @SessionAttribute(required=false) Member member) {
		String memberNickname = null;
		if(member != null) {
			memberNickname = member.getMemberNickname();
		}
		Board b = boardService.selectOneBoard(boardNo, memberNickname, check);
		
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
	
	//게시글 쓰기
	@GetMapping(value="/boardWriteFrm")
	public String boardWriteFrm(Model model) {
		List category = boardService.selectCategory();
		model.addAttribute("c",category);
		return "board/boardWriteFrm";
	}
	
	//게시글 쓰기
	@PostMapping(value="/boardWrite")
	public String boardWrite(Board b, MultipartFile boardPhoto, Model model) {
		if(!boardPhoto.isEmpty()) {
			String savepath = root+"/moisoPhoto/";
			String filepath = fileUtils.upload(savepath, boardPhoto);
			b.setBoardPicture(filepath);
		}
		int result = boardService.insertBoard(b);
		System.out.println("result");
		
		model.addAttribute("title", "작성 완료");
		model.addAttribute("text", "게시글이 등록되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/board/boardList?reqPage=1");
		return "common/msg";
	}

	//게시글 수정
	@GetMapping(value="/boardUpdateFrm")
	public String boardUpdateFrm(int boardNo,Model model, String memberNickname) {
		List category = boardService.selectCategory();
		Board b = boardService.selectOneBoard(boardNo, memberNickname, "1");
		
		model.addAttribute("c",category);
		model.addAttribute("b", b);
		return "board/boardUpdateFrm";
	}
	
	//게시글 수정
	@PostMapping(value="/boardUpdate")
	public String boardUpdate(Board b, MultipartFile boardPhoto, Model model) {
		if(!boardPhoto.isEmpty()) {
			String savepath = root+"/moisoPhoto/";
			String filepath = fileUtils.upload(savepath, boardPhoto);
			b.setBoardPicture(filepath);
		}
		int result = boardService.updateBoard(b);
		
		model.addAttribute("title", "수정 완료");
		model.addAttribute("text", "게시글이 수정되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/board/boardList?reqPage=1");
		return "common/msg";
	}
	
	//게시글 삭제
	@GetMapping(value="/deleteBoard")
	public String deleteBoard(int boardNo, Model model) {
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
		
	//댓글 입력
	@PostMapping(value="/insertComment")
	public String insertComment(BoardComment bc) {
		int result = boardService.insertBoardComment(bc);
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
	
	//댓글 수정
	@PostMapping(value="/updateComment")
	public String updateComment(BoardComment bc) {
		int result = boardService.updateBoardComment(bc);
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
	
	//댓글 삭제
	@GetMapping(value="deleteComment")
	public String deleteComment(BoardComment bc) {
		int result = boardService.deleteComment(bc.getCommentNo());
		return "redirect:/board/board?boardNo="+bc.getBoardNo();
	}
	
	//게시글 좋아요
	@ResponseBody
	@PostMapping(value="/likepush")
	public int likepush(Board b, @SessionAttribute Member member) {
		int result = boardService.likepush(b,member.getMemberNickname());
		return result;
	}
	//댓글 좋아요
	@ResponseBody
	@PostMapping(value="/likepushComment")
	public int likepushComment(BoardComment bc, @SessionAttribute Member member) {
		int result = boardService.likepushComment(bc,member.getMemberNickname());
		return result;
	}
	
}
