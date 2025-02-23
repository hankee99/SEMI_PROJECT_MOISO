package kr.co.iei.group.model.vo;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupBoard {
	private int groupBoardNo;
	private int memberNo;
	private String groupBoardContent;
	private String writeDate;
	private int groupNo;
	private int type;
	private String memberNickname;
	private String profileImg;
}
