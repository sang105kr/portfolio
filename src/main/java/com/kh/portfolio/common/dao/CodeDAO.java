package com.kh.portfolio.common.dao;

import java.util.List;

import com.kh.portfolio.common.vo.CodeVO;

public interface CodeDAO {

	//코드유형으로 코드목록 가져오기
	List<CodeVO> getCodesByCodetype(String codetype_id);
}
