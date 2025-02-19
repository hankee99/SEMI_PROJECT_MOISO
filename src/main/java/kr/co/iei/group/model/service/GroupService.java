package kr.co.iei.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.group.model.dao.GroupDao;

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
}
