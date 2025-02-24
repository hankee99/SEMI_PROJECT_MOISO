package kr.co.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupAllMember {
	private int memberNo;
	private int groupNo;
	private String memberNickname;
	private String memberAddr;
	private String memberPhone;
	private String joinDate;
	private int groupMemberLevel;

}
