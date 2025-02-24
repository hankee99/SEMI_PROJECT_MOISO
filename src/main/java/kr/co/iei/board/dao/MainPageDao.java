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
	
	public int selectReadCountTotalCount() {
		String query = "select count(*) from group_tbl";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	public List selectReadCountGroup(int start, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl order by read_count desc) g) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, groupRowMapper, params);
		return list;
	}
	

	public int selectDateTotalCount(String date) {
		String query = "select count(*) from group_tbl where meeting_date = ?";
		Object[] params = {date};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	
	public List selectDateGroup(String date, int dateStart, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl where meeting_date = ? order by read_count desc) g) where rnum between ? and ?";
		Object[] params = {date,dateStart,end};
		List list = jdbc.query(query, groupRowMapper, params);
		return list;
	}

}
