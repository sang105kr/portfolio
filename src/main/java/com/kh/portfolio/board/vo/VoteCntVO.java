package com.kh.portfolio.board.vo;

import lombok.Data;

@Data
public class VoteCntVO {
	private long rnum;//	RNUM	NUMBER(10,0)	No		1	댓글번호
	//선호도
	private int rgood;//	RGOOD	NUMBER(5,0)	Yes		8	선호
	private int rbad;//	RBAD	NUMBER(5,0)	Yes		9	비선호
}
