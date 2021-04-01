package com.kh.portfolio.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.VoteCntVO;
import com.kh.portfolio.board.vo.VoteVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RboardDAOImplXML implements RboardDAO{

	private final SqlSession sqlSession; 
	
	//댓글작성
	@Override
	public int write(RboardVO rboardVO) {

		return sqlSession.insert("mappers.RboardDAO-mapper.write", rboardVO);
	}
	//댓글수정
	@Override
	public int modify(RboardVO rboardVO) {

		return sqlSession.update("mappers.RboardDAO-mapper.modify", rboardVO);
	}
	//댓글삭제
	@Override
	public int delete(long rnum) {

		return sqlSession.delete("mappers.RboardDAO-mapper.delete", rnum);
	}
	//댓글조회
	@Override
	public RboardVO replyView(long rnum) {

		return sqlSession.selectOne("mappers.RboardDAO-mapper.replyView",rnum);
	}
	//댓글목록
	@Override
	public List<RboardVO> list(long bnum, long startRec, long endRec) {
		Map<String,Long> map = new HashMap<>();
		map.put("bnum", bnum);
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		return sqlSession.selectList("mappers.RboardDAO-mapper.list", map);
	}
	//대댓글 작성
	@Override
	public int reply(RboardVO rboardVO) {
		return sqlSession.insert("mappers.RboardDAO-mapper.reply", rboardVO);
	}
	
	//이전댓글 step업데이트
	@Override
	public int updateStep(int rgroup, int rstep) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("rgroup", rgroup);
		map.put("rstep", rstep);
		return sqlSession.update("mappers.RboardDAO-mapper.updateStep",map);
	}
	
	//총레코드
	@Override
	public int totalRecordCount(long bnum) {
		return sqlSession.selectOne("mappers.RboardDAO-mapper.totalRecordCount", bnum);
	}

	//호감,비호감
	@Override
	public int vote(VoteVO voteVO) {
		return sqlSession.insert("mappers.RboardDAO-mapper.vote", voteVO);
	}
	
	//호감,비호감 카운트
	@Override
	public VoteCntVO voteCnt(Long rnum) {
		return sqlSession.selectOne("mappers.RboardDAO-mapper.voteCnt", rnum);
	}
}






