package kr.co.iei.member.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ManagerPageStat {
	private List list;
	private TotalStat totalStat;
}
