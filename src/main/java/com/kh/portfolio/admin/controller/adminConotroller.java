package com.kh.portfolio.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.portfolio.admin.svc.AdminMemberSVC;
import com.kh.portfolio.member.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class adminConotroller {
	
	private static final Logger logger
		= LoggerFactory.getLogger(adminConotroller.class);
	
	@Autowired
	AdminMemberSVC memberSVC;
	
	@GetMapping
	public String adminPage() {
		return "/admin/adminPage";
	}
	
	//회원 전체목록
	@GetMapping("/memberList")
	public String memberList(Model model) {
		
		List<MemberVO> list = memberSVC.memberList();
		model.addAttribute("memberList", list);
		
		return "/admin/memberList";
	}
	//상담
	@GetMapping("/consulting")
	public String consulting(Model model) {
		
		List<MemberVO> list = memberSVC.memberList();
		model.addAttribute("memberList", list);
		
		return "/admin/chat/clientChat";
	}
}






