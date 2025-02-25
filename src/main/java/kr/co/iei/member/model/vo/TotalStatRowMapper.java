package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class TotalStatRowMapper implements RowMapper<TotalStat>{

	@Override
	public TotalStat mapRow(ResultSet rs, int rowNum) throws SQLException {
		TotalStat ts = new TotalStat();
		ts.setTotalBoard(rs.getInt("total_board"));
		ts.setTotalGroup(rs.getInt("total_group"));
		ts.setTotalMember(rs.getInt("total_member"));
		return ts;
	}

}
