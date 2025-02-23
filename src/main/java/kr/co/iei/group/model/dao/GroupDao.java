package kr.co.iei.group.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.group.model.vo.CategoryRowMapper;
import kr.co.iei.group.model.vo.Group;
import kr.co.iei.group.model.vo.GroupBoard;
import kr.co.iei.group.model.vo.GroupBoardRowMapper;
import kr.co.iei.group.model.vo.GroupMemberRowMapper;
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
	private GroupMemberRowMapper groupMemberRowMapper;
	@Autowired
	private GroupBoardRowMapper groupBoardRowMapper;
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
		String query = "insert into group_tbl values(group_seq.nextval,?,?,?,?,?,?,?,?,0)";
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

	public List selectGroupDetail(int groupNo) {
		String query = "select * from group_tbl where group_no=?";
		Object[] params = {groupNo};
		List list = jdbc.query(query, groupRowMapper, params);
		return list;
	}

	public List selectOneCategory(Group group) {
		String query = "select * from category where category_no = ?";
		Object[] params = {group.getCategoryNo()};
		List list = jdbc.query(query, categoryRowMapper, params);
		return list;
	}

	public List selectGroupMembers(int groupNo) {
		String query = "select group_no,member_no,group_member_level,join_date,member_nickname from group_member join MEMBER using(member_no) where group_no = ? order by member_no";
		Object[] params = {groupNo};
		List list = jdbc.query(query, groupMemberRowMapper, params);
		return list;
	}

	public int selectGroupMemberCount(int groupNo) {
		String query = "select count(*) from GROUP_MEMBER where group_no = ?";
		Object[] params = {groupNo};
		int count = jdbc.queryForObject(query, Integer.class, params);
		return count;
	}

	public int insertGroupBoard(GroupBoard groupBoard) {
		String query = "insert into group_board values(group_board_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),?,?)";
		Object[] params = {groupBoard.getMemberNo(),groupBoard.getGroupBoardContent(),groupBoard.getGroupNo(),groupBoard.getType()};
		int result = jdbc.update(query,params);
		return result;
	}

	public List selectGroupBoard(int groupNo) {
		String query = "select group_board_no, member_no, group_board_content, write_date, group_no, type, member_nickname, profile_img from group_board join member using(member_no) where group_no = ? order by 1 desc";
		Object[] params = {groupNo};
		List list  = jdbc.query(query, groupBoardRowMapper, params);
		return list;
	}

	
}
