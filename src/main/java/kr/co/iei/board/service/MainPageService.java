package kr.co.iei.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.dao.MainPageDao;

@Service
public class MainPageService {
	@Autowired
	private MainPageDao mainPageDao;

	public List readCountGroup(int start, int amount) {
		int end = start + amount -1;		
		List list = mainPageDao.readCountGroup(start,end);
		return list;
	}
}
