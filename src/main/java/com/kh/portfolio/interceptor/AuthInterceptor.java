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
public class AuthInterceptor implements HandlerInterceptor {
	
	//컨트롤서 수행전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("AuthInterceptor.preHandle");
		//요청URL분석
		String uri			= request.getRequestURI();
		String contextPath	= request.getContextPath();
		String reqURI		= uri.substring(contextPath.length());
		
		HttpSession session = request.getSession(false);
		log.info("요청uri="+reqURI);
		if(session == null || session.getAttribute("member") == null) {
			
			log.info("권한없는자의 접근시도가 있음"+request.getRemoteAddr());
		
			request.getSession().setAttribute("reqURI", reqURI);
			response.sendRedirect(request.getContextPath()+"/loginForm");
			return false;
		}			
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("AuthInterceptor.postHandle");		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("AuthInterceptor.afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
