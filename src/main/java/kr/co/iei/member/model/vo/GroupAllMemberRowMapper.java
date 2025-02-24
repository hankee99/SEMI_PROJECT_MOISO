package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class GroupAllMemberRowMapper implements RowMapper<GroupAllMember>{

	@Override
	public GroupAllMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupAllMember gam = new GroupAllMember();
		gam.setGroupMemberLevel(rs.getInt("group_member_level"));
		gam.setGroupNo(rs.getInt("group_no"));
		gam.setJoinDate(rs.getString("join_date"));
		gam.setMemberAddr(rs.getString("member_addr"));
		gam.setMemberNickname(rs.getString("member_nickname"));
		gam.setMemberNo(rs.getInt("member_no"));
		gam.setMemberPhone(rs.getString("member_phone"));
		return gam;
	}
	

}
