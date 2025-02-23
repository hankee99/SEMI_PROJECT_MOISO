package kr.co.iei.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.group.model.vo.GroupRowMapper;

@Repository
public class MainPageDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private GroupRowMapper groupRowMapper;
	
	public List readCountGroup(int start, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl where read_count order by 1 desc) g) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, groupRowMapper, params);
		return list;
	}
}
