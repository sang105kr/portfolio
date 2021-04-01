package com.kh.portfolio.member.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.portfolio.common.Code;
import com.kh.portfolio.common.svc.CodeSVC;
import com.kh.portfolio.member.svc.MemberSVC;
import com.kh.portfolio.member.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j //사용시 log.info(), log.error() 등..
public class MemberController {
	
//	private static final Logger logger
//		= LoggerFactory.getLogger(MemberController.class);
	
	private final MemberSVC memberSVC;
	private final CodeSVC codeSVC;
	
	@Autowired
	public MemberController(MemberSVC memberSVC, CodeSVC codeSVC) {
		this.memberSVC = memberSVC;
		this.codeSVC = codeSVC;
	}
	
	@ModelAttribute
	public void initData(Model model) {
		model.addAttribute("region", codeSVC.getCodesByCodetype("1000"));
		model.addAttribute("hobby", codeSVC.getCodesByCodetype("1010"));	
		
		//성별
		List<Code> gender = new ArrayList<>();
		gender.add(new Code("남","남자"));
		gender.add(new Code("여","여자"));
		model.addAttribute("gender", gender);		
	}
	
	//회원가입양식
	@GetMapping("/joinForm")
	public String joinForm(Model model) {
		
		model.addAttribute("memberVO", new MemberVO());
		return "/member/joinForm";
	}
	
	//회원가입처리
	@PostMapping("/join")
	public String join(
			@Valid MemberVO memberVO, 
			BindingResult errResult) {
		
		String viewName = null;
		int result = 0;
		
		log.info("memberVO : " + memberVO);
	
		//유효성체크 오류가 있다면
		if(errResult.hasErrors()) {
			log.info("join메소드 유효성 오류발생");
			return "/member/joinForm";
		}
		
		result = memberSVC.joinMember(memberVO);
		
		if(result == 1) {
			log.info("회원가입성공:" + memberVO);
			viewName = "redirect:/";
		}else {
			log.info("회원가입실패:" + memberVO);
			viewName = "/member/joinForm";
		}
		
		return viewName;
	}
	
	//내정보
	@GetMapping("/mypage")
	public String mypage() {
		
		return "/member/mypage";
	}
	
	//회원수정양식
	@GetMapping("/modifyForm")
	public String modifyForm(
			Model model,
			HttpSession session) {
		
		MemberVO memberVO = (MemberVO)session.getAttribute("member");
		//세션정보가 없으면 로그인 화면으로 이동
		if(memberVO == null) {
			return "redirect:/loginForm";
		}
		
		MemberVO member = memberSVC.listOneMember(memberVO.getMember_id());
		member.setPw("");
		
		model.addAttribute("memberVO", member);

		return "/member/modifyForm";
	}
	
	//회원수정처리
	@PostMapping("/modify")
	public String modify(
			@Valid MemberVO memberVO,
			BindingResult errResult,
			Model model, //포워드 방식으로 view에 전달시
			RedirectAttributes redirectAttrs, //리다이렉트 방식으로 view에 전달시
			HttpSession session
		) {
		String viewName = null;
		
		log.info("회원수정처리:" + memberVO.toString());
		
		//유효성체크 오류발생시
		if(errResult.hasErrors()) {
			return "/member/modifyForm";
		}
		
		int result = memberSVC.modifyMember(memberVO);
		//수정 성공
		if(result == 1 ) {
//			model.addAttribute("svr_msg", "회원정보가 수정되었습니다!"); //포워드
			
			//세션의 회원정보 갱신
			//현재 세션정보 제거
			session.removeAttribute("member");
			//새로운 정보로 세션생성
			session.setAttribute("member", memberSVC.listOneMember(memberVO.getMember_id()));
			
			redirectAttrs.addFlashAttribute("svr_msg", "회원정보가 수정되었습니다!"); //리다이렉트
			viewName = "redirect:/member/modifyForm";
		}else {
		//수정 실패	
			model.addAttribute("svr_msg", "회원정보가 수정 실패!");
			viewName = "/member/modifyForm";
		}
		return viewName;
	}
	
	//비밀번호 수정 양식
	@GetMapping("/changePWForm")
	public String changePWForm() {
		
		return "/member/changePWForm";
	}
	
	//비밀번호 변경 처리
	@PostMapping("/changePW")
	public String change(
			@RequestParam("member_id") String member_id,
			@RequestParam("currentpw") String currentpw, 
			@RequestParam("nextpw") String nextpw,
			Model model
			) {
		
		log.info("비밀번호변경:"+ member_id); 
		log.info("비밀번호변경:"+ currentpw); 
		log.info("비밀번호변경:"+ nextpw);
		
		String viewName = null;
		int result = memberSVC.changePw(member_id, currentpw, nextpw);
		//성공
		if(result == 1) {
			
			viewName = "redirect:/member/mypage";
		}else {
		//실패
			model.addAttribute("svr_msg", "비밀번호를 올바르게 입력하세요!");
			viewName = "/member/changePWForm";
		}
		return viewName;
	}
	
	//회원탈퇴양식	
	@GetMapping("/outMemberForm")
	public String outMemberForm() {
		
		
		return "/member/outMemberForm";
	}
	
	//회원탈퇴	/member/outMember
	@PostMapping("/outMember")
	public String outMember(
			@RequestParam("member_id") String member_id,
			@RequestParam("currentpw") String currentpw,
			Model model
			) {
		String viewName = null;
		
		int result = memberSVC.outMember(member_id, currentpw);
		//1)탈퇴성공
		if(result == 1) {
			
			//로그아웃 처리후 초기화면으로
			viewName = "redirect:../logout";
		}else {
		//2)탈퇴실패
			
			model.addAttribute("svr_msg", "비밀번호가 일치하지 않습니다!");
			viewName = "/member/outMemberForm";
		}	
		return viewName;
	}
	
	//회원 아이디 찾기
	@GetMapping("/findMemberForm")
	public String findMemberForm() {
		
		
		return "/member/findMemberForm";
	}
	//회원 비밀번호 찾기
	@GetMapping("/findPwForm")
	public String findPwForm() {
		
		
		return "/member/findPwForm";
	}
	//회원 이미지 조회
	@GetMapping("/findProfileImg/{member_id}/")
	public void findProfileImg(@PathVariable String member_id,HttpServletResponse res) {
		//res.setContentType("image/*");
		try {
			byte[] imageFile = memberSVC.findProfileImg(member_id);
			InputStream is = new ByteArrayInputStream(imageFile);
			IOUtils.copy(is, res.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




