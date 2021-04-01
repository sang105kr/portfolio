package com.kh.portfolio.member.svc;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.portfolio.member.vo.MemberVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
public class MemberSVCImplTest {

	private final static Logger logger
		= LoggerFactory.getLogger(MemberSVCImplTest.class);
	
	@Autowired
	MemberSVC memberSVC;
	
	@Test
	@DisplayName("회원가입")
	@Disabled
	void joinMember() {
		MemberVO memberVO = new MemberVO();
		memberVO.setMember_id("test4@test.com");
		memberVO.setPw("1234");
		memberVO.setTel("010-1234-5678");
		memberVO.setNickname("테스터1");
		memberVO.setGender("남");
		memberVO.setRegion("울산");
		memberVO.setBirth(LocalDate.of(2001, 01, 01));
		
		int result = memberSVC.joinMember(memberVO);
		
		logger.info("cnt:"+result);		
	}
	
	@Test
	@DisplayName("회원개별조회")
	@Disabled
	void listOneMember() {
		String id = "test@test.com";
		MemberVO memberVO = memberSVC.listOneMember(id);
		logger.info(memberVO.toString());
	}
	@Test
	@DisplayName("회원존재유무")
	@Disabled
	void existMember() {
		String id = "test@test.com";
		logger.info("회원존재유무:"+memberSVC.existMember(id));
	}
	@Test
	@DisplayName("회원인증")
	@Disabled
	void isMember() {
		String id = "test@test.com";
		String pw = "1234";
		logger.info("회원인증:"+memberSVC.isMember(id,pw));
	}
	@Test
	@DisplayName("비밀번호변경")
	@Disabled	
	void changePw() {
		
		String id = "test@test.com";
		String currentpw = "1234";
		String nextpw = "5678";
		int result = memberSVC.changePw(id, currentpw, nextpw);
		Assertions.assertEquals(1, result);
		
	}
	@Test
	@DisplayName("회원탈퇴")
	@Disabled
	void outMember() {
	
		Assertions.assertEquals(
				1,		//예상치 
				memberSVC.outMember("test4@test.com", "1234") //결과치
		);
	}
	
	@Test
	void findMemberID() {
		String tel = "010-3333-222";
		String birth = "20000101";
		String memberId = memberSVC.findMemberId(tel, birth);
		
		Assertions.assertEquals("test@test.com", memberId);
	}
}








