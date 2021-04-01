package com.kh.portfolio.websocket;

import lombok.Data;

@Data
public class ConsultMessage {
	private String fromID; 			 //송신자아이디
	private String fromNickname; //송신자별칭
	private String toID;				 //수신자아이디
	private String toNickname;	 //수신자별칭
	private String message;			 //송신메세지
}
