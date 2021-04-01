package com.kh.portfolio.common.svc;

import org.springframework.stereotype.Service;

import com.kh.portfolio.common.PasswordGenerator;
import com.kh.portfolio.common.PasswordGenerator.PasswordGeneratorBuilder;

@Service
/**
 * 비밀번호 등 임시번호 발생기
 * @author sanghak.yi
 *
 */
public class PasswordGeneratorSVC {
	
	public String geneterPassword(int passwordLength,boolean ...flag) throws Exception{
		
		if(flag.length > 4) {
			throw new Exception("flag값은 4개를 초과할 수 없습니다.");
		}
		
		PasswordGenerator passwordGenerator 
			= new PasswordGenerator.PasswordGeneratorBuilder()//
				.useDigits(flag[0]) 															// 숫자포함
				.useLower(flag[1]) 																// 소문자포함
				.useUpper(flag[2]) 																// 대문자포함
				.usePunctuation(flag[3]) 													// 특수문자포함
				.build();
		return passwordGenerator.generate(passwordLength); 	// 비밀번호 길이
	}
}
