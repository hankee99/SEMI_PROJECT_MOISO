package kr.co.iei.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardRowMapper;
import kr.co.iei.group.model.vo.CategoryRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
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
	public List selectBoardList(int start, int end) {
		String query = "select * from (select rownum as rnum, b.* from (select * from board order by board_no desc)b) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,boardRowMapper,params);
		return list;
	}
	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
}
