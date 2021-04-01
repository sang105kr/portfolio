package com.kh.portfolio.common.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.common.vo.CodeVO;

@Repository
public class CodeDAOImpl implements CodeDAO {

	private static final Logger logger 
		= LoggerFactory.getLogger(CodeDAOImpl.class);
	
	private JdbcTemplate jt;
	
	@Autowired
	public CodeDAOImpl(JdbcTemplate jt) {
		this.jt = jt;
	}
	
	//코드유형으로 코드목록 가져오기
	@Override
	public List<CodeVO> getCodesByCodetype(String codetype_id) {
		List<CodeVO> list = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select t2.code_id, t2.name");
		sql.append("  from codetype t1 , code t2");
		sql.append(" where t1.codetype_id = t2.codetype_id");
		sql.append("   and t1.codetype_id = ? ");
			
		list = jt.query(sql.toString(), 
										new BeanPropertyRowMapper<CodeVO>(CodeVO.class), 
										codetype_id);
		
		return list;
	}

}





