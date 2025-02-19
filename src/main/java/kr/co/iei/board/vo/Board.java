package kr.co.iei.board.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardReadCount;
	private	String boardPicture; 
	private String memberNickname;
	private String categoryName;
	private List<BoardComment> commentList;
	private List<BoardComment> reCommentList;
}
