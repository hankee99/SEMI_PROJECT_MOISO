package kr.co.iei.util;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.vo.Member;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//컨트롤러쪽으로 들어가는 요청을 가로챔
		//리턴 타입이 boolean
		//true를 리턴하면 Controller 수행
		//false를 리턴하면 Controller 수행 X
		//로그인 여부를 체크해서 로그인이 되어 있으면 Controller 수행
		//로그인이 안되어 있으면 로그인을 안내하는 페이지로 이동
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");//다운캐스팅 해줘야함
		if(member != null) {
			return true;
		}else {
			response.sendRedirect("/member/loginMsg"); //사용자한테 보내줌
			return false; //스프링한테 보내줌
		}
	}
	

}
