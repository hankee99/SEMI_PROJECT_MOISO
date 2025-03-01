package kr.co.iei.group.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.group.model.service.GroupService;
import kr.co.iei.group.model.vo.Category;
import kr.co.iei.group.model.vo.Group;
import kr.co.iei.group.model.vo.GroupBoard;
import kr.co.iei.group.model.vo.GroupBoardComment;
import kr.co.iei.group.model.vo.GroupMember;
import kr.co.iei.group.model.vo.Pay;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.FileUtils;
import java.util.stream.*;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping(value="/group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Value(value="${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;
	
	
	@GetMapping(value="/groupList")
	public String groupList(Model model) {
		List categoryList = groupService.selectCategoryList();
		
		model.addAttribute("categoryList", categoryList);
		
		return "group/groupList";
	}
	
	@ResponseBody
	@GetMapping("/totalCount")
	public int selectGroupTotalCount(int categoryNo) {
		int totalCount = groupService.selectGroupTotalCount(categoryNo);
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("/categoryGroup")
	public List categoryGroup(int categoryNo, int start, int amount) {
		List groupList = groupService.selectGroupList(categoryNo,start,amount);
		return groupList;
	}
	
	@GetMapping(value="/newGroup")
	public String newGroup() {
		return "group/newGroup";
	}
	
	@PostMapping("/makeNewGroup")
	public String makeNewGroup(Group group, MultipartFile imageFile, HttpSession session, Model model) {
		if(!imageFile.isEmpty()) {
			String savepath = root + "/groupThumb/";
			String filepath = fileUtils.upload(savepath, imageFile);
			group.setThumbImage(filepath);
		}
		int[] result = groupService.insertGroup(group, (Member)session.getAttribute("member"));
		
		model.addAttribute("title", "모임 생성 성공");
		model.addAttribute("text", "모임이 생성되었습니다");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/group/groupInfoPage?groupNo="+result[0]);
		
		return "common/msg";
	}
	
//	@ResponseBody
//	@GetMapping(value="/region")
//	public String getMethodName() {
//		
//		String url = "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json";
//		String consumer_key = "8a4b3386a4cf4e22bd73";
//		String consumer_secret = "17a4fc528dc64fb3bc98";
//		
//		try {
//			String result = Jsoup.connect(url)
//								.data("consumer_key",consumer_key)
//								.data("consumer_secret",consumer_secret)
//								.ignoreContentType(true)
//								.get().text();
//			
//			JsonObject access = (JsonObject)JsonParser.parseString(result);
//			String token = access.get("result").getAsJsonObject().get("accessToken").getAsString();
//			
//			String addrUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/addr/stage.json";
//			
//			String result2 = Jsoup.connect(addrUrl)
//							.data("accessToken",token)
//							.ignoreContentType(true)
//							.get().text();
//			JsonObject addr = (JsonObject)JsonParser.parseString(result2);
//			JsonArray addrArr = addr.get("result").getAsJsonArray();
//			
//
//			HashMap<String, ArrayList<String>> map = new HashMap<>();
//			
//			for(int i=0; i<addrArr.size(); i++) {
//				JsonObject item = addrArr.get(i).getAsJsonObject();
//				String cd = item.get("cd").getAsString();
//				String fullAddr = item.get("full_addr").getAsString();
//				
//				String result3 = Jsoup.connect(addrUrl)
//						.data("accessToken",token)
//						.data("cd",cd)
//						.ignoreContentType(true)
//						.get().text();
//				JsonObject sgg = (JsonObject)JsonParser.parseString(result3);
//				JsonArray sggArr = sgg.get("result").getAsJsonArray();
//				
//				ArrayList<String> list = new ArrayList<>();
//				for(int j=0; j<sggArr.size(); j++) {
//					JsonObject s = sggArr.get(j).getAsJsonObject();
//					String addrName = s.get("addr_name").getAsString();
//					list.add(addrName);
//				}
//				
//				map.put(fullAddr, list);
//			}
//			
//			
//			int rst = groupService.insertRegion(map);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return "";
//	}
	
	@ResponseBody
	@GetMapping(value="/sido")
	public List sido() {
		List list = groupService.selectSido();
		return list;
	}
	
	@ResponseBody
	@GetMapping(value="/sigungu")
	public List sigungu(String sido) {
		List list = groupService.selectSigungu(sido);
		return list;
	}
	
	@GetMapping("/groupInfoPage")
	public String groupInfoPage(int groupNo, Model model,HttpSession session) {
		Group group = groupService.selectGroupDetail(groupNo);
		Category category = groupService.selectOneCategory(group);
		List groupMembers = groupService.selectGroupMembers(groupNo);
		int numOfMembers = groupService.selectGroupMemberCount(groupNo);
		int result = groupService.updateReadCount(groupNo);
		int rst = 0;
				
		boolean flag = false;
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member");
			int memberNo = member.getMemberNo();
			rst = groupService.insertRecentGroup(memberNo,groupNo);
			
			for(GroupMember gm : (ArrayList<GroupMember>)groupMembers) {
				if(gm.getMemberNo() == memberNo) {
					flag = true;
					break;
				}
			}
		}
		
		
		model.addAttribute("group", group);
		model.addAttribute("img","/groupThumb/"+group.getThumbImage());
		model.addAttribute("categoryName", category.getCategoryName());
		model.addAttribute("groupMembers", groupMembers);
		model.addAttribute("numOfMembers", numOfMembers);
		model.addAttribute("flag", flag);
		return "group/groupInfoPage";
	}
	
	@GetMapping(value="/groupBoard")
	public String groupBoard(int groupNo,Model model) {
		
		Group group = groupService.selectGroupDetail(groupNo);
		
		model.addAttribute("group", group);
		return "group/groupBoard";
	}
	
	@ResponseBody
	@GetMapping("/groupBoardType")
	public List getMethodName(int groupNo, int type, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		int memberNo = member.getMemberNo();
		List list = groupService.selectGroupBoardType(groupNo,type,memberNo);
		
		return list;
	}
	
	@GetMapping(value="/writeFrm")
	public String writeFrm(int groupNo,Model model, HttpSession session) {
		List groupMembers = groupService.selectGroupMembers(groupNo);
		boolean flag = false;
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member");
			int memberNo = member.getMemberNo();
			
			for(GroupMember gm : (ArrayList<GroupMember>)groupMembers) {
				if(gm.getMemberNo() == memberNo && gm.getGroupMemberLevel() <3) {
					flag = true;
					break;
				}
			}
		}
		
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("flag", flag);
		return "group/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String write(GroupBoard groupBoard, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		int memberNo = member.getMemberNo();
		groupBoard.setMemberNo(memberNo);
		int result = groupService.insertGroupBoard(groupBoard);
		return "redirect:/group/groupBoard?groupNo="+groupBoard.getGroupNo();
	}
	
	@ResponseBody
	@PostMapping(value="/editorImage", produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile) {
		String savepath = root + "/groupEditor/";
		String filepath = fileUtils.upload(savepath, upfile);
		return filepath;
	}
	
	@ResponseBody
	@GetMapping(value="/likePush")
	public int likePush(int memberNo, int boardNo, int type) {
		int result = groupService.insertLike(memberNo,boardNo,type);
		int currLike = groupService.selectCurrentLikeCount(boardNo,type);
		return currLike;
	}
	
	
	@ResponseBody
	@PostMapping("/viewComment")
	public List viewComment(int boardNo) {
		List list = groupService.selectCommentList(boardNo);
		
		return list;
	}
	
	@ResponseBody
	@PostMapping(value="/insertComment")
	public Object[] insertComment(String content, int boardNo, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		int memberNo = member.getMemberNo();
		int commentNo = groupService.selectCommentSeq();
		int result = groupService.insertComment(commentNo,content,memberNo,boardNo);
		GroupBoardComment comment = groupService.selectOneComment(commentNo);
		int newCommentCount = groupService.selectCommentCount(boardNo);
		Object[] arr = {comment,newCommentCount};
		return arr;
	}
	
	@GetMapping(value="/successPay")
	public String successPay(Pay pay) {
		int result = groupService.insertPay(pay);
		return "redirect:/group/groupInfoPage?groupNo="+pay.getGroupNo();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
