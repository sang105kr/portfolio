package com.kh.portfolio.test.generic.before;

public class main {

	public static void main(String[] args) {
		Box box = new Box();
		
		Toy toy = new Toy("인형");
		
		box.setElement(toy);

		Object object = box.getElement();
		Toy toy2 = (Toy)object;
		System.out.println(toy2.getName());
		
		System.out.println("============");
		
		Box box2 = new Box();
		Fruit fruit = new Fruit("귤");
		box2.setElement(fruit);
		
		System.out.println(((Fruit)box2.getElement()).getName());
		
		
		
	}

}
