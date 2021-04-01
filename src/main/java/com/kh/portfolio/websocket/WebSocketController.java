package com.kh.portfolio.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {
    
    @RequestMapping("/clientChat")
    public String clientChat() {
        return "/chat/clientChat";
    }
    @RequestMapping("/serverChat")
    public String serverChat() {
    	return "/admin/chat/serverChat";
    }
}
