package kr.co.iei.group.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupBoardComment {
	private int groupBoardCommentNo;
	private int memberNo;
	private String groupBoardCommentContent;
	private String groupBoardCommentWriteDate;
	private int groupBoardNo;
	private String memberNickname;
	private String profileImg;
//	private int isLike;
}
