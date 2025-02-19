package kr.co.iei.group.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.group.model.vo.CategoryRowMapper;
import kr.co.iei.group.model.vo.GroupRowMapper;

@Repository
public class GroupDao {
	@Autowired
	private GroupRowMapper groupRowMapper;
	@Autowired
	private CategoryRowMapper categoryRowMapper;
	@Autowired
	private JdbcTemplate jdbc;

	public List selectCategoryList() {
		String query = "select * from category";
		List list = jdbc.query(query, categoryRowMapper);
		return list;
	}

	public List selectGroupList(int categoryNo,int start, int end) {
		String query = "select * from (select rownum as rnum, g.* from (select * from group_tbl where CATEGORY_NO=? order by 1 desc) g) where rnum between ? and ?";
		Object[] params = {categoryNo,start,end};
		List list = jdbc.query(query, groupRowMapper, params);
		return list;
	}
	
	public int selectGroupTotalCount(int categoryNo) {
		String query = "select count(*) from group_tbl where category_no = ?";
		Object[] params = {categoryNo};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}
}
