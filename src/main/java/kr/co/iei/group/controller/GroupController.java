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
import kr.co.iei.group.model.vo.GroupMember;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.FileUtils;
import java.util.stream.*;


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
	public String makeNewGroup(Group group, MultipartFile imageFile) {
		if(!imageFile.isEmpty()) {
			String savepath = root + "/groupThumb/";
			String filepath = fileUtils.upload(savepath, imageFile);
			group.setThumbImage(filepath);
		}
		int result = groupService.insertGroup(group);
		System.out.println(group.toString());
		return "redirect:/";
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
		boolean flag = false;
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member");
			int memberNo = member.getMemberNo();
			
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
	
	@GetMapping(value="/writeFrm")
	public String writeFrm(int groupNo,Model model) {
		model.addAttribute("groupNo", groupNo);
		return "group/writeFrm";
	}
	
	
	
	
	
	
}
