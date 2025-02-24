package kr.co.iei.group.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pay {
	private int payNo;
	private int memberNo;
	private int groupNo;
	private int price;
	private String payDate;
}
