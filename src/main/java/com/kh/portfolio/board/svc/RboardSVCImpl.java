package com.kh.portfolio.board.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.portfolio.board.dao.RboardDAO;
import com.kh.portfolio.board.vo.RboardVO;
import com.kh.portfolio.board.vo.VoteCntVO;
import com.kh.portfolio.board.vo.VoteVO;
import com.kh.portfolio.common.paging.PageCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RboardSVCImpl implements RboardSVC{

	private final RboardDAO rboardDAO;
	private final PageCriteria pc;
	
	//댓글 작성
	@Override
	public int write(RboardVO rboardVO) {
		return rboardDAO.write(rboardVO);
	}
	//댓글수정
	@Override
	public int modify(RboardVO rboardVO) {

		return rboardDAO.modify(rboardVO);
	}
	//댓글삭제
	@Override
	public int delete(long rnum) {

		return rboardDAO.delete(rnum);
	}
	//댓글조회
	@Override
	public RboardVO replyView(long rnum) {

		return rboardDAO.replyView(rnum);
	}
	//댓글목록
	@Override
	public Map<String,Object> list(long bnum, int reqPage) {

		Map<String,Object> map = new HashMap<>();

		//1)요청페이지
		pc.getRc().setReqPage(reqPage);
		//2)총레코드정보
		pc.setTotalRec(rboardDAO.totalRecordCount(bnum));
		//3)페이징계산
		pc.calculatePaging();
		
		//4)목록가져오기
		int startRec  = pc.getRc().getStartRec();
		int endRec 		= pc.getRc().getEndRec();
		List<RboardVO> list = rboardDAO.list(bnum, startRec, endRec);
		
		//5)map객체에 게시글 목록, pc담기
		map.put("list", list);
		map.put("pc",pc);
		
		return map;		
	}
	//대댓글 작성
	@Transactional(readOnly = false)
	@Override
	public int reply(RboardVO rboardVO) {
		//1)부모글 정보 읽어오기
		RboardVO parentRboradVO  = rboardDAO.replyView(rboardVO.getPrnum());
		//2)이전댓글 step업데이트
		rboardDAO.updateStep(parentRboradVO.getRgroup(), parentRboradVO.getRstep());
		//3)대댓글작성
		rboardVO.setBnum(parentRboradVO.getBnum());
		rboardVO.setRgroup(parentRboradVO.getRgroup());
		rboardVO.setRstep(parentRboradVO.getRstep());
		rboardVO.setRindent(parentRboradVO.getRindent());
		//4)부모댓글 아이디
		rboardVO.setPrid(parentRboradVO.getRid());
		//5)부모댓글 별칭
		rboardVO.setPrnickname(parentRboradVO.getRnickname());

		return 	rboardDAO.reply(rboardVO);
	}
	//호감,비호감
	@Transactional(readOnly = false)
	@Override
	public VoteCntVO vote(VoteVO voteVO) {
		rboardDAO.vote(voteVO);		
		return rboardDAO.voteCnt(Long.valueOf(voteVO.getRnum()));
	}
	//총레코드
	@Override
	public int totalRecordCount(long bnum) {

		return rboardDAO.totalRecordCount(bnum);
	}
}
