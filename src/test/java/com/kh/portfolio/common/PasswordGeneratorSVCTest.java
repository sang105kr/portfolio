package com.kh.portfolio.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.portfolio.common.svc.PasswordGeneratorSVC;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/*.xml" })
@Slf4j
public class PasswordGeneratorSVCTest {
	
	@Autowired
	PasswordGeneratorSVC passwordGeneratorSVC;
	
	@Test
	void generator() {
		try {
			String tmp = passwordGeneratorSVC.geneterPassword(10, true, false, false, false);
			log.info("임시비밀번호:"+tmp);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
