package com.kh.portfolio.test.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {
//		Service service = new Service();
//		service.method1();
//		service.method2();
//		service.method3();
		
		Class t =Service.class;		//Service클래스의 메타정보를 읽어오기위해 Class객체를 얻어낸다.
		Method[] methods = t.getDeclaredMethods();	//Service클래스의 메소드를 추출
		
		for(Method method : methods) {
//			log.info(method.getName());
			//해당 메소드에 @PrintAnotation 존재유무를 판단
			if(method.isAnnotationPresent(PrintAnotation.class)) {
				//어노테이션 @PrintAnotation이 있으면 PrintAnotation객체에 접근
				PrintAnotation printAnnotation = method.getAnnotation(PrintAnotation.class);
				//시작구분선 출력
				for(int i=0; i<printAnnotation.number(); i++) {
					System.out.print(printAnnotation.value());
				}		
				System.out.println();
				
				//메소드 호출
				try {
					method.invoke(new Service());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//종료구분선 출력
				for(int i=0; i<printAnnotation.number(); i++) {
					System.out.print(printAnnotation.value());
				}		
				System.out.println();				
			}
		}
	}
}






