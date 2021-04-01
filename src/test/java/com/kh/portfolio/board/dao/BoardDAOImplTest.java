package com.kh.portfolio.board.dao;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
@Slf4j					//로거 기록
//@Transactional  //junit환경에서 @Transactional 읽기전용 트랙잭션으로만 사용된다.
public class BoardDAOImplTest {

	@Autowired
	private BoardDAO boardDAO;
	
	@Test
	@DisplayName("분류코드 가져오기")
	@Disabled
	void getCategory() {
		
		List<BoardCategoryVO> list = boardDAO.getCategory();
		
		list.stream().forEach(ele->{
			log.info(ele.toString());
		});
	}
	
	@Test
	@DisplayName("게시글작성")
	@Disabled
	void write() {
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardCategoryVO(new BoardCategoryVO(1020, "JAVA"));
		boardVO.setBtitle("제목2");
		boardVO.setBid("test@test.com");
		boardVO.setBnickname("테스터2");
		boardVO.setBcontent("내용2");
	
		Assertions.assertNotEquals(0, boardDAO.write(boardVO));
	}
	@Test
	@DisplayName("게시글작성샘플300개생성")
	void write2() {
		
		for(int i=0; i<300; i++) {
			BoardVO boardVO = new BoardVO();
			boardVO.setBoardCategoryVO(new BoardCategoryVO(1020, "JAVA"));
			boardVO.setBtitle("제목:" + (i+1));
			boardVO.setBid("test@test.com");
			boardVO.setBnickname("테스터2");
			boardVO.setBcontent("내용"+ (i+1));
			
			boardDAO.write(boardVO);
		}
		//Assertions.assertNotEquals(0, boardDAO.write(boardVO));
	}	
	
//	@Test
//	@DisplayName("시퀀스번호생성")
//	@Disabled
//	void getNextVal() {
//		long num = ((BoardDAOImpl)boardDAO).getNextVal("board_bnum_seq");
//		log.info(String.valueOf(num));
//	}
	
	@Test
	@DisplayName("게시글목록")
	@Disabled
	void list() {
		int startRec = 1;
		int endRec = 10;
		List<BoardVO> list= boardDAO.list(startRec,endRec);
		
//		//case1) 일반 for문
//		for(int i=0; i<list.size(); i++) {
//			log.info(list.get(i).toString());
//		}
//		//case2) 향상된 for문
//		for(BoardVO boardVO : list) {
//			log.info(boardVO.toString());
//		}
		//case3) 스트림+람다식
		list.stream().forEach(ele->{
			log.info(ele.toString());
		});
	}
	
	@Test
	@DisplayName("게시글조회")
	@Disabled
	void view() {
		
		long bnum = 81;
		BoardVO boardVO = boardDAO.view(bnum);
		
		log.info("게시글조회테스트 boardVO : " + boardVO.toString());
		Assertions.assertEquals(bnum, boardVO.getBnum());
	}
	
	@Test
	@DisplayName("첨부파일조회")
	@Disabled
	void getFiles() {
		
		long bnum = 81;
		List<BoardUploadFileVO> list = boardDAO.getFiles(bnum);
		
		list.stream().forEach(file->{
			log.info(file.toString());
		});
		
		Assertions.assertEquals(1, list.size());
	
	}
	
	@Test
	@Disabled
	void delete() {
		long bnum = 81;
		boardDAO.delete(bnum);
		Assertions.assertThrows(
				EmptyResultDataAccessException.class,  //예상되는 예외 타입
				()-> {																 //실행코드를 람다식으로 작성
					boardDAO.view(bnum);
				});
	}
	
	@Test
	@DisplayName("게시글수정")
	@Disabled
	void modify() {
		BoardVO boardVO = new BoardVO();
		BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
		boardVO.setBoardCategoryVO(boardCategoryVO);
		
		boardVO.getBoardCategoryVO().setCid(1030);
		boardVO.setBtitle("제목수정2");
		boardVO.setBcontent("내용수정2");
		boardVO.setBnum(3);
		
		Assertions.assertEquals(1, boardDAO.modify(boardVO));
	}
	
	@Test
	@DisplayName("첨부파일 개별삭제")
	@Disabled
	void deleteFile() {
		long fid = 84 ;
		
		Assertions.assertEquals(1, boardDAO.deleteFile(fid));
	}
	
	@Test
	@DisplayName("조회수 증가")
	@Disabled
	void updateBhit() {
		long bnum = 170;
		
		//170번 게시글의 현재조회수
		int beforeBhit = boardDAO.view(bnum).getBhit();
		boardDAO.updateBhit(bnum);
		int afterBhit = boardDAO.view(bnum).getBhit();
		Assertions.assertEquals(beforeBhit+1, afterBhit);
		
	}
}





