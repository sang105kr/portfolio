package com.kh.portfolio.board.vo;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VoteVO {
	@NotBlank(message = "댓글 정보가 누락되었습니다!")
	private String rnum;//	RNUM	NUMBER(10,0)	No		1	댓글번호
	
	@NotBlank(message = "게시원글 정보가 누락되었습니다!")
	private String bnum;//	BNUM	NUMBER(10,0)	No		2	최초원글
	
	@NotBlank(message = "투표자 정보가 누락되었습니다.")
	@Email(message = "투표자 아이디는 이메일 형식 이어야합니다! ex)aaa@bbb.com")		
	private String rid;//	RID	VARCHAR2(40 BYTE)	No		3	댓글아이디
	
	@NotNull(message = "선호도 정보가 누락되었습니다!")
	private Vote vote;//	VOTE	VARCHAR2(10 BYTE)	No		4	GOOD:호감'BAD':비호감
	
	private Timestamp rcdate;//	CDATE	DATE	No	SYSTIMESTAMP	5	투표일시
	private Timestamp rudate;//	UDATE	DATE	Yes	SYSTIMESTAMP	6	투표일시

}
