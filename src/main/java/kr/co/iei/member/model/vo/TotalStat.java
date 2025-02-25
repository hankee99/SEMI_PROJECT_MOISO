package kr.co.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TotalStat {
	private int totalMember;
	private int totalGroup;
	private int totalBoard;
}
