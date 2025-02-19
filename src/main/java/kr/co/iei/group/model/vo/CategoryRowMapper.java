package kr.co.iei.group.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryRowMapper implements RowMapper<Category>{

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category c= new Category();
		c.setCategoryNo(rs.getInt("category_no"));
		c.setCategoryName(rs.getString("category_name"));
		return c;
	}

}
