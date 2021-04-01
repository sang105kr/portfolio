package com.kh.portfolio.test.collection;

import java.util.HashSet;
import java.util.Set;

public class HashSetExam {
	
	public static void main(String args[]) {
		Set<String> set = new HashSet<>();
		
		set.add("Java");
		set.add("html");
		set.add("csss");
		set.add("javascript");
		set.add("javascript");
		set.add(null);
		set.add(null);
		
		
		System.out.println(set.contains("html"));
		System.out.println(set.isEmpty());
		System.out.println(set.size());
		
		for(String ele:set) {
			System.out.println(ele);
		}
		System.out.println(set);
		
		set.remove("javascript");
		System.out.println(set);
	}
		
}
