package com.kh.portfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.kh.portfolio.websocket.ConsultHandler;
import com.kh.portfolio.websocket.LogInOutHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logInOutHandler(),  "/loginout-socket")
        				.addHandler(consultHandler(), "/consult-socket")
        				.addInterceptors(new HttpSessionHandshakeInterceptor())
        				.setAllowedOrigins("http://localhost:9080");
    }

    @Bean
    public WebSocketHandler logInOutHandler() {
        return new LogInOutHandler();
    }
    
    @Bean
    public WebSocketHandler consultHandler() {
    	return new ConsultHandler();
    }

}