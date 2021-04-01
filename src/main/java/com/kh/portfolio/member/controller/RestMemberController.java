package com.kh.portfolio.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.portfolio.common.svc.MailService;
import com.kh.portfolio.common.svc.PasswordGeneratorSVC;
import com.kh.portfolio.member.svc.MemberSVC;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController //@Controller + @ResponseBody
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class RestMemberController {

	private final MemberSVC memberSVC;
	private final MailService mailService;
	private final PasswordGeneratorSVC pwGenSVC;
	
//	@Autowired
//	public RestMemberController(MemberSVC memberSVC,MailService mailService) {
//		this.memberSVC = memberSVC;
//		this.mailService = mailService;
//	}
	
	@GetMapping("/member_id")
	public Map<String,Object> findMemberId(
			@RequestParam("tel") String tel,
			@RequestParam("birth") String birth
			) {
		
		log.info("tel :" + tel);
		log.info("birth :" + birth);
		Map<String,Object> map = new HashMap<>();
		String member_id = null;
		try {
			member_id = memberSVC.findMemberId(tel, birth);
			map.put("rtcd", "00");
			map.put("result", member_id);
		}catch (Exception e) {
			map.put("rtcd", "01");
			map.put("errmsg","찾고자하는 아이디가 없습니다!" );
		}
		
		return map;
	}
	//회원아이디찾기
	@PostMapping("/member_id")
	public String findMemberId2(
			@RequestParam("tel") String tel,
			@RequestParam("birth") String birth
			) {
		
		log.info("tel :" + tel);
		log.info("birth :" + birth);
		
		return "test@test.com";
	}
	//회원비밀번호찾기
	@PostMapping("/pw")
	public Map<String,Object> findPW(
			@RequestBody Map<String,String> reqData
//				@RequestBody MemberVO memberVO

			) {
		log.info("member_id :" + reqData.get("member_id"));
		log.info("tel :" + reqData.get("tel"));
		log.info("birth :" + reqData.get("birth"));
		
		Map<String,Object> map = new HashMap<>();
		String pw = null; 
		
		try {
			pw =	memberSVC.findPW(
					reqData.get("member_id"), 
					reqData.get("tel"), 
					reqData.get("birth"));
		
			map.put("rtcd", "00");
			map.put("result", pw);			
		}catch (Exception e) {
			map.put("rtcd", "01");
			map.put("errmsg","회원정보가 없습니다." );			
		}
		
		return map;
	}
	//회원비밀번호찾기
	@PostMapping("/pwmail")
	public Map<String,Object> findPWMail(
			@RequestBody Map<String,String> reqData, 
			HttpServletRequest request,
			HttpServletResponse response
//				@RequestBody MemberVO memberVO
			
			) {
		log.info("member_id :" + reqData.get("member_id"));
		log.info("tel :" + reqData.get("tel"));
		log.info("birth :" + reqData.get("birth"));
		
		Map<String,Object> map = new HashMap<>();
		String pw = null; 
		
		try {
			pw =	memberSVC.findPW(
					reqData.get("member_id"), 
					reqData.get("tel"), 
					reqData.get("birth"));
			
			//1)임시비밀번호 비밀번호 생성
			String tmpPw = pwGenSVC.geneterPassword(7, true,true,true,true);
			
			//2)임시비밀번호로 회원의 비밀번호 변경
			memberSVC.changePw(reqData.get("member_id"), pw, tmpPw);
			
			//3)비밀번호 메일전송
			String mailSubject = "신규 비밀번호 발송";
			
			//로긴주소
			StringBuilder url = new StringBuilder();
			url.append("http://" + request.getServerName());
			url.append(":" + request.getServerPort());
			url.append(request.getContextPath());
			url.append("/loginForm");
			
			//메일본문내용
			StringBuilder sb = new StringBuilder();
			sb.append("<!DOCTYPE html>");
			sb.append("<html lang='ko'>");
			sb.append("<head>");
			sb.append("  <meta charset='UTF-8'>");
			sb.append("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
			sb.append("  <title>임시 비밀번호 발송</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("  <h1>신규비밀번호</h1>");
			sb.append("  <p>아래 비밀번호로 로그인 하셔서 비밀번호를 변경하세요</p>");
			sb.append("  <p>비밀번호 :" + tmpPw + "</p>");
			sb.append("  <a href='"+ url +"'>로그인</a>");
			sb.append("</body>");
			sb.append("</html>");
			//메일 전송
			mailService.sendMail(reqData.get("member_id"), mailSubject, sb.toString());
			
			map.put("rtcd", "00");
			map.put("result", "가입한 메일계정에서 확인바랍니다.");			
		}catch (Exception e) {
			e.printStackTrace();
			map.put("rtcd", "01");
			map.put("errmsg","회원정보가 없습니다." );			
		}
		
		return map;
	}
}



