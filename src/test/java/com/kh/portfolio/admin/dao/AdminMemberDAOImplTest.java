package com.kh.portfolio.admin.dao;

import java.util.List;

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
public class AdminMemberDAOImplTest {
	
	private static final Logger logger
		= LoggerFactory.getLogger(AdminMemberDAOImplTest.class);
	
	@Autowired
	AdminMemberDAO adminMemberDAO;
	
	@Test
	void memberList() {
		List<MemberVO> list = adminMemberDAO.memberList();
		logger.info(list.toString());
	}
}
