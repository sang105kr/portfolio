package com.kh.portfolio.common;

import lombok.Data;

@Data
public class Code {
	private String code_id;	//코드값
	private String name;		//코드명
	
	public Code(String code_id, String name) {
		this.code_id = code_id;
		this.name = name;
	}
}
