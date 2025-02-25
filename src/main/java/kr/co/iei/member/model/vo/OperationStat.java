package kr.co.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationStat {
	private String dataDate;
	private int joinCount;
	private int paySum;
	private int enrollCount;
	private int boardCount;
}
