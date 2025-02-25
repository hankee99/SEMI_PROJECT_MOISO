package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RecentGroupRowMapper implements RowMapper<RecentGroup>{

	@Override
	public RecentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		RecentGroup recentGroup = new RecentGroup();
		recentGroup.setRecentNo(rs.getInt("recent_no"));
		recentGroup.setMemberNo(rs.getInt("member_no"));
		recentGroup.setGroupNo(rs.getInt("group_no"));
		return recentGroup;
	}

	

}
