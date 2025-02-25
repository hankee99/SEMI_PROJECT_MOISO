package kr.co.iei.board.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment> {
	
	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment bc = new BoardComment();
		bc.setCommentNo(rs.getInt("comment_no"));
		bc.setCommentContent(rs.getString("comment_content"));
		bc.setCommentDate(rs.getString("comment_date"));
		bc.setMemberNickname(rs.getString("member_nickname"));
		bc.setBoardNo(rs.getInt("board_no"));
		bc.setCommentRef(rs.getInt("comment_ref"));
		bc.setIsLike(rs.getInt("is_like"));
		bc.setLikeCount(rs.getInt("like_count"));
		bc.setProfileImg(rs.getString("profile_img"));
		return bc;
	}
}
