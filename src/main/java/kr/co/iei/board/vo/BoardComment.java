package kr.co.iei.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {
	private int commentNo;
	private String commentContent;
	private String commentDate;
	private String memberNickname;
	private int boardNo;
	private int commentRef;
	private int isLike;
	private int likeCount;
	private String profileImg;
}
