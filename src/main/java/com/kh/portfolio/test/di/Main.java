package com.kh.portfolio.test.di;

public class Main {

	public static void main(String[] args) {
		
		Person person = new Person(new Calculator());
		
		person.setCal(new Calculator());
		
		System.out.println(person.cal.add(10,20));
	}

}
