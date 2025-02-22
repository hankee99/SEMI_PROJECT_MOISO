package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
		String query = "insert into member values(member_seq.nextval,?,?,?,?,null,null,to_char(sysdate,'yyyy-mm-dd'),'N',NULL,NULL,NULL,1)";
		Object[] params = {m.getMemberId(), m.getMemberPw(), m.getMemberNickname(), m.getMemberPhone()};
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

	public List ajaxIdSelect(String memberEmail) {
		String query = "select member_id from member where member_email = ?";
		Object[] params = {memberEmail};
		List list = jdbc.query(query, memberIdRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}
	
	
}














