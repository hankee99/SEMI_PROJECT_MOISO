package kr.co.iei.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class FileUtils {
	public String upload(String savepath, MultipartFile file) {
		
		//원본파일명 추출 -> 배고파.gif
		String filename = file.getOriginalFilename();
		//배고파랑		.gif로 분리
		//원본파일명에서 시작부터 제일 뒤에 있는 .앞까지를 문자열로 가져옴
		String onlyFilename = filename.substring(0, filename.lastIndexOf("."));
		//원본파일명에서 제일 뒤에 있는 .부터 끝까지를 문자열로 가져옴 -> .gif
		String extention = filename.substring(filename.lastIndexOf("."));
		//실제로 업로드할 파일명을 저장할 변수
		String filepath = null;
		//파일명이 겹치면 증가시키면서 뒤에 붙일 변수
		int count = 0;
		//파일명이 겹치지 않을 때까지 반복해서 수행
		while(true) {
			if(count == 0) {
				filepath = onlyFilename+extention;
			}else {
				filepath = onlyFilename+"_"+count+extention;
			}
			
			//위에서 만든 파일명이 사용중인지 체크
			File checkFile = new File(savepath+filepath);
			//해당 파일명으로 업로드폴더에 파일이름이 존재하지 않으면 반복문 종료
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		//파일명 중복체크 끝 -> 내가 업로드한 파일명이 결정 -> 이후 업로드(일단 지금은 코드 분리)
		
		//중복체크가 끝난 파일명 업로드
		File uploadFile = new File(savepath+filepath);
		
		try {
			//파일업로드
			file.transferTo(uploadFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return filepath;
	}

	public  void downloadFile(String savepath, String filename, String filepath, HttpServletResponse response) {
		String downFile = savepath+filepath;
				
		try {
			//첨부파일을 현재 서버프로그램으로 읽어오기 위한 주스트림 생성
			FileInputStream fis = new FileInputStream(downFile);
			//속도개선을 위한 보조스트림 생성
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			//읽어온 파일을 사용자에게 내보낼 주스트림 생성 -> response 객체 내부에 존재
			ServletOutputStream sos = response.getOutputStream();
			//속도 개선을 위한 보조스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			
			//다운로드할 파일이름 처리(사용자가 받을 파일 이름)
			String resFilename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
			
			//파일 다운로드를 위한 HTTP Header 설정(응답형식/파일이름 지정)
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+resFilename);
			
			//파일을 읽어서 클라이언트에게 전송
			while(true) {
				int read = bis.read();
				if(read != -1) {
					bos.write(read);					
				}else {
					break;
				}
			}
			bos.close();
			bis.close();
 		} catch (FileNotFoundException e) {
 			
			e.printStackTrace();	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
}