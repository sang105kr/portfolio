package com.kh.portfolio.test.generic.method;

public class Main {

	public static void main(String[] args) {
		Box<Toy> box = Util.<Toy>boxing(new Toy("인형"));
		System.out.println(box.getElement().getName());
		
		Box<Fruit> box2 = Util.boxing(new Fruit("귤"));
		System.out.println(box2.getElement().getName());
	}

}
