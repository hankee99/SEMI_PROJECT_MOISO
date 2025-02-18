package kr.co.iei.board.vo;

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
	private int memberNo;
	private int categoryNo;
}
