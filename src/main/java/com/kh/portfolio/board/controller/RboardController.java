package com.kh.portfolio.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.portfolio.board.svc.RboardSVC;
import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.VoteCntVO;
import com.kh.portfolio.board.vo.VoteVO;
import com.kh.portfolio.exception.ErrorMsg;
import com.kh.portfolio.exception.RestAccessException;
import com.kh.portfolio.member.vo.MemberVO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController //@Controller + @ResponseBody
@Slf4j // log.info()사용하기위함
@RequestMapping("/rboard")
@RequiredArgsConstructor
public class RboardController {

	public final RboardSVC rboardSVC;
	
	//댓글작성
	@PostMapping(
			value="",
			//http요청헤더가 "Content-Type:application;/json;charset=UTF-8인 것만 처리
			consumes = "application/json",  
			//http응답헤더가 "Content-Type:application;/json;charset=UTF-8을 반환
			produces = "application/json")
	public ResponseEntity<Map<String, String>> write(
			@Valid // 데이터 바인딩할때 유효성체크하겠다. 
			@RequestBody RboardVO rboardVO,  //@RequestBody http요청 본문을 자바객체에 바인딩하겠다
			BindingResult result,	//@Valid를 통해 유효성체크시 오류정보를 저장하는 객체
			HttpServletRequest request //client요청 정보를 갖고있는 객체
			){
		ResponseEntity<Map<String, String>> res = null;
		
		log.info("RboardVO:"+rboardVO);
		if(result.hasErrors()) {
			log.info("result:"+result);
			throwRestAccessException(result);
		}
		
		rboardSVC.write(rboardVO);
		
		Map<String,String> map = new HashMap<>();
		map.put("rtcd", "00");
		map.put("rtmsg", "댓글등록!");
		
		res = new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
		
		return res;
	}

	//목록가져오기
	@GetMapping(value={"/{bnum}","/{bnum}/{reqPage}"},
							produces = "application/json")
	public ResponseEntity<Map<String, Object>> list(
			@PathVariable(required = false) Optional<Integer> reqPage,
			@PathVariable long bnum			
			){
		ResponseEntity<Map<String, Object>> res = null;		
		Map<String,Object> map = new HashMap<>();
		
		map.put("rtcd", "00");
		map.put("rtmsg", rboardSVC.list(bnum, reqPage.orElse(1)));
				
		res = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		return res;
	}
	
	@Data
	static class ModifyVO{
		@NotBlank(message="댓글번호가 누락되었습니다!")
		private String rnum;
		@NotBlank(message = "댓글작성자 아이디가 누락되었습니다!")
		@Email(message = "작성자 아이디는 이메일 형식 이어야합니다! ex)aaa@bbb.com")	
		private String rid;		
		@NotBlank(message = "내용을 누락되었습니다!")
		private String rcontent;		
	}
	
	//수정하기
	@PatchMapping(value="",
							consumes = "application/json",
							produces = "application/json")
	public ResponseEntity<Map<String,Object>> modify(
			@Valid @RequestBody ModifyVO modifyVO,
			BindingResult result			
			){
		ResponseEntity<Map<String,Object>> res = null;
		log.info("댓글수정->{}",modifyVO);
		if(result.hasErrors()) {
			log.info("result:"+result);
			throwRestAccessException(result);
		}		
		
		RboardVO rboardVO = new RboardVO();
		rboardVO.setRnum(Long.valueOf(modifyVO.getRnum()));
		rboardVO.setRid(modifyVO.getRid());
		rboardVO.setRcontent(modifyVO.getRcontent());
		
		rboardSVC.modify(rboardVO);
		
		Map<String, Object> map = new HashMap<>();
		map.put("rtcd", "00");
		map.put("rtmsg", "댓글수정!");
		
		res = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		return res;
	}
	
	//삭제
	@DeleteMapping(value = "/{rnum}",produces = "application/json")
	public ResponseEntity<Map<String,Object>> delete(
			@PathVariable long rnum){
		ResponseEntity<Map<String,Object>> res = null;
		
		int result = rboardSVC.delete(rnum);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> map = new HashMap<>();
		if(result == 1) {
			map.put("rtcd", "00");
			map.put("rtmsg", "댓글삭제!");
			res = new ResponseEntity<Map<String,Object>>(map,responseHeaders, HttpStatus.OK);		
		}else {
			map.put("rtcd", "01");
			map.put("rtmsg", "삭제대상 댓글이 없습니다.");		
			res = new ResponseEntity<Map<String,Object>>(map,responseHeaders, HttpStatus.NOT_FOUND);					
		}
		
		return res;
	}
	
	//대댓글등록
	@PostMapping(value="/reply",
							 consumes = "application/json",
							 produces = "application/json")
	public ResponseEntity<Map<String,String>> reply(
			@Valid @RequestBody RboardVO rboardVO,
			BindingResult result,
			HttpServletRequest request
			){
		
		ResponseEntity<Map<String, String>> res = null;
		
		log.info("RboardVO:"+rboardVO);
		if(result.hasErrors()) {
			log.info("result:"+result);
			throwRestAccessException(result);
		}
		
		Map<String,String> map = new HashMap<>();
		
		//접속 클라이언트로부터 세션정보를 가져온다.
		HttpSession session = request.getSession(false); 
		if(session != null ) {
			
			MemberVO memberVO = (MemberVO)session.getAttribute("member");
			
			if(memberVO.getMember_id().equals(rboardVO.getRid())) {					
				rboardSVC.reply(rboardVO);
				map.put("rtcd", "00");
				map.put("rtmsg", "대댓글등록!");				
				res = new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
				
			}else {
				map.put("rtcd", "02");
				map.put("rtmsg", "정상적인 접근이 아닙니다!");
				res = new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
			}
			
		}else {
			map.put("rtcd", "01");
			map.put("rtmsg", "세션정보가 없습니다.");
			res = new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
		}
		
		return res;				
	}
	//호감, 비호감
	@PutMapping(value="/vote", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String,Object>> vote(
			@Valid @RequestBody VoteVO voteVO,
			BindingResult result,
			HttpServletRequest request
			){
		ResponseEntity<Map<String,Object>> res = null;
		
		if(result.hasErrors()) {
			log.info("result:"+result);
			throwRestAccessException(result);
		}
		
		Map<String,Object> map = new HashMap<>();
		//접속 클라이언트로부터 세션정보를 가져온다.
		HttpSession session = request.getSession(false); 
		if(session != null ) {
			
			MemberVO memberVO = (MemberVO)session.getAttribute("member");
			
			if(memberVO.getMember_id().equals(voteVO.getRid())) {					
				VoteCntVO voteCntVO = rboardSVC.vote(voteVO);
				map.put("rtcd", "00");
				map.put("rtmsg", voteCntVO);				
				res = new ResponseEntity<>(map,HttpStatus.OK);
				
			}else {
				map.put("rtcd", "02");
				map.put("rtmsg", "정상적인 접근이 아닙니다!");
				res = new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			}
			
		}else {
			map.put("rtcd", "01");
			map.put("rtmsg", "세션정보가 없습니다.");
			res = new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}		
		
//		rboardSVC.vote(voteVO);
//		map.put("rtcd", "00");
//		map.put("rtmsg", "선호도 등록!");				
//		res = new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
		return res;
	}
	
	//vo객체에 바인딩 오류에 대한 예외 처리
	private void throwRestAccessException(BindingResult result) {
		List<FieldError> list = result.getFieldErrors();
		List<ErrorMsg> errList = new ArrayList<>();
		
		for(FieldError item : list) {
			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setFieldName(item.getField());
			errorMsg.setRequestValue((String)item.getRejectedValue());
			errorMsg.setErrMsg(item.getDefaultMessage());
			errList.add(errorMsg);
		}
		
		throw new RestAccessException(errList);
	}
}











