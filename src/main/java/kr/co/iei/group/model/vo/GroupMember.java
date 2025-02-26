package kr.co.iei.group.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupMember {
	private int groupNo;
	private int memberNo;
	private int groupMemberLevel;
	private String joinDate;
	private String memberNickname;
	private String profileImg;
}
