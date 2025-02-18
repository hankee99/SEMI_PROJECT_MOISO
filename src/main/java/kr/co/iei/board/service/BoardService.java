package kr.co.iei.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.dao.BoardDao;
import kr.co.iei.board.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public int insertBoard(Board b) {
		int boardNo = boardDao.newBoardNo();
		b.setBoardNo(boardNo);
		int result = boardDao.insertBoard(b);
		return result;
	}
}
