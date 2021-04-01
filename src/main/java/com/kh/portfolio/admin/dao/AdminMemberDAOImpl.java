package com.kh.portfolio.admin.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.member.vo.MemberVO;

@Repository
public class AdminMemberDAOImpl implements AdminMemberDAO {

	private static final Logger logger 
		= LoggerFactory.getLogger(AdminMemberDAOImpl.class);

	@Autowired
	JdbcTemplate jt;
	
	//회원목록
	@Override
	public List<MemberVO> memberList() {

		String sql = "select * from member";
		
		List<MemberVO> list = jt.query(sql, new BeanPropertyRowMapper(MemberVO.class));
		
		return list;
	}

}





