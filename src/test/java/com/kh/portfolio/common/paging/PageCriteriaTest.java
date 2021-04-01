package com.kh.portfolio.common.paging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.portfolio.board.dao.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/*.xml" })
@Slf4j
public class PageCriteriaTest {
	
	@Autowired
	PageCriteria pc;
	
	@Autowired
	BoardDAO boardDAO;
	
	@Test
	void pageCriteria() {
		int reqPage = 1;
		pc.getRc().setReqPage(reqPage);
		pc.setTotalRec(boardDAO.totalRecordCount());
		pc.calculatePaging();
		
		log.info("총레코드수:"+pc.getTotalRec());
		log.info("시작레코드:"+pc.getRc().getStartRec());
		log.info("종료레코드:"+pc.getRc().getEndRec());
		log.info("시작페이지:"+pc.getStartPage());
		log.info("종료페이지:"+pc.getEndPage());
		log.info("이전페이지:"+pc.isPrev());
		log.info("다음페이지:"+pc.isNext());
		log.info("최종페이지:"+pc.getFinalEndPage());
	}
}








