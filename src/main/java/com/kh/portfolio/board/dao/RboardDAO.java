package com.kh.portfolio.board.dao;

import java.util.List;

import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.VoteCntVO;
import com.kh.portfolio.board.vo.VoteVO;

public interface RboardDAO {
	//댓글작성
	int write(RboardVO rboardVO);
	
	//댓글수정
	int modify(RboardVO rboardVO);
	
	//댓글삭제
	int delete(long rnum);
	
	//댓글조회
	RboardVO replyView(long rnum);
	
	//댓글목록
	List<RboardVO> list(long bnum, long startRec, long endRec);
	
	//대댓글 작성
	int reply(RboardVO rboardVO);
	//이전댓글 step업데이트
	int updateStep(int rgroup, int rstep);
	
	//호감,비호감
	int vote(VoteVO voteVO);
	
	//호감,비호감 카운트
	VoteCntVO voteCnt(Long rnum);

	//총레코드
	int totalRecordCount(long bnum);
}
