package com.kh.portfolio.board.svc;

import java.util.Map;

import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.VoteCntVO;
import com.kh.portfolio.board.vo.VoteVO;

public interface RboardSVC {
	//댓글작성
	int write(RboardVO rboardVO);
	
	//댓글수정
	int modify(RboardVO rboardVO);
	
	//댓글삭제
	int delete(long rnum);
	
	//댓글조회
	RboardVO replyView(long rnum);
	
	//댓글목록
	Map<String,Object> list(long bnum, int reqPage);
	
	//대댓글 작성
	int reply(RboardVO rboardVO);
	
	//호감,비호감
	VoteCntVO vote(VoteVO voteVO);

	//총레코드
	int totalRecordCount(long bnum);	
}
