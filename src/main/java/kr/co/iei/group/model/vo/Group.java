package kr.co.iei.group.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group {
	private int groupNo;
	private String groupName;
	private String groupInfo;
	private int maxNum;
	private String groupLocation;
	private String meetingDate;
	private String thumbImage;
	private int categoryNo;
	private int joinFee;
	private int readCount;
	private String categoryName;
}
