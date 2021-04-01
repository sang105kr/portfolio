package com.kh.portfolio.test.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ArrayListExam {

	public static void main(String[] args) {
		List<String>list = new LinkedList<String>();
	
		list.add("Java");
		list.add("HTML");
		list.add("CSS");
		list.add("JAVASCRIPT");
		list.add("JAVASCRIPT");
		list.add(3,"spring");
		
		System.out.println(list);
		
		list.set(3, "summer");
		
		System.out.println(list);
		
		System.out.println(list.contains("spring"));
		
		System.out.println(list.get(3));
		System.out.println(list.isEmpty());
		System.out.println(list.size());
		//일반for문
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		//향상된for문
		for(String ele : list) {
			System.out.println(ele);
		}
		
		System.out.println(list.remove(0));
		System.out.println(list);
		System.out.println(list.remove("summer"));
		System.out.println(list);
		list.clear();
		System.out.println(list.size());
	}
}






