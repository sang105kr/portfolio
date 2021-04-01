package com.kh.portfolio.board.svc;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;
import com.kh.portfolio.common.paging.PageCriteria;

public interface BoardSVC {

	/**
	 * 게시판 유효성 체크
	 * @param errors
	 * @return
	 */
	Map<String, String> valideHandling(Errors errors);

	/**
	 * 게시판 카테고리 읽어오기
	 * @return
	 */
	public List<BoardCategoryVO> getCategory();	
	
	/**
	 * 게시글 작성
	 * @param boardVO
	 * @return
	 */
	long write(BoardVO boardVO);	
	
	/**
	 * 게시글 목록
	 * @return
	 */
	Map<String,Object> list(int reqPage);	
	/**
	 * 게시글 목록 (검색어 포함)
	 * @param reqPage
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	Map<String,Object> list(int reqPage, String searchType, String keyword);	

	/**
	 * 게시글 보기(첨부파일 포함)
	 * @param bnum
	 * @return
	 */
	Map<String, Object> view(long bnum);
	
	/**
	 * 게시글 보기(첨부파일 제외)
	 * @param bnum
	 * @return
	 */
	BoardVO viewWithoutFiles(long bnum);
	
	/**
	 * 게시글 삭제 : 게시글과 첨부파일이 동시에 삭제 처리됨.
	 * @param bnum
	 */
	void delete(long bnum);
	/**
	 * 게시글 수정
	 * @param board
	 * @return
	 */
	int modify(BoardVO boardVO);	
	/**
	 * 첨부파일 개별 삭제
	 * @param fid
	 * @return
	 */
	int deleteFile(long fid);
	/**
	 * 답글 작성
	 * @param boardVO
	 */
	long reply(BoardVO boardVO);
	
	/**
	 * 첨부파일 다운로드
	 * @param bnum
	 * @return
	 */
	BoardUploadFileVO downloadFile(long fid);	
	/**
	 * 첨부파일 업로드(게시글 본문에 포함되는 이미지)
	 * @param bnum
	 * @return 이미지파일 경로
	 */
	String uploadFile(MultipartFile mfile);		
}


