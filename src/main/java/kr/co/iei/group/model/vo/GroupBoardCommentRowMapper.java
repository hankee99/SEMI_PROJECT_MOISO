package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupBoardCommentRowMapper implements RowMapper<GroupBoardComment>{

	@Override
	public GroupBoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupBoardComment groupBoardComment = new GroupBoardComment();
		groupBoardComment.setGroupBoardCommentNo(rs.getInt("group_board_comment_no"));
		groupBoardComment.setMemberNo(rs.getInt("member_no"));
		groupBoardComment.setGroupBoardCommentContent(rs.getString("group_board_comment_content"));
		groupBoardComment.setGroupBoardCommentWriteDate(rs.getString("group_board_comment_write_date"));
		groupBoardComment.setGroupBoardNo(rs.getInt("group_board_no"));
		groupBoardComment.setMemberNickname(rs.getString("member_nickname"));
		groupBoardComment.setProfileImg(rs.getString("profile_img"));
//		groupBoardComment.setIsLike(rs.getInt("is_like"));
		return groupBoardComment;
	}

}
