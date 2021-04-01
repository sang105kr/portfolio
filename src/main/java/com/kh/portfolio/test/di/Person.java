package com.kh.portfolio.test.di;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {

	//필드 Dependancy Injection
	Calculator cal = new Calculator();

	@Autowired
	Calculator cal2;
	
	public Person() {}
	
	//생성자 Dependancy Injection
	@Autowired
	public Person(Calculator cal) {
		this.cal = cal;
	}

	//setter Dependancy Injection
	@Autowired
	public void setCal(Calculator cal) {
		this.cal = cal;
	}
	
}
