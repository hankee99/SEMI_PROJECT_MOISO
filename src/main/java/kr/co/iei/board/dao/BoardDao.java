package kr.co.iei.board.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.vo.Board;
import kr.co.iei.board.vo.BoardRowMapper;

@Repository
public class BoardDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;

	public int newBoardNo() {
		String query = "select board_seq.nextval from dual";
		int boardNo = jdbc.queryForObject(query, Integer.class);
		return boardNo;
	}

	public int insertBoard(Board b) {
		String query = "insert into board values(?,?,?,to_char(sysdate,'yyyy-mm-dd'),0,?,?,?)";
		Object[] params = {b.getBoardNo(),b.getBoardTitle(),b.getBoardContent(),b.getBoardPicture(),b.getMemberNo(),b.getCategoryNo()};
		int result = jdbc.update(query,params);
		return result;
	}
}
