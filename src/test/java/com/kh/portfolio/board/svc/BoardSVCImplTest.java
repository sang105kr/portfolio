package com.kh.portfolio.board.svc;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
@Slf4j					//로거 기록
@Transactional  //SVC에서는 commit , junit에서는 rollback처리됨
public class BoardSVCImplTest {

	//junit테스트 환경에서는 필드 주입방식 사용 권장!! 
	//테스트 이외 환경에서는 생성자 주입방식 사용 권장!!
	@Autowired  
	private BoardSVC boardSVC;
	
	@Test
	@DisplayName("게시글 목록")
	@Disabled
	void list() {
		int reqPage =1;
		((List<BoardVO>)(boardSVC.list(reqPage).get("boardList")))
														.stream().forEach(ele -> {
			log.info(ele.toString());
		});
	}
	
	@Test
	@DisplayName("게시글 보기")
	void view() {
		long bnum = 41;
		Map<String,Object> map = boardSVC.view(bnum);
		
		log.info(map.toString());
		
		BoardVO boardVO = (BoardVO)map.get("boardVO");
		log.info(boardVO.toString());
		
		List<BoardUploadFileVO> list = (List<BoardUploadFileVO>)map.get("files");
		log.info(list.toString());
	}
}











