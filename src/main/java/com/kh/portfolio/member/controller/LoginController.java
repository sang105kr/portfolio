package com.kh.portfolio.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;

import com.kh.portfolio.member.svc.MemberSVC;
import com.kh.portfolio.member.vo.MemberVO;
import com.kh.portfolio.websocket.LogInOutHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
	private static final Logger logger
		= LoggerFactory.getLogger(LoginController.class);
	
	private final MemberSVC memberSVC;
	private final LogInOutHandler logInOutHandler;

	//로그인양식
	@GetMapping("/loginForm")
	public String loginForm() {
		
		return "/member/loginForm";
	}
	
	//로그인처리
	@PostMapping("/login")
	public String login(
			@RequestParam("member_id") String member_id,
			@RequestParam("pw") String pw,
			Model model,
			HttpSession	session
			) throws Exception {
		String viewName = null;
		
		logger.info("id:"+member_id);
		logger.info("pw:"+pw);
		
		//1. 회원인경우
		if(memberSVC.existMember(member_id)) {

			//1-1 회원번호일치
			if(memberSVC.isMember(member_id, pw)) {
				MemberVO memberVO = memberSVC.listOneMember(member_id);
				session.setAttribute("member", memberVO);
				
				logInOutHandler.getWebSocketSession()
				 .stream()
				 .filter(wss->(!((MemberVO)wss.getAttributes().get("member"))
						 													.getMember_id().equals(memberVO.getMember_id())))
				 .forEach(wss -> {
			      try {
			      	StringBuilder msg = new StringBuilder();
			      	msg.append(memberVO.getNickname());
			      	msg.append("("+memberVO.getMember_id()+")");
			      	msg.append("님이 로그인 하셨습니다!");
			        wss.sendMessage(new TextMessage(msg));
				    } catch (IOException e) {
				        log.error("Error occurred.", e);
				    }
					});		
					viewName = "index";
			//1-2 회원번호불일치
			}else {
				model.addAttribute("svr_msg","가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
				viewName = "/member/loginForm";
			}
		//2. 비회원인경우
		}else {
			model.addAttribute("svr_msg","가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
			viewName = "/member/loginForm";
		}
		return viewName;
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		MemberVO memberVO = ((MemberVO)session.getAttribute("member"));
		logInOutHandler.getWebSocketSession()
						 .stream()
						 .filter(wss->(!((MemberVO)wss.getAttributes().get("member"))
								 													.getMember_id().equals(memberVO.getMember_id())))
						 .forEach(wss -> {
					      try {
					      	StringBuilder msg = new StringBuilder();
					      	msg.append(memberVO.getNickname());
					      	msg.append("("+memberVO.getMember_id()+")");
					      	msg.append("님이 로그아웃 하셨습니다!");
					        wss.sendMessage(new TextMessage(msg));
						    } catch (IOException e) {
						        log.error("Error occurred.", e);
						    }
							});;
							
		//세션에 저장된 정보 제거
		session.invalidate();
		return "redirect:/";
	}
}






