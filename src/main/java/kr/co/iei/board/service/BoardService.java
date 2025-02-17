package kr.co.iei.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.dao.BoardDao;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
}
