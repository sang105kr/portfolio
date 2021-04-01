package com.kh.portfolio.test.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapExam {

	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		
		map.put("홍길동", Integer.valueOf(90));
		map.put("홍길동2", 100);
		map.put("홍길동2", 90);
		
		System.out.println(map);
		System.out.println(map.size());

		//검색
		System.out.println(map.get("홍길동"));
		
		System.out.println(map.containsKey("홍길동3"));
		System.out.println(map.containsValue(100));
		System.out.println(map.containsValue(90));
		
		System.out.println(Map.entry("홍길동",90));
		
		System.out.println(map.keySet());
		
		Set<String> set2 = map.keySet();
		
		for(String name : set2) {
			System.out.println(name);
		}
		
		for(int value : map.values()) {
			System.out.println(value);
		}
		for(String value : map.keySet()) {
			System.out.println(value);
		}
		
		map.remove("홍길동");
		System.out.println(map);
		map.clear();
		System.out.println(map);
	}
}




