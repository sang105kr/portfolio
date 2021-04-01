package com.kh.portfolio.board.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.portfolio.board.svc.BoardSVC;
import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;
import com.kh.portfolio.common.paging.PageCriteria;
import com.kh.portfolio.member.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	public final BoardSVC boardSVC;
	
	//현재 컨트롤러에서 만들어지는 view페이지내에서 boardCategoryVO 이름으로 참조가 가능
	@ModelAttribute("boardCategoryVO")
	public List<BoardCategoryVO> getCategory(){
		return boardSVC.getCategory();
	}
	
	/**
	 * 게시글 작성
	 * @return
	 */
	@GetMapping("/writeForm")
	public String writeForm(Model model) {

		return "/board/writeForm";
	}
	/**
	 * 게시글 처리
	 * @param boardVO
	 * @return
	 */
	@PostMapping("/write")
	public String write(
			@Valid BoardVO boardVO,
			BindingResult result,			//유효성체크시 오류내용을 담고있는 객체
			Model model,
			HttpSession session
			) {
		log.info("boardVO:"+boardVO);
		
		if( boardVO.getBid() == null || 
				boardVO.getBid().trim().length() == 0) {
			MemberVO m = (MemberVO)session.getAttribute("member");
			boardVO.setBid(m.getMember_id());
			boardVO.setBnickname(m.getNickname());
		}
		
		model.addAttribute("boardVO", boardVO);
		//유효성 체크 오류시
		if(result.hasErrors()) {
			
			Map<String,String> map = boardSVC.valideHandling(result);
			model.addAttribute("svr_msg", map);
			log.info("map:==>"+map);
			log.info("map:==>"+model);
			return "/board/writeForm";	
		}
		
		//게시글 저장
		boardSVC.write(boardVO);
		
		//게시글 저장후 목록으로
		return "redirect:/board/list";
	}
	/**
	 * 게시글 목록
	 * @return
	 */
	@GetMapping({ "/list",
							  "/list/{reqPage}",
							  "/list/{reqPage}/{searchType}/{keyword}"})
	public String list(
			@PathVariable(required = false) Optional<Integer> reqPage, 
			@PathVariable(required = false) String searchType,
			@PathVariable(required = false) String keyword,
			Model model) {
		
		log.info("reqPage="+reqPage);
		log.info("searchType="+searchType);
		log.info("keyword="+keyword);
		
		Map<String,Object> map = null;
		if(searchType != null && keyword !=null) {
			//검색어 있는 경우
			log.info("검색어 있는 경우");
			map	= boardSVC.list(reqPage.orElse(1),searchType,keyword);
		}else {
			//검색어 없는 경우
			log.info("검색어 없는 경우");
			map = boardSVC.list(reqPage.orElse(1));
		}
		
		//게시글 목록
		model.addAttribute("boardList", (List<BoardVO>)(map.get("boardList")));		
		//페이징 정보
		model.addAttribute("pc", (PageCriteria)(map.get("pc")));		
		
		return "/board/list";
	}
	
	@GetMapping("/view/{bnum}")
	public String view(
			@PathVariable(name = "bnum") long bnum,
			Model model) {
		
		Map<String,Object> map = boardSVC.view(bnum);
		BoardVO boardVO = (BoardVO)map.get("boardVO");
		List<BoardUploadFileVO> files 
			= (List<BoardUploadFileVO>)map.get("files"); 
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("files", files);
		
		return "/board/readForm";
	}
	/**
	 * 게시글 삭제
	 * @param bnum
	 * @return
	 */
	@GetMapping("/delete/{bnum}")
	public String delete(@PathVariable("bnum") long bnum) {
		
		log.info("게시글 삭제번호=>"+bnum);
		
		boardSVC.delete(bnum);
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(
			@Valid BoardVO boardVO, 
			Model model,
			BindingResult result) {
		
		//바인딩시 오류 발생할경우
		if(result.hasErrors()) {
			Map<String,String> map = boardSVC.valideHandling(result);
			model.addAttribute("svr_msg", map);
			log.info("map:==>"+map);
			log.info("map:==>"+model);			
			return "/board/readForm";
		}
		
		boardSVC.modify(boardVO);
		
		return "redirect:/board/view/"+boardVO.getBnum();
	}
		
	@DeleteMapping("/file/{fid}")
	@ResponseBody //view없이 data만 응답메세지body에 포함해 보내겠다는 의미
	public String deleteFile(@PathVariable long fid) {

		String status = null;
		int result = boardSVC.deleteFile(fid);
		
		if(result == 1) {
			status = "ok";
		}else {
			status = "nok";
		}
		return status;
	}
	
	//답글양식
	@GetMapping("/replyForm/{bnum}")
	public String replyForm(
			@PathVariable long bnum,
			Model model) {
		
		model.addAttribute("boardVO", boardSVC.viewWithoutFiles(bnum));
		
		return "/board/replyForm";
	}
	//답글 처리
	@PostMapping("/reply")
	public String reply(
			@Valid BoardVO boardVO,
			BindingResult result,			//유효성체크시 오류내용을 담고있는 객체
			Model model,
			HttpSession session
			) {
		log.info("boardVO:"+boardVO);
		
		if( boardVO.getBid() == null || 
				boardVO.getBid().trim().length() == 0) {
			MemberVO m = (MemberVO)session.getAttribute("member");
			boardVO.setBid(m.getMember_id());
			boardVO.setBnickname(m.getNickname());
		}
		
		model.addAttribute("boardVO", boardVO);
		//유효성 체크 오류시
		if(result.hasErrors()) {
			
			Map<String,String> map = boardSVC.valideHandling(result);
			model.addAttribute("svr_msg", map);
			log.info("map:==>"+map);
			log.info("map:==>"+model);
			return "/board/replyForm";	
		}
		
		//답글 저장
		boardSVC.reply(boardVO);
		
		//게시글 저장후 목록으로
		return "redirect:/board/list";
	}

	@GetMapping("/file/{fid}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable long fid) {
		ResponseEntity<byte[]> res = null;
		
		BoardUploadFileVO boardUploadFileVO = boardSVC.downloadFile(fid);
		
		//응답헤더에 mymeType과 파일 사이즈 정보 설정
		final HttpHeaders headers = new HttpHeaders();
		String[] mymeTypes = boardUploadFileVO.getFtype().split("/");
		headers.setContentType(new MediaType(mymeTypes[0],mymeTypes[1]));
		headers.setContentLength(Long.valueOf(boardUploadFileVO.getFsize()));
		
		//첨부파일명 한글깨짐을 방지하기위해 UTF-8 => ISO-8859-1로 변환
		String filename = null;
		try {
			filename = new String(boardUploadFileVO.getFname().getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//응답헤더에 첨부파일 있음을 알림
		headers.setContentDispositionFormData("attachmemt", filename);
		
		res = new ResponseEntity<byte[]>(boardUploadFileVO.getFdata(),headers,HttpStatus.OK);
		return res;
	}

	@PostMapping(value="/image/upload",produces = "application/json")
	@ResponseBody
	public Map<String,String> imageUpload(@RequestBody MultipartFile upload) {
		
		String path = boardSVC.uploadFile(upload);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("url", path);
		return map;
	}
}


















