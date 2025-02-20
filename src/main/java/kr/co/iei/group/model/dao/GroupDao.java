package kr.co.iei.group.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.group.model.vo.CategoryRowMapper;
import kr.co.iei.group.model.vo.Group;
import kr.co.iei.group.model.vo.GroupRowMapper;
import kr.co.iei.group.model.vo.Region;
import kr.co.iei.group.model.vo.RegionRowMapper;
import kr.co.iei.group.model.vo.SidoRowMapper;
import kr.co.iei.group.model.vo.SigunguRowMapper;

@Repository
public class GroupDao {
	@Autowired
	private GroupRowMapper groupRowMapper;
	@Autowired
	private CategoryRowMapper categoryRowMapper;
	@Autowired
	private RegionRowMapper regionRowMapper;
	@Autowired
	private SidoRowMapper sidoRowMapper;
	@Autowired
	private SigunguRowMapper sigunguRowMapper;
	
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

	public int insertRegion(Region region) {
		String query = "insert into region values(?,?)";
		Object[] params = {region.getSido(),region.getSigungu()};
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectSido() {
		String query = "select sido from (select distinct sido,idx from region order by idx)";
		List list = jdbc.query(query, sidoRowMapper);
		return list;
	}

	public List selectSigungu(String sido) {
		String query = "select sigungu from region where sido = ? order by 1";
		Object[] params = {sido};
		List list = jdbc.query(query, sigunguRowMapper, params);
		return list;
	}

	public int insertGroup(Group group) {
		String query = "insert into group_tbl values(group_seq.nextval,?,?,?,?,?,?,?,?)";
		Object[] params = {
				group.getGroupName()
				,group.getGroupInfo()
				,group.getMaxNum()
				,group.getGroupLocation()
				,group.getMeetingDate()
				,group.getThumbImage()
				,group.getCategoryNo()
				,group.getJoinFee()
		};
		int result = jdbc.update(query,params);
		return result;
	}

	
}
