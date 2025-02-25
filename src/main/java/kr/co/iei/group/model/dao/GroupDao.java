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
import kr.co.iei.group.model.vo.GroupBoardCommentRowMapper;
import kr.co.iei.group.model.vo.GroupBoardRowMapper;
import kr.co.iei.group.model.vo.GroupMemberRowMapper;
import kr.co.iei.group.model.vo.GroupRowMapper;
import kr.co.iei.group.model.vo.Pay;
import kr.co.iei.group.model.vo.Region;
import kr.co.iei.group.model.vo.RegionRowMapper;
import kr.co.iei.group.model.vo.SidoRowMapper;
import kr.co.iei.group.model.vo.SigunguRowMapper;
import kr.co.iei.member.model.vo.Member;

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
	private GroupBoardCommentRowMapper groupBoardCommentRowMapper;
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
	
	public int selectNewGroupNo() {
		String query = "select group_seq.nextval from dual";
		int groupNo = jdbc.queryForObject(query, Integer.class);
		return groupNo;
	}
	
	public int insertGroup(Group group) {
		String query = "insert into group_tbl values(?,?,?,?,?,?,?,?,?,0)";
		Object[] params = {
				group.getGroupNo()
				,group.getGroupName()
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
	
	public int insertGroupMember(int memberNo, int groupNo,int memberLevel) {
		String query = "insert into group_member values(?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {groupNo,memberNo,memberLevel};
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

	

	public List selectGroupBoardType(int groupNo, int type, int memberNo) {
		if(type == -1) {
			String query = "SELECT \r\n"
					+ "    gb.group_board_no, \r\n"
					+ "    gb.member_no, \r\n"
					+ "    gb.group_board_content, \r\n"
					+ "    gb.write_date, \r\n"
					+ "    gb.group_no, \r\n"
					+ "    gb.type, \r\n"
					+ "    m.member_nickname, \r\n"
					+ "    m.profile_img,\r\n"
					+ "    NVL(gc.comment_count, 0) AS comment_count,\r\n"
					+ "    NVL(gl.like_count, 0) AS like_count,\r\n"
					+ "    (SELECT COUNT(*) \r\n"
					+ "     FROM group_like \r\n"
					+ "     WHERE text_type = 0 AND text_no = gb.group_board_no AND member_no = ?) AS is_like\r\n"
					+ "FROM \r\n"
					+ "    group_board gb\r\n"
					+ "JOIN \r\n"
					+ "    member m ON gb.member_no = m.member_no\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT \r\n"
					+ "         group_board_no, \r\n"
					+ "         COUNT(*) AS comment_count \r\n"
					+ "     FROM \r\n"
					+ "         group_board_comment \r\n"
					+ "     GROUP BY \r\n"
					+ "         group_board_no) gc ON gb.group_board_no = gc.group_board_no\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT \r\n"
					+ "         text_no,  -- 게시글 번호로 변경\r\n"
					+ "         COUNT(*) AS like_count \r\n"
					+ "     FROM \r\n"
					+ "         group_like \r\n"
					+ "     WHERE \r\n"
					+ "         text_type = 0  -- type 필터링 추가\r\n"
					+ "     GROUP BY \r\n"
					+ "         text_no) gl ON gb.group_board_no = gl.text_no\r\n"
					+ "WHERE \r\n"
					+ "    gb.group_no = ?\r\n"
					+ "ORDER BY \r\n"
					+ "    gb.group_board_no DESC";
			Object[] params = {memberNo,groupNo};
			List list  = jdbc.query(query, groupBoardRowMapper, params);
			return list;
		}else {
			String query = "SELECT \r\n"
					+ "    gb.group_board_no, \r\n"
					+ "    gb.member_no, \r\n"
					+ "    gb.group_board_content, \r\n"
					+ "    gb.write_date, \r\n"
					+ "    gb.group_no, \r\n"
					+ "    gb.type, \r\n"
					+ "    m.member_nickname, \r\n"
					+ "    m.profile_img,\r\n"
					+ "    NVL(gc.comment_count, 0) AS comment_count,\r\n"
					+ "    NVL(gl.like_count, 0) AS like_count,\r\n"
					+ "    (SELECT COUNT(*) \r\n"
					+ "     FROM group_like \r\n"
					+ "     WHERE text_type = 0 AND text_no = gb.group_board_no AND member_no = ?) AS is_like\r\n"
					+ "FROM \r\n"
					+ "    group_board gb\r\n"
					+ "JOIN \r\n"
					+ "    member m ON gb.member_no = m.member_no\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT \r\n"
					+ "         group_board_no, \r\n"
					+ "         COUNT(*) AS comment_count \r\n"
					+ "     FROM \r\n"
					+ "         group_board_comment \r\n"
					+ "     GROUP BY \r\n"
					+ "         group_board_no) gc ON gb.group_board_no = gc.group_board_no\r\n"
					+ "LEFT JOIN \r\n"
					+ "    (SELECT \r\n"
					+ "         text_no,  -- 게시글 번호로 변경\r\n"
					+ "         COUNT(*) AS like_count \r\n"
					+ "     FROM \r\n"
					+ "         group_like \r\n"
					+ "     WHERE \r\n"
					+ "         text_type = 0  -- type 필터링 추가\r\n"
					+ "     GROUP BY \r\n"
					+ "         text_no) gl ON gb.group_board_no = gl.text_no\r\n"
					+ "WHERE \r\n"
					+ "    gb.group_no = ? and gb.type = ?\r\n"
					+ "ORDER BY \r\n"
					+ "    gb.group_board_no DESC";
			Object[] params = {memberNo,groupNo,type};
			List list  = jdbc.query(query, groupBoardRowMapper, params);
			return list;
		}
		
		
		
	}

	public int insertLike(int memberNo, int boardNo, int type) {
		String query = "insert into group_like values(?,?,?)";
		Object[] params = {memberNo,boardNo,type};
		int result = jdbc.update(query,params);
		return result;
	}

	public int selectCurrentLikeCount(int boardNo, int type) {
		String query = "select count(*) from group_like where text_no = ? and text_type = ?";
		Object[] params = {boardNo,type};
		int count = jdbc.queryForObject(query, Integer.class, params);
		return count;
	}

	public int updateReadCount(int groupNo) {
		String query = "update group_tbl set read_count = read_count+1 where group_no = ?";
		Object[] parmas = {groupNo};
		int result = jdbc.update(query,parmas);
		return result;
	}

	public List selectCommentList(int boardNo) {
		String query = "select gbc.*, m.member_nickname, m.profile_img from group_board_comment gbc join member m on gbc.member_no = m.member_no where gbc.group_board_no = ? order by gbc.group_board_comment_write_date";
		Object[] params = {boardNo};
		List list = jdbc.query(query, groupBoardCommentRowMapper, params);
		return list;
	}

	public int insertComment(int commentNo, String content,int memberNo, int boardNo) {
		String query = "insert into group_board_comment values(?,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),?)";
		Object[] params = {commentNo,memberNo,content,boardNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int selectCommentSeq() {
		String query = "select group_board_comment_seq.nextval from dual";
		int seq = jdbc.queryForObject(query, Integer.class);
		return seq;
	}

	public List selectOneComment(int commentNo) {
		String query = "select gbc.*, m.member_nickname, m.profile_img from group_board_comment gbc join member m on gbc.member_no = m.member_no where gbc.group_board_comment_no = ?";
		Object[] params = {commentNo};
		List list = jdbc.query(query, groupBoardCommentRowMapper,params);
		return list;
	}

	public int selectCommentCount(int boardNo) {
		String query = "select count(*) from group_board_comment where group_board_no=?";
		Object[] params = {boardNo};
		int count = jdbc.queryForObject(query, Integer.class, params);
		return count;
	}

	public int insertPay(Pay pay) {
		String query = "insert into pay values(pay_seq.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd'))";
		Object[] params = {pay.getMemberNo(),pay.getMemberNo(),pay.getPrice()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertRecentGroup(int memberNo, int groupNo) {
		String query = "insert into recent_group values(recent_group_seq.nextval,?,?)";
		Object[] params = {memberNo,groupNo};
		int result = jdbc.update(query,params);
		return result;
	}

	

	

	

	
}
