package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupRowMapper implements RowMapper<Group>{

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		Group g = new Group();
		g.setCategoryNo(rs.getInt("group_no"));
		g.setGroupName(rs.getString("group_name"));
		g.setGroupInfo(rs.getString("group_info"));
		g.setMaxNum(rs.getInt("max_num"));
		g.setGroupLocation(rs.getString("group_location"));
		g.setMeetingDate(rs.getString("meeting_date"));
		g.setThumbImage(rs.getString("thumb_image"));
		g.setCategoryNo(rs.getInt("category_no"));
		g.setJoinFee(rs.getInt("join_fee"));
		return g;
	}
	
	
}
