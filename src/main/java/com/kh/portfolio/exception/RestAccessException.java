package com.kh.portfolio.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class RestAccessException extends RuntimeException {
	List<ErrorMsg> errMsgList;
	ErrorMsg errorMsg;
	
	public RestAccessException() { }
	
	public RestAccessException(ErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public RestAccessException(List<ErrorMsg> errMsgList) {
		this.errMsgList = errMsgList;
	}
	
	public RestAccessException(Throwable t) {
		super(t);
	}
}
