package com.kh.portfolio.member.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.portfolio.admin.svc.AdminMemberSVC;
import com.kh.portfolio.member.svc.MemberSVC;
import com.kh.portfolio.member.vo.MemberVO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest")
@Slf4j
public class RestTestController {

	private final MemberSVC memberSVC;
	private final AdminMemberSVC adminMemberSVC;
	
	@Autowired
	public RestTestController(
			MemberSVC memberSVC,
			AdminMemberSVC adminMemberSVC) {
		
		this.memberSVC = memberSVC;
		this.adminMemberSVC = adminMemberSVC;
	}
	
	@GetMapping(value="/member/{id:.+}/")
	public MemberVO test1(@PathVariable("id") String member_id) {
		log.info("member_id:" + member_id );
		MemberVO memberVO = memberSVC.listOneMember(member_id);
		return memberVO;
	}
	@Data
	static class Result<T>{
		private String result;
	}
	@GetMapping(value="/member" )
	public MemberVO test2() {
		
		MemberVO memberVO = memberSVC.listOneMember("test@test.com");
		return memberVO;
	}
	
	@GetMapping("/members/all")
	public List<MemberVO> test3() {
		
		return adminMemberSVC.memberList();
		
	}
	@GetMapping("/members/all2")
	public Map<String,List<MemberVO>> test4() {
		
		Map<String,List<MemberVO>> map = new HashMap<>();
		
		map.put("data", adminMemberSVC.memberList());
		
		return map;
		
	}
	@GetMapping("/members/all3")
	public Set<List<MemberVO>> test5() {
		
		Set<List<MemberVO>> set = new HashSet<>();
		
		set.add(adminMemberSVC.memberList());
		
		return set;
		
	}
	
	@GetMapping("/members/all5")
	public Map<String,Object> test6() {
		
		Map<String,Object> map = new HashMap<>();
		List<MemberVO> list = adminMemberSVC.memberList();
		
		if(list.size() > 0 ) {
			map.put("rtcd", "success");
			map.put("count", list.size());
			map.put("data", adminMemberSVC.memberList());
			
		}else {
			map.put("rtcd", "fail");
			
		}
		
		return map;
	}	
	
	//회원생성
	@PostMapping("/member")
	public Map<String,Object> createMember(@RequestBody MemberVO memberVO) {
			
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			memberSVC.joinMember(memberVO);
			memberVO = memberSVC.listOneMember(memberVO.getMember_id());
			map.put("result", "success");
			map.put("data", memberVO);
		}catch(Exception e) {
			map.put("result", "fail");
			map.put("data", null);			
		}
		return map;
	}
	//회원수정
	@PatchMapping("/member")
	public Map<String,Object> modifyMember(@RequestBody MemberVO memberVO) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			memberSVC.modifyMember(memberVO);
			memberVO = memberSVC.listOneMember(memberVO.getMember_id());
			map.put("result", "success");
			map.put("data", memberVO);
		}catch(Exception e) {
			map.put("result", "fail");
			map.put("data", null);			
		}
		return map;
	}
	//회원삭제
	@DeleteMapping("/member")
	public Map<String,Object> deleteMember(@RequestBody MemberVO memberVO) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
			memberSVC.outMember(memberVO.getMember_id(), memberVO.getPw());
			map.put("result", "success");
			map.put("data", memberVO);
		}catch(Exception e) {
			map.put("result", "fail");
			map.put("data", null);			
		}
		return map;
	}	
}






