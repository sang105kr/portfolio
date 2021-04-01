package com.kh.portfolio.common.paging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/*.xml" })
@Slf4j
public class RecordCriteiraTest {

	@Autowired
	@Qualifier("rec1")
	RecordCriteria rc1;
	@Autowired
	@Qualifier("rec2")
	RecordCriteria rc2;
	@Autowired
	@Qualifier("rec3")
	RecordCriteria rc3;
	
	@Test
	void getRec() {
		//RecordCriteria rc = new RecordCriteria(10);
		rc1.setReqPage(2);
		rc2.setReqPage(2);
		rc3.setReqPage(2);
		log.info("시작레코드:" + rc1.getStartRec());
		log.info("종료레코드:" + rc1.getEndRec());
		log.info("시작레코드:" + rc2.getStartRec());
		log.info("종료레코드:" + rc2.getEndRec());
		log.info("시작레코드:" + rc3.getStartRec());
		log.info("종료레코드:" + rc3.getEndRec());
		
	}
}
