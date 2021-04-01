package com.kh.portfolio.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kh.portfolio.member.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInOutHandler extends TextWebSocketHandler {
	   
  private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
  	//로그인 된경우만 웹소켓세션저장
  	MemberVO memberVO = (MemberVO)session.getAttributes().get("member");
  	if(memberVO == null) return;
  	
    sessions.add(session);
    super.afterConnectionEstablished(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
 			sessions.remove(session);
 			super.afterConnectionClosed(session, status);
  }

  public List<WebSocketSession> getWebSocketSession(){
  	return this.sessions;
  }
}