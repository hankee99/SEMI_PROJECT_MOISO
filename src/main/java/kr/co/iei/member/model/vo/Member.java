package kr.co.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberNickname;
	private String memberPhone;
	private String memberAddr;
	private String profileImg;
	private String enrollDate;
	private String delMember;
	private String memberIntro;
	private String memberMbti;
	private String memberGender;
}
