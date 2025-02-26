package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.iei.group.model.vo.Group;

@Component
public class GroupDataRowMapper implements RowMapper<Group>{

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		Group g = new Group();
		g.setThumbImage(rs.getString("thumb_image"));
		g.setCategoryName(rs.getString("category_name"));
		g.setMaxNum(rs.getInt("max_num"));
		g.setGroupName(rs.getString("group_name"));
		g.setMeetingDate(rs.getString("meeting_date"));
		g.setGroupLocation(rs.getString("group_location"));
		return g;
	}
	

}
