package kr.co.iei.group.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.group.model.dao.GroupDao;
import kr.co.iei.group.model.vo.Category;
import kr.co.iei.group.model.vo.Group;
import kr.co.iei.group.model.vo.GroupBoard;
import kr.co.iei.group.model.vo.GroupBoardComment;
import kr.co.iei.group.model.vo.Pay;
import kr.co.iei.group.model.vo.Region;
import kr.co.iei.member.model.vo.Member;

@Service
public class GroupService {
	@Autowired
	private GroupDao groupDao;

	public List selectCategoryList() {
		List list = groupDao.selectCategoryList();
		
		return list;
	}

	public List selectGroupList(int categoryNo, int start, int amount) {
		int end = start + amount -1;
		
		List list = groupDao.selectGroupList(categoryNo,start,end);
		return list;
	}
	
	public int selectGroupTotalCount(int categoryNo) {
		int totalCount = groupDao.selectGroupTotalCount(categoryNo);
		return totalCount;
	}
	
	//api로 불러온거 db에 집어넣는 1회용
	@Transactional
	public int insertRegion(HashMap<String, ArrayList<String>> map) {
		int result = 0;
		for(String sido : map.keySet()) {
			for(String sigungu : map.get(sido)) {
				Region region = new Region();
				region.setSido(sido);
				region.setSigungu(sigungu);
				
				result += groupDao.insertRegion(region);
			}
		}
		
		
		
		return result;
	}

	public List selectSido() {
		List list = groupDao.selectSido();
		return list;
	}

	public List selectSigungu(String sido) {
		List list = groupDao.selectSigungu(sido);
		return list;
	}
	
	@Transactional
	public int[] insertGroup(Group group, Member member) {
		int groupNo = groupDao.selectNewGroupNo();
		group.setGroupNo(groupNo);
		int result = groupDao.insertGroup(group);
		int memberNo = member.getMemberNo();
		int result2 = groupDao.insertGroupMember(memberNo,groupNo,1);
		int[] resultArr = {groupNo, result + result2}; 
		return resultArr;
	}

	public Group selectGroupDetail(int groupNo) {
		List list = groupDao.selectGroupDetail(groupNo);
		Group group = (Group)list.get(0);
		return group;
	}

	public Category selectOneCategory(Group group) {
		List list = groupDao.selectOneCategory(group);
		Category category = (Category)list.get(0);
		return category;
	}

	public List selectGroupMembers(int groupNo) {
		List list = groupDao.selectGroupMembers(groupNo);
		return list;
	}

	public int selectGroupMemberCount(int groupNo) {
		int count = groupDao.selectGroupMemberCount(groupNo);
		return count;
	}
	
	@Transactional
	public int insertGroupBoard(GroupBoard groupBoard) {
		int result = groupDao.insertGroupBoard(groupBoard);
		return result;
	}

	public List selectGroupBoardType(int groupNo, int type, int memberNo) {
		List list = groupDao.selectGroupBoardType(groupNo,type,memberNo);
		return list;
	}

	@Transactional
	public int insertLike(int memberNo, int boardNo, int type) {
		int result = groupDao.insertLike(memberNo,boardNo,type);
		return result;
	}

	public int selectCurrentLikeCount(int boardNo, int type) {
		int currLike = groupDao.selectCurrentLikeCount(boardNo,type);
		return currLike;
	}

	@Transactional
	public int updateReadCount(int groupNo) {
		int result = groupDao.updateReadCount(groupNo);
		return result;
	}

	public List selectCommentList(int boardNo) {
		List list = groupDao.selectCommentList(boardNo);
		return list;
	}

	@Transactional
	public int insertComment(int commentNo, String content,int memberNo, int boardNo) {
		int result = groupDao.insertComment(commentNo,content,memberNo,boardNo);
		return result;
	}

	public int selectCommentSeq() {
		int commentSeq = groupDao.selectCommentSeq();
		return commentSeq;
	}

	public GroupBoardComment selectOneComment(int commentNo) {
		List list = groupDao.selectOneComment(commentNo);
		GroupBoardComment groupBoardComment = (GroupBoardComment)list.get(0);
		return groupBoardComment;
	}

	public int selectCommentCount(int boardNo) {
		int commentCount = groupDao.selectCommentCount(boardNo);
		return commentCount;
	}

	@Transactional
	public int insertPay(Pay pay) {
		int result = groupDao.insertPay(pay);
		int result2 = groupDao.insertGroupMember(pay.getMemberNo(), pay.getGroupNo(), 3);
		return result+result2;
	}

	@Transactional
	public int insertRecentGroup(int memberNo, int groupNo) {
		int result = groupDao.insertRecentGroup(memberNo,groupNo);
		return result;
	}
	
	

	

	

	
}
