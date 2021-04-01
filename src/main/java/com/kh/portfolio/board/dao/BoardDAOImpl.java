package com.kh.portfolio.board.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardUploadFileVO;
import com.kh.portfolio.board.vo.BoardVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor //멤버필드중 final멤버만 매개변수로 갖는 생성자 자동생성
@Slf4j
public class BoardDAOImpl implements BoardDAO {

	private final JdbcTemplate jt;
	
	/**
	 * 게시판 카테고리 읽어오기
	 * @return
	 */
	@Override
	public List<BoardCategoryVO> getCategory() {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select cid, cname  ");
		sql.append("  from board_category ");
		sql.append(" where use_yn = 'y' ");		
		
		//자동 매핑
		List<BoardCategoryVO> list = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<BoardCategoryVO>(BoardCategoryVO.class)
		);
		
//		//수동매핑
//		List<BoardCategoryVO> list = jt.query(sql.toString(), new RowMapper<BoardCategoryVO>() {
//
//			@Override
//			public BoardCategoryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//				
//				BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
//				boardCategoryVO.setCid(rs.getString("cid"));
//				boardCategoryVO.setCname(rs.getString("cname"));
//				
//				return boardCategoryVO;
//			}
//		});
		
		return list;
	}
	/**
	 * 게시글 작성
	 * @param boardVO
	 * @return
	 */
	@Override
	public long write(BoardVO boardVO) {
		//게시글 생성 번호 가져오기
		long bnum = getNextVal("board_bnum_seq");
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO board ( ");
		sql.append("    bnum, ");
		sql.append("    bcategory,  ");
		sql.append("    btitle, ");
		sql.append("    bid,  ");
		sql.append("    bnickname,  ");
		sql.append("    bcontent, ");
		sql.append("    bgroup, ");
		sql.append("    bstep,  ");
		sql.append("    bindent ");
		sql.append(") VALUES (  ");
		//sql.append("    board_bnum_seq.nextval, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?,  ");
		sql.append("    ?,  ");
		sql.append("    ?, ");
		sql.append("    ?, ");		
		//sql.append("    board_bnum_seq.currval, ");
		sql.append("    0,  ");
		sql.append("    0 ");
		sql.append(")  ");
		
		jt.update(
				sql.toString(), 
				bnum,
				boardVO.getBoardCategoryVO().getCid(),
				boardVO.getBtitle(),
				boardVO.getBid(),
				boardVO.getBnickname(),
				boardVO.getBcontent(),
				bnum);
		
		return bnum;
	}
	
	/**
	 * 다음 시퀀스번호 가져오기
	 * @return
	 */
	private long getNextVal(String sequence) {		
		String sql = String.format("select %s.nextval from dual", sequence);
		return jt.queryForObject(sql, Long.class);
	}
	

	@Override
	public void addFile(List<BoardUploadFileVO> list) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO board_upload_file ( ");
		sql.append("    fid, ");
		sql.append("    bnum, ");
		sql.append("    fname, ");
		sql.append("    fsize, ");
		sql.append("    ftype, ");
		sql.append("    fdata ");
		sql.append(") VALUES ( ");
		sql.append("    board_upload_file_seq.nextval, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ? ");
		sql.append(") ");		
		
		jt.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, list.get(i).getBnum());
					ps.setString(2, list.get(i).getFname());
					ps.setString(3, list.get(i).getFsize());
					ps.setString(4, list.get(i).getFtype());
					ps.setBytes(5, list.get(i).getFdata());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
			
		});			
	}
	/**
	 * 게시글 목록
	 * @return
	 */	
	@Override
	public List<BoardVO> list(int startRec, int endRec) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select t3.bnum,   ");
		sql.append("       t3.cid   as \"boardCategoryVO.cid\",   ");
		sql.append("       t3.cname as \"boardCategoryVO.cname\", ");		
		sql.append("       t3.btitle, ");
		sql.append("       t3.bid,    ");
		sql.append("       t3.bnickname,   ");
		sql.append("       t3.bcdate, ");
		sql.append("       t3.bhit,    ");
		sql.append("       t3.bgroup,  ");
		sql.append("       t3.bstep,   ");
		sql.append("       t3.bindent  ");		
		sql.append("from(   ");
		sql.append("    select row_number() over (order by t1.bgroup desc, t1.bstep asc) as num,  ");
		sql.append("           t1.bnum,    ");
		sql.append("           t2.cid,  ");
		sql.append("           t2.cname, ");
		sql.append("           t1.btitle,  ");
		sql.append("           t1.bid,     ");
		sql.append("           t1.bnickname,    ");
		sql.append("           t1.bcdate,  ");
		sql.append("           t1.budate,  ");
		sql.append("           t1.bhit,    ");
		sql.append("           t1.bgroup,  ");
		sql.append("           t1.bstep,   ");
		sql.append("           t1.bindent  ");
		sql.append("      from board t1, board_category t2     ");
		sql.append("     where t1.bcategory = t2.cid) t3  ");
		sql.append("where t3.num between ? and ?   ");
		
		 List<BoardVO> list = jt.query(
				sql.toString(), 
				new RowMapper<BoardVO>() {
					@Override
					public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						BoardVO boardVO = new BoardVO();
						
						boardVO.setBnum(rs.getLong("bnum"));
						boardVO.setBtitle(rs.getString("btitle"));
						boardVO.setBid(rs.getString("bid"));
						boardVO.setBnickname(rs.getString("bnickname"));
						boardVO.setBcdate(rs.getTimestamp("bcdate"));
						boardVO.setBhit(rs.getInt("bhit"));
						boardVO.setBgroup(rs.getInt("bgroup"));
						boardVO.setBstep(rs.getInt("bstep"));
						boardVO.setBindent(rs.getInt("bindent"));
						
						BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
						boardCategoryVO.setCid(rs.getInt("boardCategoryVO.cid"));
						boardCategoryVO.setCname(rs.getString("boardCategoryVO.cname"));						
						boardVO.setBoardCategoryVO(boardCategoryVO);						
						return boardVO;
					}
				}, 
				startRec,endRec);
		
		return list;
	}
	
	@Override
	public List<BoardVO> list(int startRec, int endRec, String searchType, String keyWord) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select t1.bnum,   ");
		sql.append("       t1.cid   as \"boardCategoryVO.cid\",   ");
		sql.append("       t1.cname as \"boardCategoryVO.cname\", ");		
		sql.append("       t1.btitle, ");
		sql.append("       t1.bcontent,  ");		
		sql.append("       t1.bid,    ");
		sql.append("       t1.bnickname,   ");
		sql.append("       t1.bcdate, ");
		sql.append("       t1.bhit,    ");
		sql.append("       t1.bgroup,  ");
		sql.append("       t1.bstep,   ");
		sql.append("       t1.bindent  ");		
		sql.append("from(   ");
		sql.append("    select row_number() over (order by t1.bgroup desc, t1.bstep asc) as num,  ");
		sql.append("           t1.bnum,    ");
		sql.append("           t2.cid,  ");
		sql.append("           t2.cname, ");
		sql.append("           t1.btitle,  ");
		sql.append("           t1.bcontent,  ");
		sql.append("           t1.bid,     ");
		sql.append("           t1.bnickname,    ");
		sql.append("           t1.bcdate,  ");
		sql.append("           t1.budate,  ");
		sql.append("           t1.bhit,    ");
		sql.append("           t1.bgroup,  ");
		sql.append("           t1.bstep,   ");
		sql.append("           t1.bindent  ");
		sql.append("      from board t1, board_category t2     ");
		sql.append("     where t1.bcategory = t2.cid  ");

		switch (searchType) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + keyWord + "%' ");
			sql.append("   or t1.bcontent like '%" + keyWord + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + keyWord + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + keyWord + "%' ");
			break;
		case "N": //별칭
			sql.append("and t1.bnickname  like '%" + keyWord + "%'" );
			break;
		case "I":  //아이디
			sql.append("and t1.bid  like '%" + keyWord + "%'" );
			break;
		case "A":  //전체			
			sql.append("and ( t1.btitle  like '%" + keyWord + "%' ");
			sql.append("   or t1.bcontent like '%" + keyWord + "%' ");
			sql.append("   or t1.bnickname like '%" + keyWord + "%' ");
			sql.append("   or t1.bid like '%" + keyWord + "%' )");
			break;

		default:
			break;
		}	
		
		sql.append(") t1  ");
		sql.append("where t1.num between ? and ?   ");
		
		 List<BoardVO> list = jt.query(
				sql.toString(), 
				new RowMapper<BoardVO>() {
					@Override
					public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						BoardVO boardVO = new BoardVO();
						
						boardVO.setBnum(rs.getLong("bnum"));
						boardVO.setBtitle(rs.getString("btitle"));
						boardVO.setBid(rs.getString("bid"));
						boardVO.setBnickname(rs.getString("bnickname"));
						boardVO.setBcdate(rs.getTimestamp("bcdate"));
						boardVO.setBhit(rs.getInt("bhit"));
						boardVO.setBgroup(rs.getInt("bgroup"));
						boardVO.setBstep(rs.getInt("bstep"));
						boardVO.setBindent(rs.getInt("bindent"));
						
						BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
						boardCategoryVO.setCid(rs.getInt("boardCategoryVO.cid"));
						boardCategoryVO.setCname(rs.getString("boardCategoryVO.cname"));						
						boardVO.setBoardCategoryVO(boardCategoryVO);						
						return boardVO;
					}
				}, 
				startRec,endRec);
		
		return list;
	}
	
//	@Override
//	public List<BoardVO> list() {
//		StringBuilder sql = new StringBuilder();
//
//		sql.append("select t1.bnum, ");
//		sql.append("       t2.cid   as \"boardCategoryVO.cid\", ");
//		sql.append("       t2.cname as \"boardCategoryVO.cname\", ");
//		sql.append("       t1.btitle, ");
//		sql.append("       t1.bid, ");
//		sql.append("       t1.bnickname, ");
//		sql.append("       t1.bcdate, ");
//		sql.append("       t1.budate, ");
//		sql.append("       t1.bhit, ");
//		sql.append("       t1.bgroup, ");
//		sql.append("       t1.bstep, ");
//		sql.append("       t1.bindent ");
//		sql.append("  from board t1, board_category t2 ");
//		sql.append(" where t1.bcategory = t2.cid ");		
//		sql.append(" order by t1.bgroup desc, t1.bstep asc ");		
//		
//		List<BoardVO> list = jt.query(sql.toString(), new RowMapper<BoardVO>() {
//
//			@Override
//			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//				BoardVO boardVO = new BoardVO();
//				BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
//				//boardVO에 저장
//				boardVO.setBnum(rs.getLong("bnum"));
//				boardVO.setBtitle(rs.getString("btitle"));
//				boardVO.setBid(rs.getString("bid"));
//				boardVO.setBnickname(rs.getString("bnickname"));
//				boardVO.setBcdate(rs.getTimestamp("bcdate"));
//				boardVO.setBudate(rs.getTimestamp("budate"));
//				boardVO.setBhit(rs.getInt("bhit"));
//				boardVO.setBgroup(rs.getInt("bgroup"));
//				boardVO.setBstep(rs.getInt("bstep"));
//				boardVO.setBindent(rs.getInt("bindent"));
//				//boardCategoryVO에 저장
//				boardCategoryVO.setCid(rs.getInt("boardCategoryVO.cid"));
//				boardCategoryVO.setCname(rs.getString("boardCategoryVO.cname"));
//				//boardVO에 boardCategory저장
//				boardVO.setBoardCategoryVO(boardCategoryVO);
//				
//				return boardVO;
//			}
//		});
//		
//		return list;		
//	}
	/**
	 * 게시글 조회(by게시글)
	 * @param bnum
	 * @return
	 */	
	@Override
	public BoardVO view(long bnum) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select t1.bnum, ");
		sql.append("       t2.cid   as \"boardCategoryVO.cid\", ");
		sql.append("       t2.cname as \"boardCategoryVO.cname\", ");
		sql.append("       t1.btitle, ");
		sql.append("       t1.bid, ");
		sql.append("       t1.bnickname, ");
		sql.append("       t1.bcdate, ");
		sql.append("       t1.budate, ");
		sql.append("       t1.bhit, ");
		sql.append("       t1.bcontent, ");
		sql.append("       t1.bgroup, ");
		sql.append("       t1.bstep, ");
		sql.append("       t1.bindent ");
		sql.append("  from board t1, board_category t2 ");
		sql.append(" where t1.bcategory = t2.cid ");
		sql.append("   and t1.bnum = ? ");
		
		BoardVO boardVO = jt.queryForObject(sql.toString(), new RowMapper<BoardVO>() {

			@Override
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardVO boardVO = new BoardVO();
				BoardCategoryVO boardCategoryVO = new BoardCategoryVO();
				//boardVO에 저장
				boardVO.setBnum(rs.getLong("bnum"));
				boardVO.setBtitle(rs.getString("btitle"));
				boardVO.setBid(rs.getString("bid"));
				boardVO.setBnickname(rs.getString("bnickname"));
				boardVO.setBcdate(rs.getTimestamp("bcdate"));
				boardVO.setBudate(rs.getTimestamp("budate"));
				boardVO.setBhit(rs.getInt("bhit"));
				boardVO.setBcontent(rs.getString("bcontent"));
				boardVO.setBgroup(rs.getInt("bgroup"));
				boardVO.setBstep(rs.getInt("bstep"));
				boardVO.setBindent(rs.getInt("bindent"));
				//boardCategoryVO에 저장
				boardCategoryVO.setCid(rs.getInt("boardCategoryVO.cid"));
				boardCategoryVO.setCname(rs.getString("boardCategoryVO.cname"));
				//boardVO에 boardCategory저장
				boardVO.setBoardCategoryVO(boardCategoryVO);
				
				return boardVO;
			}
		}, bnum);
		
		return boardVO;
	}
	/**
	 * 첨부파일 조회(by게시글)
	 * @param bnum
	 * @return
	 */	
	@Override
	public List<BoardUploadFileVO> getFiles(long bnum) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select fid,bnum, ");
		sql.append("       fname,fsize,ftype, ");
		sql.append("       cdate,udate ");
		sql.append("  from board_upload_file ");
		sql.append(" where bnum = ?  ");
		
		List<BoardUploadFileVO> list = 
				jt.query(sql.toString(), new BeanPropertyRowMapper<>(BoardUploadFileVO.class), bnum);
		
		return list;
	}
	/**
	 * 첨부파일 다운로드
	 * @param bnum
	 * @return
	 */	
	@Override
	public BoardUploadFileVO downloadFile(long fid) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select fid,bnum, ");
		sql.append("       fname,fsize,ftype,fdata, ");
		sql.append("       cdate,udate ");
		sql.append("  from board_upload_file ");
		sql.append(" where fid = ?  ");

		return jt.queryForObject(
							sql.toString(), 
							new BeanPropertyRowMapper<BoardUploadFileVO>(BoardUploadFileVO.class),
							fid);
	}
	/**
	 * 게시글 삭제 : 게시글과 첨부파일이 동시에 삭제 처리됨.
	 * @param bnum
	 */	
	@Override
	public void delete(long bnum) {
		
		String sql = "delete from board where bnum = ? ";
		
		jt.update(sql.toString(), bnum);
		
	}
	
	/**
	 * 게시글 수정
	 * @param board
	 * @return
	 */	
	@Override
	public int modify(BoardVO boardVO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update board  ");
		sql.append("   set bcategory =? , ");
		sql.append("       btitle = ?, ");
		sql.append("       bcontent = ?, ");
		sql.append("       budate = systimestamp ");
		sql.append(" where bnum = ?    ");		
		
		int result = jt.update(sql.toString(), 
				      boardVO.getBoardCategoryVO().getCid(),
				      boardVO.getBtitle(),
				      boardVO.getBcontent(),
				      boardVO.getBnum());	
		
		return result;
	}
	/**
	 * 첨부파일 개별 삭제
	 * @param fid
	 * @return
	 */	
	@Override
	public int deleteFile(long fid) {
		
		String sql = "delete from board_upload_file where fid = ? ";
	
		return jt.update(sql.toString(), fid);
	}
	/**
	 * 답글 작성
	 * @param boardVO
	 * @return
	 */
	@Override
	public long reply(BoardVO boardVO) {
		//1)이전 답글 step변경
		updateStep(boardVO.getBgroup(), boardVO.getBstep());
		
		//2)답글 저장
		//게시글 생성 번호 가져오기
		long bnum = getNextVal("board_bnum_seq");
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO board ( ");
		sql.append("    bnum, ");
		sql.append("    bcategory,  ");
		sql.append("    btitle, ");
		sql.append("    bid,  ");
		sql.append("    bnickname,  ");
		sql.append("    bcontent, ");
		sql.append("    bgroup, ");
		sql.append("    bstep,  ");
		sql.append("    bindent ");
		sql.append(") VALUES (  ");
		//sql.append("    board_bnum_seq.nextval, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?, ");
		sql.append("    ?,  ");
		sql.append("    ?,  ");
		sql.append("    ?, ");
		sql.append("    ?, ");		
		//sql.append("    board_bnum_seq.currval, ");
		sql.append("    ?,  ");
		sql.append("    ? ");
		sql.append(")  ");
		
		jt.update(
				sql.toString(), 
				bnum,
				boardVO.getBoardCategoryVO().getCid(),
				boardVO.getBtitle(),
				boardVO.getBid(),
				boardVO.getBnickname(),
				boardVO.getBcontent(),
				boardVO.getBgroup(),
				boardVO.getBstep() + 1,
				boardVO.getBindent() + 1);
		
		return bnum;		
	}
	private int updateStep(int bgroup, int bstep) {
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("update board ");
		sql.append("   set bstep = bstep + 1 ");
		sql.append(" where bgroup = ? ");
		sql.append("   and bstep > ? ");
		
		
		return jt.update(sql.toString(), bgroup, bstep);
	}	
	/**
	 * 조회수 증가
	 * @param bnum
	 * @return
	 */	
	@Override
	public int updateBhit(long bnum) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("update board ");
		sql.append("   set bhit = bhit + 1 ");
		sql.append(" where bnum = ? ");
		
		return jt.update(sql.toString(), bnum);
	}
	
	/**
	 * 게시판 전체 레코드 수
	 * @return
	 */	
	@Override
	public long totalRecordCount() {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as totalRecCnt ");
		sql.append("  from board t1, board_category t2 ");
		sql.append(" where t1.bcategory = t2.cid ");
		
		return jt.queryForObject(sql.toString(), Long.class);
	}
	
	/**
	 * 게시판 전체 레코드 수 (검색어 포함)
	 * @return
	 */		
	@Override
	public long totalRecordCount(String searchType, String keyWord) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as totalRecCnt ");
		sql.append("  from board t1, board_category t2 ");
		sql.append(" where t1.bcategory = t2.cid ");
			
		switch (searchType) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + keyWord + "%' ");
			sql.append("   or t1.bcontent like '%" + keyWord + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + keyWord + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + keyWord + "%' ");
			break;
		case "N": //별칭
			sql.append("and t1.bnickname  like '%" + keyWord + "%'" );
			break;
		case "I":  //아이디
			sql.append("and t1.bid  like '%" + keyWord + "%'" );
			break;
		case "A":  //전체			
			sql.append("and ( t1.btitle  like '%" + keyWord + "%' ");
			sql.append("   or t1.bcontent like '%" + keyWord + "%' ");
			sql.append("   or t1.bnickname like '%" + keyWord + "%' ");
			sql.append("   or t1.bid like '%" + keyWord + "%' )");
			break;

		default:
			break;
		}	
		return jt.queryForObject(sql.toString(), Long.class);
	}
}
















