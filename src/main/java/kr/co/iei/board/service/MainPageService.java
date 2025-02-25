package kr.co.iei.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.dao.MainPageDao;

@Service
public class MainPageService {
	@Autowired
	private MainPageDao mainPageDao;

	public int selectReadCountTotalCount() {
		int totalCount = mainPageDao.selectReadCountTotalCount();
		return totalCount;
	}
	
	public List selectReadCountGroup(int start, int amount) {
		int end = start + amount -1;//8
		List list = mainPageDao.selectReadCountGroup(start,end);
		return list;
	}

	public List selectDateGroup(String date, int dateStart, int amount) {
		int end = dateStart + amount -1;
		List list = mainPageDao.selectDateGroup(date,dateStart,end);
		return list;
	}

	public int selectDateTotalCount(String date) {
		int totalCount = mainPageDao.selectDateTotalCount(date);
		return totalCount;
	}

	public List selectRecentGroup(int memberNo, int recentStart, int amount) {
		int end = recentStart + amount -1;
		List list = mainPageDao.selectRecentGroup(memberNo, recentStart,end);
		return list;
	}
}
