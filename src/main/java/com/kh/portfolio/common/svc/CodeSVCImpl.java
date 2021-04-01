package com.kh.portfolio.common.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.portfolio.common.dao.CodeDAO;
import com.kh.portfolio.common.vo.CodeVO;

@Service
public class CodeSVCImpl implements CodeSVC{

	private static final Logger logger 
	 = LoggerFactory.getLogger(CodeSVCImpl.class);
	
	private CodeDAO codeDAO;
	
	@Autowired
	public CodeSVCImpl(CodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}
	
	//코드유형으로 코드목록 가져오기
	@Override
	public List<CodeVO> getCodesByCodetype(String codetype_id) {
		List<CodeVO> list = null;
		list = codeDAO.getCodesByCodetype(codetype_id);
		return list;
	}

}
