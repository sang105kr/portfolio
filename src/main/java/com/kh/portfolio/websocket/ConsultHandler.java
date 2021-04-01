package com.kh.portfolio.websocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.portfolio.member.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsultHandler extends TextWebSocketHandler {
	   
  private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
  	//로그인 된경우만 웹소켓세션저장
//  	MemberVO memberVO = (MemberVO)session.getAttributes().get("member");
//  	if(memberVO == null) return;
  	
    sessions.add(session);
    super.afterConnectionEstablished(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
 			sessions.remove(session);
 			super.afterConnectionClosed(session, status);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
      super.handleTextMessage(session, message);
      //수신자 추출
      ObjectMapper objectMapper = new ObjectMapper();
      ConsultMessage payload = objectMapper.readValue(message.getPayload(),ConsultMessage.class);

      //수신자 웹소켓으로 메세지 전송
      sessions.stream()
      	.filter(wss->
      			((MemberVO)wss.getAttributes().get("member")).getMember_id().equals(payload.getToID()) ||
      			((MemberVO)wss.getAttributes().get("member")).getMember_id().equals(payload.getFromID()) )
      	.forEach(wss -> {
          try {
              wss.sendMessage(message);
              log.info("consulHandler:{}",message.getPayload());
          } catch (IOException e) {
              log.error("Error occurred.", e);
          }
      });
  }
  public List<WebSocketSession> getWebSocketSession(){
  	return this.sessions;
  }
}