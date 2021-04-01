package com.kh.portfolio.member.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.common.vo.CodeVO;
import com.kh.portfolio.member.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	private static final Logger logger
		= LoggerFactory.getLogger(MemberDAOImpl.class);
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public MemberDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//회원등록
	@Override
	public int joinMember(MemberVO memberVO) {
		logger.info("int joinMember(MemberVO memberVO) 호출됨!" + memberVO );
		int result = 0;
		
		//sql문 생성
		StringBuilder sql = new StringBuilder();
		sql.append("insert into member (member_id,pw,tel,nickname,gender,region,birth)");
		sql.append("values (?,?,?,?,?,?,?)");
		
		//sql문 실행
		result = jdbcTemplate.update(sql.toString(),memberVO.getMember_id(),memberVO.getPw(),memberVO.getTel(),
				memberVO.getNickname(),memberVO.getGender(),memberVO.getRegion(),memberVO.getBirth());
		
		if(memberVO.getHobby() != null) {
			//취미등록
			insertHobby(memberVO);
		}
		
		return result;
	}

	private int[] insertHobby(MemberVO memberVO) {

		String sql = "insert into member_hobby values( ? , ? )";
		
		int[] result = jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1,memberVO.getHobby().get(i));
				ps.setString(2,memberVO.getMember_id());
			}
			
			@Override
			public int getBatchSize() {
				return memberVO.getHobby().size();
			}
		});
		
		return result;
	}

	// 회원 수정
	@Override
	public int modifyMember(MemberVO memberVO) {
		int result = 0;
		
		//프로파일 이미지가 없으면
		if(memberVO.getFile().getSize() == 0) {
			
			return modifyMemberNoFile(memberVO);			
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("update member ");
		sql.append("   set tel = ?, ");
		sql.append("       nickname = ?, ");
		sql.append("       gender = ?, ");
		sql.append("       region = ?, ");
		sql.append("       birth = ?, ");
		sql.append("       udate = systimestamp, ");
		sql.append("       pic = ?, ");
		sql.append("       fsize = ?, ");
		sql.append("       ftype = ?, ");
		sql.append("       fname = ? ");
		sql.append("where  member_id = ? ");
		sql.append("  and  pw = ? ");
		
		logger.info("DAO : int modifyMember(MemberVO memberVO) " + memberVO);
		
		result = jdbcTemplate.update(
									sql.toString(),
									memberVO.getTel(),
									memberVO.getNickname(),
									memberVO.getGender(),
									memberVO.getRegion(),
									memberVO.getBirth(),								
									memberVO.getPic(),
									memberVO.getFsize(),
									memberVO.getFtype(),
									memberVO.getFname(),
									memberVO.getMember_id(),
									memberVO.getPw());
		if(memberVO.getHobby() != null) {
			//취미삭제
			deleteHobby(memberVO.getMember_id());
			//취미등록
			insertHobby(memberVO);
		}		
		return result;
	}

	//회원수정 : 프로파일사진이 없는경우
	private int modifyMemberNoFile(MemberVO memberVO) {
		int result = 0;
			
		StringBuilder sql = new StringBuilder();
		sql.append("update member ");
		sql.append("   set tel = ?, ");
		sql.append("       nickname = ?, ");
		sql.append("       gender = ?, ");
		sql.append("       region = ?, ");
		sql.append("       birth = ?, ");
		sql.append("       udate = systimestamp ");
		sql.append("where  member_id = ? ");
		sql.append("  and  pw = ? ");
				
		result = jdbcTemplate.update(
									sql.toString(),
									memberVO.getTel(),
									memberVO.getNickname(),
									memberVO.getGender(),
									memberVO.getRegion(),
									memberVO.getBirth(),								
									memberVO.getMember_id(),
									memberVO.getPw());
		if(memberVO.getHobby() != null) {
			//취미삭제
			deleteHobby(memberVO.getMember_id());
			//취미등록
			insertHobby(memberVO);
		}		
		return result;
	}

	private void deleteHobby(String member_id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete from member_hobby ");
		sql.append(" where member_id = ? ");
		
		jdbcTemplate.update(sql.toString(), member_id);
		
	}

	//회원 개별조회
	@Override
	public MemberVO listOneMember(String member_id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from member ");
		sql.append("where member_id = ?");
		
		MemberVO memberVO =	jdbcTemplate.queryForObject(
							sql.toString(), 
							new BeanPropertyRowMapper<MemberVO>(MemberVO.class), 
							member_id);	
		
		//취미가져오기
		List<String> hobby = listHobby(memberVO.getMember_id());
		if(hobby.size() > 0) {
			memberVO.setHobby(hobby);
		}
		logger.info("============"+memberVO.toString());
		return memberVO;
	}

	//취미가져오기
	private List<String> listHobby(String member_id) {

		StringBuilder sql = new StringBuilder();
		sql.append("select t2.code_id  ");
		sql.append("  from member t1, member_hobby t2 ");
		sql.append(" where t1.member_id = t2.member_id ");
		sql.append("   and t1.member_id = ? ");
		
		return	jdbcTemplate.queryForList(
							sql.toString(), String.class, member_id);
	}

	//회원 삭제
	@Override
	public int outMember(String member_id, String currentpw) {
		int result = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete from member ");
		sql.append(" where member_id = ? ");
		sql.append("   and pw = ? ");		
		
		result = jdbcTemplate.update(sql.toString(), member_id, currentpw);	
		return result;
	}

	//회원 존재
	@Override
	public boolean existMember(String member_id) {
		boolean result = false;
		String sql ="select count(*) cnt from member where member_id = ? ";
		Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class ,member_id);
		result = (cnt == 1) ? true : false;		
		return result;
	}

	//회원 인증
	@Override
	public boolean isMember(String member_id, String pw) {
		boolean result = false;
		String sql ="select count(*) cnt from member where member_id = ? and pw = ?";
		Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class ,member_id,pw);
		result = (cnt == 1) ? true : false;		
		return result;
	}

	//회원비밀번호 변경
	@Override
	public int changePw(String member_id, String currentpw, String nextpw) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("update member ");
		sql.append("   set pw = ? ");
		sql.append(" where member_id = ? ");
		sql.append("   and pw = ? ");		
		
		int result = jdbcTemplate.update(sql.toString(), nextpw,member_id,currentpw);
		
		return result;
	}

	//회원 아이디 찾기 by(전화번호,생년월일)
	@Override
	public String findMemberId(String tel, String birth) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select member_id from member ");
		sql.append(" where tel = ? ");
		sql.append("   and birth = ? ");
		
		return jdbcTemplate.queryForObject(sql.toString(),String.class,tel,birth);
	}

	@Override
	public String findPW(String member_id, String tel, String birth) {
		StringBuilder sql = new StringBuilder();		
		
		sql.append("select pw ");
		sql.append("  from member ");
		sql.append(" where member_id = ? ");
		sql.append("   and tel = ? ");
		sql.append("   and birth = ? ");
		
		return jdbcTemplate.queryForObject(sql.toString(),String.class,member_id,tel,birth);
	}

	@Override
	public byte[] findProfileImg(String member_id) throws Exception {
		String sql = "select pic from member where member_id = ? ";
		
		byte[] bytes = jdbcTemplate.queryForObject(sql, 
				new RowMapper<byte[]>() {
					@Override
					public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
						byte[] bytes = rs.getBytes(1);	
						return bytes;
					}
				},
				member_id);
		return bytes;
	}
}


















