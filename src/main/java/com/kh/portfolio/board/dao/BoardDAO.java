package com.kh.portfolio.board.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;

public interface BoardDAO {
	/**
	 * 게시판 카테고리 읽어오기
	 * @return
	 */
	List<BoardCategoryVO> getCategory();
	/**
	 * 게시글 작성
	 * @param boardVO
	 * @return
	 */
	long write(BoardVO boardVO);
	/**
	 * 첨부파일 저장
	 * @param list
	 */
	void addFile(List<BoardUploadFileVO> list);
	/**
	 * 게시글 목록
	 * @return
	 */
	//List<BoardVO> list();
	List<BoardVO> list(int startRec, int endRec);
	/**
	 * 게시글 목록
	 * @param startRec 시작레코드
	 * @param endRec	종료레코드
	 * @param searchType 검색유형
	 * @param keyWord	검색어
	 * @return 게시글 목록
	 */
	List<BoardVO> list(int startRec, int endRec, String searchType, String keyWord);
	
	/**
	 * 게시글 조회(by게시글)
	 * @param bnum
	 * @return
	 */
	BoardVO view(long bnum);
	/**
	 * 첨부파일 조회(by게시글)
	 * @param bnum
	 * @return
	 */
	List<BoardUploadFileVO> getFiles(long bnum);
	/**
	 * 첨부파일 다운로드
	 * @param bnum
	 * @return
	 */
	BoardUploadFileVO downloadFile(long fid);
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
	 * @return
	 */
	long reply(BoardVO boardVO);
	/**
	 * 조회수 증가
	 * @param bnum
	 * @return
	 */
	int updateBhit(long bnum);
	/**
	 * 게시판 전체 레코드 수
	 * @return
	 */
	long totalRecordCount(); 
	long totalRecordCount(String searchType, String keyWord); 
}
















