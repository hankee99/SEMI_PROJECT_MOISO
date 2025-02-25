package kr.co.iei.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.group.model.vo.GroupCategoryRowMapper;
import kr.co.iei.group.model.vo.GroupRowMapper;

@Repository
public class MainPageDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private GroupRowMapper groupRowMapper;
	@Autowired
	private GroupCategoryRowMapper groupCategoryRowMapper;
	
	public int selectReadCountTotalCount() {
		String query = "select count(*) from group_tbl";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	public List selectReadCountGroup(int start, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl join category using(category_no) order by read_count desc) g) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, groupCategoryRowMapper, params);
		return list;
	}
	

	public int selectDateTotalCount(String date) {
		String query = "select count(*) from group_tbl where meeting_date = ?";
		Object[] params = {date};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
	
	public List selectDateGroup(String date, int dateStart, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl join category using(category_no) where meeting_date = ? order by read_count desc) g) where rnum between ? and ?";
		Object[] params = {date,dateStart,end};
		List list = jdbc.query(query, groupCategoryRowMapper, params);
		return list;
	}

	public List selectRecentGroup(int memberNo, int recentStart, int end) {
		String query = "select * from (select rownum as rnum, r.* from (select * from group_tbl join category using(category_no) join  recent_group using (group_no) where member_no = ? order by recent_no desc) r) where rnum between ? and ?";
		Object[] params = {memberNo, recentStart,end};
		List list = jdbc.query(query, groupCategoryRowMapper, params);
		return list;
	}

	
	//모임리스트 카운트
	public List selectGroupSearchList(String groupSearch, int start, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl join category using(category_no) where group_name like '%'||?||'%' order by 1 desc) g) where rnum between ? and ?";
		Object[] params = {groupSearch,start,end};
		List list = jdbc.query(query, groupCategoryRowMapper, params);
		return list;
	}
	
	

}
