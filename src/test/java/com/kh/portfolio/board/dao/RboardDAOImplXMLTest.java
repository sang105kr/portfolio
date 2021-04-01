package com.kh.portfolio.board.dao;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.Vote;
import com.kh.portfolio.board.vo.VoteVO;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
@Slf4j					//로거 기록
public class RboardDAOImplXMLTest {

	@Autowired
	private RboardDAO rboardDAO;
	
	@Test
	@DisplayName("댓글작성")
	@Disabled
	void write() {
		RboardVO rboardVO = new RboardVO();
		rboardVO.setBnum(String.valueOf(747));
		rboardVO.setRid("test5@test.com");
		rboardVO.setRnickname("테스터5");
		rboardVO.setRcontent("747게시글에 대한 댓글 테스트중...");
		
		rboardDAO.write(rboardVO);
	}
	
	@Test
	@DisplayName("댓글목록")
	@Disabled
	void list() {
		
		rboardDAO.list(757, 1, 10)
						 .stream()
						 .forEach(item->log.info(item.toString()));
	}
	@Test
	@DisplayName("선호도")
	@Disabled
	void vote() {
		VoteVO voteVO = new VoteVO();
		voteVO.setRnum(String.valueOf(41));
		voteVO.setBnum(String.valueOf(757));
		voteVO.setRid(String.valueOf("test50@test.com"));
		voteVO.setVote(Vote.BAD);
		log.info("vote:{}",rboardDAO.vote(voteVO));
	}
	@Test
	@DisplayName("선호도카운트")
	void voteCnt() {
		log.info("voteCnt:{}",rboardDAO.voteCnt(41l));
	}
}









