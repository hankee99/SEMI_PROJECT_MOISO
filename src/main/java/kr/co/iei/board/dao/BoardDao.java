package kr.co.iei.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardComment;
import kr.co.iei.board.vo.BoardCommentRowMapper;
import kr.co.iei.board.vo.BoardRowMapper;
import kr.co.iei.group.model.vo.CategoryRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;
	@Autowired
	private CategoryRowMapper categoryRowMapper;

	//글쓰기
	public int insertBoard(Board b) {
		String query = "insert into board values(board_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd pm\" \"hh:mi\"'),0,?,?,?)";
		Object[] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardPicture(),b.getMemberNickname(),b.getCategoryName()};
		int result = jdbc.update(query,params);
		return result;
	}
	//카테고리 불러오기
	public List selectBoardCategory() {
		String query = "select * from category";
		List list = jdbc.query(query,categoryRowMapper);
		return list;
	}

	//게시글리스트
	public List selectBoardList(int start, int end, String memberNickname) {
		System.out.println(start);
		System.out.println(end);
		System.out.println(memberNickname);
		String query = "select * \r\n"
				+ "from (select rownum as rnum, b.*,\r\n"
				+ "    (select count(*) from board_like where board_no = b.board_no) like_count,\r\n"
				+ "    (select count(*) from board_like where board_no = b.board_no and member_nickname = ?) is_like,\r\n"
				+ "    (select count(*) from board_comment where board_no = b.board_no) comment_count\r\n"
				+ "from (select * from board order by board_no desc)b) where rnum between ? and ?";
		Object[] params = {memberNickname,start,end};
		List list = jdbc.query(query,boardRowMapper,params);
		return list;
	}
	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	public Board selectOneBoard(int boardNo, String memberNickname) {
		String query = "select \r\n"
				+ "    b.*,\r\n"
				+ "    (select count(*) from board_like where board_no = b.board_no) like_count,\r\n"
				+ "    (select count(*) from board_like where board_no = b.board_no and member_nickname = ?) is_like,\r\n"
				+ "    (select count(*) from board_comment where board_no = b.board_no) comment_count\r\n"
				+ "from board b where board_no = ?";
		Object[] params = {memberNickname, boardNo};
		List list = jdbc.query(query,boardRowMapper,params);
		if(list.isEmpty()) {
			return null;
		}else {
			Board b = (Board)list.get(0);
			return b;
		}
	}
	public int updateReadCount(int boardNo) {
		String query = "update board set board_read_count = board_read_count + 1 where board_no = ?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int updateBoard(Board b) {
		String query = "update board set board_title = ?, board_content = ?, board_picture = ? where board_no = ?";
		Object[] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardPicture(),b.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	public int deleteBoard(int boardNo) {
		String query = "delete from board where board_no = ?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//댓글
	public int insertBoardComment(BoardComment bc) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,to_char(sysdate,'yyyy-mm-dd pm\" \"hh:mi\"'),?,?,?)";
		String boardCommentRef = bc.getCommentRef() == 0 ? null : String.valueOf(bc.getCommentRef());
		Object[] params = {bc.getCommentContent(),bc.getMemberNickname(),bc.getBoardNo(),boardCommentRef};
		int result = jdbc.update(query,params);
		return result;
	}
	public List selectBoardCommentList(int boardNo, String memberNickname) {
		String query = "select \r\n"
				+ "    bc.*,\r\n"
				+ "    (select count(*) from comment_like where comment_no = bc.comment_no) like_count,\r\n"
				+ "    (select count(*) from comment_like where comment_no = bc.comment_no and member_nickname = ?) is_like\r\n"
				+ "from board_comment bc where board_no = ? and comment_ref is null";
		Object[] params = {memberNickname, boardNo};
		List list = jdbc.query(query,boardCommentRowMapper,params);
		return list;
	}
	public List selectBoardReCommentList(int boardNo, String memberNickname) {
		String query = "select \r\n"
				+ "    bc.*,\r\n"
				+ "    (select count(*) from comment_like where comment_no = bc.comment_no) like_count,\r\n"
				+ "    (select count(*) from comment_like where comment_no = bc.comment_no and member_nickname = ?) is_like\r\n"
				+ "from board_comment bc where board_no = ? and comment_ref is not null";
		Object[] params = {memberNickname,boardNo};
		List list = jdbc.query(query,boardCommentRowMapper,params);
		return list;
	}
	public int updateBoardComment(BoardComment bc) {
		String query = "update board_comment set comment_content = ? where comment_no = ?";
		Object[] params = {bc.getCommentContent(),bc.getCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteComment(int commentNo) {
		String query = "delete from board_comment where comment_no = ?";
		Object[] params = {commentNo};
		int result = jdbc.update(query,params);
		return result;
	}
	public int insertBoardLike(int boardNo, String memberNickname) {
		String query = "insert into board_like values(board_like_seq.nextval,?,?)";
		Object[] params = {boardNo, memberNickname};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteBoardLike(int boardNo, String memberNickname) {
		String query = "delete from board_like where board_no = ? and member_nickname = ?";
		Object[] params = {boardNo, memberNickname};
		int result = jdbc.update(query,params);
		return result;
		}
	public int selectBoardLikeCount(int boardNo) {
		String query = "select count(*) from board_like where board_no = ?";
		Object[] params = {boardNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
	public int insertCommentLike(int commentNo, String memberNickname) {
		String query = "insert into comment_like values(?,?)";
		Object[] params = {commentNo, memberNickname};
		int result = jdbc.update(query,params);
		return result;
	}
	public int deleteCommentLike(int commentNo, String memberNickname) {
		String query = "delete from comment_like where comment_no = ? and member_nickname = ?";
		Object[] params = {commentNo, memberNickname};
		int result = jdbc.update(query,params);
		return result;
	}
	public int selectCommentLikeCount(int commentNo) {
		String query = "select count(*) from comment_like where comment_no = ?";
		Object[] params = {commentNo};
		int likeCount = jdbc.queryForObject(query, Integer.class, params);
		return likeCount;
	}
}
