package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.iei.group.model.vo.Pay;

@Component
public class PayDataRowMapper implements RowMapper<Pay>{

	@Override
	public Pay mapRow(ResultSet rs, int rowNum) throws SQLException {
		Pay p = new Pay();
		p.setPayDate(rs.getString("pay_date"));
		p.setPayNo(rs.getInt("pay_no"));
		p.setPrice(rs.getInt("price"));
		return p;
	}
	
}
