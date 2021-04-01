package com.kh.portfolio.test.generic.after;

public class main {

	public static void main(String[] args) {
		Box<Fruit> box = new Box<Fruit>();
		box.setElement(new Fruit("귤"));
		System.out.println(box.getElement().getName());
		
		System.out.println();
		
		Box<Toy> box2 = new Box<>();
		box2.setElement(new Toy("인형"));
		System.out.println(box2.getElement().getName());
		
	}
}
