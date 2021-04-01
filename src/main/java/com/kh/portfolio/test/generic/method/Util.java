package com.kh.portfolio.test.generic.method;

public class Util {

		public static <T> Box<T> boxing(T t){
			
			Box<T> box = new Box<>();
			box.setElement(t);
			
			return box;
		}
}
