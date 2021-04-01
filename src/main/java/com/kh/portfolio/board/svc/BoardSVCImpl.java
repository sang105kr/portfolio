package com.kh.portfolio.board.svc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.kh.portfolio.board.dao.BoardDAO;
import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;
import com.kh.portfolio.common.paging.PageCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //읽기전용 트랙잭션
@Slf4j
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	private final PageCriteria pc;
	
	
	/**
	 * 게시판 유효성 체크
	 * @param errors
	 * @return
	 */
	@Override
	public Map<String,String> valideHandling(Errors errors){
		
		Map<String,String> map = new HashMap<>();
		
		for(FieldError error : errors.getFieldErrors()) {
			String key = String.format("valid_%s", error.getField());
			String value = error.getDefaultMessage();
			map.put(key, value);
		}
		
		return map;
	}
	/**
	 * 게시판 카테고리 읽어오기
	 * @return
	 */
	@Override
	public List<BoardCategoryVO> getCategory() {
		return boardDAO.getCategory();
	}
	/**
	 * 게시글 작성
	 * @param boardVO
	 * @return
	 */
	@Transactional(readOnly = false)
	@Override
	public long write(BoardVO boardVO) {

		//1)게시글 작성
		long bnum = boardDAO.write(boardVO);

		//2)첨부파일 저장
		List<MultipartFile> files = boardVO.getFiles();
		if( files !=null) {
			
			addFiles(files,bnum);
		}
		return bnum; 
	}
	
	private void addFiles(List<MultipartFile> files,long bnum) {
		
	  //파일사이즈가 0인건은 제외
		long cnt  = files.stream()
	 		 						 .filter( file -> file.getSize() > 0)
				 					 .count();
		if(cnt == 0) return ;	
		
		List<BoardUploadFileVO> list = new ArrayList<>();
		
		for(MultipartFile file : files) {
			BoardUploadFileVO boardUploadFileVO = new BoardUploadFileVO();
			//게시글번호
			boardUploadFileVO.setBnum(bnum);
			//파일명
			boardUploadFileVO.setFname(file.getOriginalFilename());
			//파일크기
			boardUploadFileVO.setFsize(String.valueOf(file.getSize()));
			//파일유형
			boardUploadFileVO.setFtype(file.getContentType());
			//첨부파일
			try {
				boardUploadFileVO.setFdata(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			list.add(boardUploadFileVO);
		}
		//첨부파일저장
		if(list.size() > 0) {
			boardDAO.addFile(list);
		}
	}
	/**
	 * 게시글 목록
	 * @return
	 */
	@Override
	public Map<String,Object> list(int reqPage) {
		
		Map<String,Object> map = new HashMap<>();

		//1)요청페이지
		pc.getRc().setReqPage(reqPage);
		//2)총레코드정보
		pc.setTotalRec(boardDAO.totalRecordCount());
		//3)페이징계산
		pc.calculatePaging();
		
		//4)목록가져오기
		int startRec  = pc.getRc().getStartRec();
		int endRec 		= pc.getRc().getEndRec();
		List<BoardVO> list = boardDAO.list(startRec, endRec);
		
		//5)map객체에 게시글 목록, pc담기
		map.put("boardList", list);
		map.put("pc",pc);
		
		return map;
	}
	/**
	 * 게시글 목록 (검색어 포함)
	 * @param reqPage
	 * @param searchType
	 * @param keyword
	 * @return
	 */	
	@Override
	public Map<String, Object> list(int reqPage, String searchType, String keyword) {
		Map<String,Object> map = new HashMap<>();

		//1)요청페이지
		pc.getRc().setReqPage(reqPage);
		//2)총레코드정보
		pc.setTotalRec(boardDAO.totalRecordCount(searchType,keyword));
		//3)페이징계산
		pc.calculatePaging();
		
		//4)목록가져오기
		int startRec  = pc.getRc().getStartRec();
		int endRec 		= pc.getRc().getEndRec();
		List<BoardVO> list = boardDAO.list(startRec, endRec, searchType, keyword);
		
		//5)map객체에 게시글 목록, pc담기
		map.put("boardList", list);
		map.put("pc",pc);
		
		return map;
	}
	/**
	 * 게시글 보기(첨부파일 포함)
	 * @param bnum
	 * @return
	 */	
	@Transactional(readOnly = false)
	@Override
	public Map<String, Object> view(long bnum) {
		
		Map<String,Object> map = new HashMap<>();
		
		//1)게시글 가져오기
		BoardVO boardVO = boardDAO.view(bnum);
		//2)첨부파일 가져오기
		List<BoardUploadFileVO> files = boardDAO.getFiles(bnum);
		//3)조회수 증가
		boardDAO.updateBhit(bnum);
		
		map.put("boardVO", boardVO);
		map.put("files", files);
		
		return map;
	}
	
	/**
	 * 게시글 보기(첨부파일 제외)
	 * @param bnum
	 * @return
	 */	
	@Override
	public BoardVO viewWithoutFiles(long bnum) {
		
		return boardDAO.view(bnum);
	}
	/**
	 * 게시글 삭제 : 게시글과 첨부파일이 동시에 삭제 처리됨.
	 * @param bnum
	 */	
	@Override
	@Transactional(readOnly = false)
	public void delete(long bnum) {
		boardDAO.delete(bnum);
	}
	
	/**
	 * 게시글 수정
	 * @param board
	 * @return
	 */	
	@Transactional(readOnly = false)
	@Override
	public int modify(BoardVO boardVO) {
		//1)게시글 수정
		int result = boardDAO.modify(boardVO);
		log.info("수정시 첨부파일:"+boardVO.getFiles().size());
		//2)첨부파일 추가
		List<MultipartFile> files = boardVO.getFiles();
		if( files != null ) { 
			addFiles(files,boardVO.getBnum());
		}
		return result;
	}
	/**
	 * 첨부파일 개별 삭제
	 * @param fid
	 * @return
	 */	
	@Override
	public int deleteFile(long fid) {
		
		return boardDAO.deleteFile(fid);
	}	
	/**
	 * 답글 작성
	 * @param boardVO
	 */	
	@Transactional(readOnly = false)
	@Override
	public long reply(BoardVO boardVO) {
		//1)답글 작성
		long bnum = boardDAO.reply(boardVO);

		//2)첨부파일 저장
		List<MultipartFile> files = boardVO.getFiles();
		if( files !=null) {
			
			addFiles(files,bnum);
		}
		return bnum; 
	}
	
	/**
	 * 첨부파일 다운로드
	 * @param bnum
	 * @return
	 */	
	@Override
	public BoardUploadFileVO downloadFile(long fid) {
		return boardDAO.downloadFile(fid);
	}
	
	/**
	 * 첨부파일 업로드(게시글 본문에 포함되는 이미지)
	 * @param bnum
	 * @return
	 */	
	@Override
	public String uploadFile(MultipartFile mfile) {
		String path = ""; //이미지 파일 경로
		
		String randomFN = null; //내부관리 파일명(난수파일명)
		String originFN = null;	//사용자upload 파일명
		//첨부파일이 실제 저장되는 위치 지정
		final String fileLocation 
			//= "D:\\javaedu\\test\\portfolio\\src\\main\\webapp\\WEB-INF\\resources\\upload\\img";
		  = "D:\\javaedu\\tomcat9\\wtpwebapps\\portfolio\\WEB-INF\\resources\\upload\\img";
		randomFN = UUID.randomUUID().toString(); //중복없는 파일명생성 위함.
		originFN = mfile.getOriginalFilename();
		
		//초기파일명에서 확장자추출
		int pos = originFN.lastIndexOf(".");
		String ext = originFN.substring(pos);
		randomFN = randomFN + ext;
		
		//메모리상의 파일을 파일객체로변환
		File tmpFile = new File(fileLocation, randomFN);
		
		//실제 물리적인 파일로 지정된 위치에 파일 생성
		try {
			// 파일시스템에 파일쓰기
			mfile.transferTo(tmpFile);
			path = "http://localhost:9080/portfolio/upload/img/"+randomFN;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}















