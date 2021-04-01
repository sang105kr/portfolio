package com.kh.portfolio.anonymous;

public class PersonMain {

	public static void main(String[] args) {

		Person p = new NewPerson();
		p.smile();
		
		Person p2 = new Person() {
			@Override
			public void smile() {
				System.out.println("호호 웃다");
				
			}
		};
		p2.smile();
	}

}
