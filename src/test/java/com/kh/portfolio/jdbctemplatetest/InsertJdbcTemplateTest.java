package com.kh.portfolio.jdbctemplatetest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
public class InsertJdbcTemplateTest {

	private final static Logger logger
		= LoggerFactory.getLogger(InsertJdbcTemplateTest.class);
	
	@Autowired
	JdbcTemplate jt;
	
	@Test
	@DisplayName("학생점수생성")
	//@Disabled
	void insert() {
		
		StringBuilder sql = new StringBuilder(); // String, StringBuffer, StringBuilder
		
		sql.append("insert into student(id,name,kor,eng,math) ");
		sql.append("values(?,?,?,?,?)");
		
		int result = jt.update(sql.toString(),"id3","홍길동3",60,90,77);
		logger.info("result="+result);
	}
	
	@Test
	@DisplayName("학생점수 삭제")
	@Disabled
	void delete() {
		
		String sql = "delete from student where id = ?";
		
		int result = jt.update(sql, "id3");
		logger.info("result="+result);
	}
	
	@Test
	@DisplayName("국어점수 변경")
	@Disabled
	void update() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("update student ");
		sql.append("   set kor = ? ");
		sql.append(" where id = ? ");
		
		int result = jt.update(sql.toString(), 50, "id2");
		logger.info("result="+result);
	}
	
}










