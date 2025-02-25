package kr.co.iei.member.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.ManagerPageStat;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.member.model.vo.TotalStat;

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

	public ManagerPageStat managerPageStat() {
		TotalStat totalStat = memberDao.selectTotalStat();
		List operationStat = memberDao.OperationStat();
		
		//service가 가지고 있는것중에 되돌려줘야 할것 -> totalStat, list
		//java의 메소드는 1개의 자료형만 리턴이 가능 -> 2개를 되돌려줘야 함 객체, List
		//List와 객체를 속성으로 가지고 있는 객체 생성
		ManagerPageStat mps = new ManagerPageStat(operationStat, totalStat);
		return mps;
	}

	

}













