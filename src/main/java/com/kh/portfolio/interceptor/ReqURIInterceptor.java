package com.kh.portfolio.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ReqURIInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("ReqURIInterceptor.preHandle");
		HandlerInterceptor.super.preHandle(request, response, handler);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("ReqURIInterceptor.postHandle");

		HttpSession session = request.getSession(false);
    
    if(session != null) {
    	if(session.getAttribute("member") != null) {
        // 이전 요청 URL로 이동      
        String reqURI = (String)session.getAttribute("reqURI");
        if (reqURI != null) {
        	response.sendRedirect(request.getContextPath()+"/"+reqURI);
        }
    	}
    }				
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("ReqURIInterceptor.afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
