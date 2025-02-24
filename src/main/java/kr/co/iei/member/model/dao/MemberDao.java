package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.group.model.vo.SidoRowMapper;
import kr.co.iei.group.model.vo.SigunguRowMapper;
import kr.co.iei.member.model.vo.GroupAllMemberRowMapper;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.MemberIdRowMapper;
import kr.co.iei.member.model.vo.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private MemberIdRowMapper memberIdRowMapper;
	@Autowired
	private SidoRowMapper sidoRowMapper;
	@Autowired
	private SigunguRowMapper sigunguRowMapper;
	@Autowired
	private GroupAllMemberRowMapper groupAllMemberRowMapper;
	
	public Member selectOneMember(Member m) {
		String query = "Select * from member where member_id=? and member_pw=?";
		Object[] params = {m.getMemberId(), m.getMemberPw()};
		List list = jdbc.query(query, memberRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			Member member = (Member)list.get(0);
			return member;
		}
	}

	public int insertMember(Member m) {
		String query = "insert into member values(member_seq.nextval,?,?,?,?,null,null,to_char(sysdate,'yyyy-mm-dd'),'N',NULL,NULL,NULL,1,?)";
		Object[] params = {m.getMemberId(), m.getMemberPw(), m.getMemberNickname(), m.getMemberPhone(), m.getMemberEmail()};
		int result =jdbc.update(query, params);
		return result;
	}

	public Member selectOneMember(String memberId) {
		String query = "select * from member where member_id = ?";
		Object[] params = {memberId};
		List list = jdbc.query(query, memberRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}

	public Member selectOneMemberNickname(String memberNickname) {
		String query = "select * from member where member_nickname = ?";
		Object[] params = {memberNickname};
		List list = jdbc.query(query, memberRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (Member)list.get(0);
		}
	}

	public List idSelect(String memberEmail) {
		String query = "select member_id from member where member_email = ?";
		Object[] params = {memberEmail};
		List list = jdbc.query(query, memberIdRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
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

	
	
	public int updateMypage(Member m ) {
		String query = "update member set profile_img=?, member_nickname=?, member_intro=?, member_mbti=?, member_addr=?, member_gender=?, member_pw=?, member_phone=? where member_id=?";
		Object[] params = {m.getProfileImg(), m.getMemberNickname(), m.getMemberIntro(), m.getMemberMbti(), m.getMemberAddr(), m.getMemberGender(), m.getMemberPw(), m.getMemberPhone(), m.getMemberId()};
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectGroupMember() {
		String query = "select group_no, m.member_no, member_nickname, member_addr, member_phone, join_date, group_member_level \r\n"
				+ "from member m join GROUP_MEMBER gm on (m.member_no = gm.member_no)\r\n"
				+ "where group_no = (select group_no from group_member where member_no=1 and group_member_level=1)";
		List list = jdbc.query(query, groupAllMemberRowMapper);
		return list;
	}
	
	
}














