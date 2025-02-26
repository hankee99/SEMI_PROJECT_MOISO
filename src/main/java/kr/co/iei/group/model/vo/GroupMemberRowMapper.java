package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupMemberRowMapper implements RowMapper<GroupMember>{

	@Override
	public GroupMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupMember groupMember = new GroupMember();
		groupMember.setGroupNo(rs.getInt("group_no"));
		groupMember.setMemberNo(rs.getInt("member_no"));
		groupMember.setGroupMemberLevel(rs.getInt("group_member_level"));
		groupMember.setJoinDate(rs.getString("join_date"));
		groupMember.setMemberNickname(rs.getString("member_nickname"));
		groupMember.setProfileImg(rs.getString("profile_img"));
		return groupMember;
	}

}
