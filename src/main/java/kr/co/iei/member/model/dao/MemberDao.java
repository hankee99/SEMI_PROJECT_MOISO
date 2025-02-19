package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private MemberRowMapper memberRowMapper;
	
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
	
	
}
