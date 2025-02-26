package kr.co.iei.member.model.vo;

import java.util.List;

import kr.co.iei.group.model.vo.Pay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MypaymentData {
	private List list;
	private Pay pay;
}
