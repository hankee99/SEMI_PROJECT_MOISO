package kr.co.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member selectOneMember(Member m) {
		Member member = memberDao.selectOneMember(m);
		return member;
	}

	public int inserMember(Member m) {
		int result = memberDao.insertMember(m);
		return result;
	}

	public Member selectOneMember(String memberId) {
		Member m = memberDao.selectOneMember(memberId);
		return m;
	}

	public Member selectOneMemberNickname(String memberNickname) {
		Member m = memberDao.selectOneMemberNickname(memberNickname);
		return m;
	}

}
