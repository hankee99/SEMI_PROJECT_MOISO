package kr.co.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

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

	@Transactional
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

	public List idSelect(String memberEmail) {
		List list = memberDao.idSelect(memberEmail);
		return list;
	}
	
	public List selectSido() {
		List list = memberDao.selectSido();
		return list;
	}
	
	public List selectSigungu(String sido) {
		List list = memberDao.selectSigungu(sido);
		return list;
	}



	@Transactional
	public int updateMypage(Member m) {
		int result = memberDao.updateMypage(m);// TODO Auto-generated method stub
		return result;
	}

	public List selectGroupMemebr() {
		List list = memberDao.selectGroupMember();
		return list;
	}

	

}













