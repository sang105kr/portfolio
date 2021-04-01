package com.kh.portfolio.test.annotation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

		@PrintAnotation
		public void method1() {
			System.out.println("method1() 실행");
		}
		
		@PrintAnotation("*")
		public void method2() {
			System.out.println("method2() 실행");
		}
		
		@PrintAnotation(value = "+",number = 30)
		public void method3() {
			System.out.println("method3() 실행");
		}
}
