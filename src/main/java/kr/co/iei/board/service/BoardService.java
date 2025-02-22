package kr.co.iei.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.dao.BoardDao;
import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardComment;
import kr.co.iei.board.vo.BoardListData;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public List selectCategory() {
		List list = boardDao.selectBoardCategory();
		return list;
	}
	
	public int insertBoard(Board b) {
		int result = boardDao.insertBoard(b);
		return result;
	}

	public BoardListData selectBoardList(int reqPage, String memberNickname, int noticeCount) {
		//reqPage : 사용자가 요청한 페이지 번호
		//한 페이지에 보여줄 게시물 수(지정) : 10개
		int numPerPage = 10;
		if(noticeCount != 0) {
			if(reqPage == 1 && noticeCount % 2 != 0) {
				numPerPage = 9;
			}
		}
		
		//쿼리문은 변경되지 않고 조회의 시작값과 끝값만 변경(start, end)
		//사용자가 요청한 페이지에 따라서 게시물의 시작번호와 끝번호가 변경 -> 계산
		//reqPage == 1 -> start = 1		/ end = 10
		//reqPage == 2 -> start = 11	/ end = 20
		//reqPage == 3 -> start = 21	/ end = 30
		//reqPage == 4 -> start = 31	/ end = 40
		int end = reqPage * numPerPage;//9, 20
		int start = end - numPerPage + 1;//0, 11	
		if(noticeCount % 2 != 0 && reqPage != 1) {
			System.out.println(end + " " + start);
			end = end - 1;//19 //29
			start = start - 1;//10 //20
			System.out.println(end + " " + start);
		}
//		if(reqPage == 2) {
//			end = 19;
//			start = 10;
//		}
		//해당 요청 페이지의 게시물을 조회 
		List list = boardDao.selectBoardList(start,end,memberNickname);
		
		//페이지 네비게이션(사용자가 클릭해서 다른 페이지를 요청할 수 있도록 하는 요소)
		//페이지 네비게이션은 Service에서 만드는 이유 -> 총게시물수, reqPage같은 데이터를 이용해서 만들어야하기 때문에
		//전체페이지 수를 계산 -> 총 게시물 수, 페이지당 게시물 수를 이용해서 연산
		//총 게시물 수 조회
		//select count(*) from notice
		int totalCount = boardDao.selectBoardTotalCount();
		/*
		int totalPage = 0;
		if(totalCount/numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage + 1;
		}
		*/
		int totalPage = totalCount / 10;
		if(totalCount%10 != 0) {
			totalPage += 1;
		}
		
		//페이지 네비게이션 제작 시작
		//페이지네비 길이 지정
		int pageNaviSize = 5;
		//페이지네비의 시작번호를 지정
		//reqPage 1~5 : 1 2 3 4 5			(0 ~ 4)/5	-> 0 x 5 -> 0 + 1 -> 1
		//reqPage 6~10 : 6 7 8 9 10			(5 ~ 9)/5	-> 1 x 5 -> 5 + 1 -> 6
		//reqPage 11~15 : 11 12 13 14 15	(10 ~ 14)/5	-> 2 x 5 -> 10 + 1 -> 11
		//...
		int pageNo = ((reqPage - 1)/pageNaviSize)*pageNaviSize + 1;
		
		//페이지네비 html을 생성
		String pageNavi = "<ul class=\"pagination justify-content-center\">";
		//이전 버튼
		if(pageNo != 1) {
			pageNavi += "<li class=\"page-item \">";
			pageNavi += "<a class=\"page-link\" href='/board/boardList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "Previous</a></li>";
		}else {
			pageNavi += "<li class=\"page-item disabled\">";
			pageNavi += "<a class=\"page-link\" href='/board/boardList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "Previous</a></li>";
		}
		//페이지이동
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li class=\"page-item\">";
				pageNavi += "<a class=\"page-link\" href='/board/boardList?reqPage="+pageNo+"'>";
			
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			//페이징을 만들다가 최종페이지를 출력했으면 더이상 반복하지 않고 반복문을 종료
			if(pageNo > totalPage) {
				break;
			}
		}
		//다음버튼
		if(pageNo <= totalPage) {
			pageNavi += "<li class=\"page-item\">";
			pageNavi += "<a class=\"page-link\" href='/board/boardList?reqPage="+pageNo+"'>";
			pageNavi += "Next</a></li>";
		}else {
			pageNavi += "<li class=\"page-item disabled\">";
			pageNavi += "<a class=\"page-link\" href='/board/boardList?reqPage="+pageNo+"'>";
			pageNavi += "Next</a></li>";
		}
		pageNavi += "</ul>";
		
		//service가 가지고 있는 것 중에 되돌려줘야할 것 -> 페이지 너비, list
		//java의 메소드는 1개의 자료형만 리턴이 가능 -> 2개를 되돌려줘야 함 String, List
		//List와 문자열을 속성으로 가지고 있는 객체 생성
		BoardListData bld = new BoardListData(list, pageNavi);
		return bld;
		
	}

	public List selectBoardNoticeList(String memberNickname) {
		List list = boardDao.selectBoardNoticeList(memberNickname);
		return list;
	}
	
	public Board selectOneBoard(int boardNo,String memberNickname, String check) {
		Board b = boardDao.selectOneBoard(boardNo, memberNickname);
		if(b != null){
			if(check == null) {
				int result = boardDao.updateReadCount(boardNo);
			}
		}
			
		//댓글조회
		List commentList = boardDao.selectBoardCommentList(boardNo, memberNickname);
		b.setCommentList(commentList);
		
		//대댓글조회
		List reCommentList = boardDao.selectBoardReCommentList(boardNo, memberNickname);
		b.setReCommentList(reCommentList);
		
		return b;
	}
	
	public int updateBoard(Board b) {
		int result = boardDao.updateBoard(b);
		return result;
	}
	
	public Board deleteBoard(int boardNo) {
		Board b = boardDao.selectOneBoard(boardNo, null);
		int result = boardDao.deleteBoard(boardNo);
		return b;
	}

	public int insertBoardComment(BoardComment bc) {
		int result = boardDao.insertBoardComment(bc);
		return result;
	}

	public int updateBoardComment(BoardComment bc) {
		int result = boardDao.updateBoardComment(bc);
		return result;
	}
	
	public int deleteComment(int commentNo) {
		int result = boardDao.deleteComment(commentNo);
		return result;
	}

	public int likepush(Board b, String memberNickname) {

		if(b.getIsLike() == 0) {
			//현재값이 0 -> 좋아요를 누르겠다 -> insert
			int result = boardDao.insertBoardLike(b.getBoardNo(), memberNickname);
		}else {
			//현재값이 1 -> 좋아요를 취소하겠다 -> delete
			int result = boardDao.deleteBoardLike(b.getBoardNo(), memberNickname);
		}
		//좋아요, 좋아요 취소 로직을 수행하고나면 현재 좋아요 갯수 조회해서 리턴
		int likeCount = boardDao.selectBoardLikeCount(b.getBoardNo());
		return likeCount;
	}

	public int likepushComment(BoardComment bc, String memberNickname) {

		if(bc.getIsLike() == 0) {
			//현재값이 0 -> 좋아요를 누르겠다 -> insert
			int result = boardDao.insertCommentLike(bc.getCommentNo(), memberNickname);
		}else {
			//현재값이 1 -> 좋아요를 취소하겠다 -> delete
			int result = boardDao.deleteCommentLike(bc.getCommentNo(), memberNickname);
		}
		//좋아요, 좋아요 취소 로직을 수행하고나면 현재 좋아요 갯수 조회해서 리턴
		int likeCount = boardDao.selectCommentLikeCount(bc.getCommentNo());
		return likeCount;
	}


}
