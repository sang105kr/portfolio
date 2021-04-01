package com.kh.portfolio.member.dao;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.portfolio.member.vo.MemberVO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
public class MemberDAOImplTest {

	private final static Logger logger
		= LoggerFactory.getLogger(MemberDAOImplTest.class);
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	JdbcTemplate jt;
	
	@Test
	@Disabled
	void joinMember() {
		MemberVO memberVO = new MemberVO();
		
//    values ('test@test.com','1234','010-1234-5678','테스터1','남','울산','20010101');		
		memberVO.setMember_id("test2@test.com");
		memberVO.setPw("1234");
		memberVO.setTel("010-1234-5678");
		memberVO.setNickname("테스터1");
		memberVO.setGender("남");
		memberVO.setRegion("울산");
		memberVO.setBirth(LocalDate.of(2001, 01, 01));
		
		int result = memberDAO.joinMember(memberVO);
		
		logger.info("cnt:"+result);
		
	}
	@Test
	@DisplayName("회원개별조회")
	@Disabled
	void listOneMember() {
		String id = "test@test.com";
		MemberVO memberVO = memberDAO.listOneMember(id);
		logger.info(memberVO.toString());
	}
	@Test
	@DisplayName("회원존재유무")
	@Disabled
	void existMember() {
		String id = "test@test.com";
		logger.info("회원존재유무:"+memberDAO.existMember(id));
	}
	@Test
	@DisplayName("회원인증")
	@Disabled
	void isMember() {
		String id = "test@test.com";
		String pw = "1234";
		logger.info("회원인증:"+memberDAO.isMember(id,pw));
	}
	@Test
	@DisplayName("회원수정")
	@Disabled
	void modifyMember() {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setTel("010-1111-1111");
		memberVO.setNickname("관리자2");
		memberVO.setGender("남");
		memberVO.setRegion("울산");
		memberVO.setBirth(LocalDate.of(2001, 01, 01));
		memberVO.setMember_id("test@test.com");
		
		int result = memberDAO.modifyMember(memberVO);
		logger.info("회원수정:" + result);
	}
	@Test
	@DisplayName("회원비밀번호 변경")
	@Disabled
	void changePw() {
		String id = "test@test.com";
		String currentpw = "5678";
		String nextpw = "1234";
		int result = memberDAO.changePw(id, currentpw, nextpw);
		Assertions.assertEquals(1, result);
	}
	
	@Test
	@DisplayName("회원탈퇴")
	@Disabled
	void outMember() {
		String id = "test4@test.com";
		String currentpw = "1234";
		int result = memberDAO.outMember(id, currentpw);
		Assertions.assertEquals(1, result);
	}
	
	@Test
	@DisplayName("batchInsert")
	@Disabled
	void batchInsert() {
		
		String id = "test@test.com";
		List<String> hobbys = List.of("01","02","03");

		String sql = "insert into hobby values( ? , ? )";
		
		int[] result = jt.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1,hobbys.get(i));
				ps.setString(2,id);
			}
			
			@Override
			public int getBatchSize() {
				return hobbys.size();
			}
		});
		
		logger.info(hobbys.toString());
		logger.info(result.toString());
		
		
	}
	
	@Test
	@Disabled
	void memberExistHobby() {
		String member_id = "test39@test.com";
		MemberVO member = memberDAO.listOneMember(member_id);
		logger.info(member.toString());
	}
	
	@Test
	@Disabled
	void findMemberID() {
		String tel = "010-3333-222";
		String birth = "20000101";
//		String memberId = memberDAO.findMemberId(tel, birth);
//		Assertions.assertEquals("test@test.com", memberId);
		Assertions.assertThrows(
				EmptyResultDataAccessException.class, 
				() -> {
					memberDAO.findMemberId(tel, birth);
				}
		);
	}
	
	@Test
	@Disabled
	void findPw() {
		String member_id = "test@test.com"; 
		String tel = "010-3333-222";
		String birth = "20000101";
//		String memberId = memberDAO.findMemberId(tel, birth);
//		Assertions.assertEquals("test@test.com", memberId);
		Assertions.assertThrows(
				EmptyResultDataAccessException.class, 
				() -> {
					memberDAO.findPW(member_id, tel, birth);
				}
		);
	}
	
	@Test
	@Disabled
	void findProfileImg() throws Exception {
		byte[] bytes = memberDAO.findProfileImg("test@test.com");
		logger.info(bytes.toString());
	}
	
}









