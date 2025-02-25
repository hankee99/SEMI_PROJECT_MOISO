package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class OperationStatRowMapper implements RowMapper<OperationStat>{

	@Override
	public OperationStat mapRow(ResultSet rs, int rowNum) throws SQLException {
		OperationStat os = new OperationStat();
		os.setBoardCount(rs.getInt("board_count"));
		os.setDataDate(rs.getString("data_date"));
		os.setEnrollCount(rs.getInt("enroll_count"));
		os.setJoinCount(rs.getInt("join_count"));
		os.setPaySum(rs.getInt("pay_sum"));
		return os;
	}
	

}
