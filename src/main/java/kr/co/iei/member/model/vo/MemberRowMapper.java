package kr.co.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MemberRowMapper implements RowMapper<Member>{

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member m = new Member();
		m.setDelMember(rs.getString("del_member"));
		m.setEnrollDate(rs.getString("enroll_date"));
		m.setMemberAddr(rs.getString("member_addr"));
		m.setMemberEmail(rs.getString("member_email"));
		m.setMemberGender(rs.getString("member_gender"));
		m.setMemberIntro(rs.getString("member_intro"));
		m.setMemberMbti(rs.getString("member_mbti"));
		m.setMemberNickname(rs.getString("member_nickname"));
		m.setMemberNo(rs.getInt("member_no"));
		m.setMemberPhone(rs.getString("member_phone"));
		m.setMemberPw(rs.getString("member_pw"));
		m.setProfileImg(rs.getString("profile_img"));
		
		return m;
	}

}
