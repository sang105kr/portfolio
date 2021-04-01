package com.kh.portfolio.common.svc;

import java.util.List;

import com.kh.portfolio.common.vo.CodeVO;

public interface CodeSVC {
	//코드유형으로 코드목록 가져오기
	List<CodeVO> getCodesByCodetype(String codetype_id);
}
