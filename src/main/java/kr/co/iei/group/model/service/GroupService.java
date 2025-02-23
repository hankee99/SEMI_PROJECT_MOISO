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
	public int insertGroup(Group group, Member member) {
		int result = groupDao.insertGroup(group);
		int result2 = groupDao.insertGroupLeader(member);
		return result + result2;
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

	public List selectGroupBoard(int groupNo) {
		List list = groupDao.selectGroupBoard(groupNo); 
		return list;
	}

	public List selectGroupBoardType(int groupNo, int type) {
		List list = groupDao.selectGroupBoardType(groupNo,type);
		return list;
	}

	
}
