package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupBoardRowMapper implements RowMapper<GroupBoard>{

	@Override
	public GroupBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupBoard groupBoard = new GroupBoard();
		groupBoard.setGroupBoardNo(rs.getInt("group_board_no"));
		groupBoard.setMemberNo(rs.getInt("member_no"));
		groupBoard.setGroupBoardContent(rs.getString("group_board_content"));
		groupBoard.setWriteDate(rs.getString("write_date"));
		groupBoard.setGroupBoardNo(rs.getInt("group_no"));
		groupBoard.setType(rs.getInt("type"));
		groupBoard.setMemberNickname(rs.getString("member_nickname"));
		groupBoard.setProfileImg(rs.getString("profile_img"));
		groupBoard.setCommentCount(rs.getInt("comment_count"));
		groupBoard.setLikeCount(rs.getInt("like_count"));
		return groupBoard;
	}

}
