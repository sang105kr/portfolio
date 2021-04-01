package com.kh.portfolio.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintAnotation {
	
  String value() default "-"; //디폴트 엘리먼트
  int number() default 15;
  
}
