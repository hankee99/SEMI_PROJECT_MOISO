package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RegionRowMapper implements RowMapper<Region>{

	@Override
	public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
		Region r = new Region();
		r.setSido(rs.getString("sido"));
		r.setSigungu(rs.getString("sigungu"));
		return r;
	}

}
