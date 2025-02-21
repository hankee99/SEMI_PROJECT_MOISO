package kr.co.iei.board.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class BoardRowMapper implements RowMapper<Board> {
	
	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardDate(rs.getString("board_date"));
		b.setBoardReadCount(rs.getInt("board_read_count"));
		b.setBoardPicture(rs.getString("board_picture"));
		b.setMemberNickname(rs.getString("member_nickname"));
		b.setCategoryName(rs.getString("category_name"));
		b.setIsLike(rs.getInt("is_like"));
		b.setLikeCount(rs.getInt("like_count"));
		b.setCommentCount(rs.getInt("comment_count"));
		return b;
	}
}

