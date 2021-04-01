package com.kh.portfolio.board.vo;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RboardVO {
	private long rnum;//	RNUM	NUMBER(10,0)	No		1	댓글번호
	@NotBlank(message="최초원글정보가 누락되었습니다!")
	private String bnum;//	BNUM	NUMBER(10,0)	No		2	최초원글
	@NotBlank(message = "댓글작성자 정보가 누락되었습니다.")
	@Email(message = "작성자 아이디는 이메일 형식 이어야합니다! ex)aaa@bbb.com")	
	private String rid;//	RID	VARCHAR2(40 BYTE)	No		3	댓글작성자ID
	private String rnickname;		//	RNICKNAME	VARCHAR2(30 BYTE)	Yes		4	댓글작성자별칭
	@JsonFormat(pattern = "yyyy-MM-dd h:mm a",timezone = "Asia/Seoul")
	private Timestamp rcdate;//	RCDATE	TIMESTAMP(6)	No	SYSTIMESTAMP	5	작성일시
	@JsonFormat(pattern = "yyyy-MM-dd h:mm a",timezone = "Asia/Seoul")
	private Timestamp rudate;//	RUDATE	TIMESTAMP(6)	Yes	SYSTIMESTAMP	6	수정일시
	@NotBlank(message = "내용을 입력해주세요!")
	private String rcontent;//	RCONTENT	CLOB	No		7	댓글본문내용
	//선호도
	private int rgood;//	RGOOD	NUMBER(5,0)	Yes		8	선호
	private int rbad;//	RBAD	NUMBER(5,0)	Yes		9	비선호
	//댓글그룹
	private int rgroup;//	RGROUP	NUMBER(5,0)	Yes		10	댓글그룹
	private int rstep;//	RSTEP	NUMBER(5,0)	Yes		11	댓글 단계
	private int rindent;//	RINDENT	NUMBER(5,0)	Yes		12	댓글 들여쓰기
	//부모댓글 참조
	private long prnum;//	PRNUM	NUMBER(10,0)	Yes		13	부모댓글번호
	private String prid;//	PRID	VARCHAR2(40 BYTE)	Yes		14	부모댓글아이디
	private String prnickname;//	PRNICKNAME	VARCHAR2(30 BYTE)	Yes		15	부대댓글작성자(별칭)
	
	//프로파일 이미지
	private String ftype; //파일mime타입 image/*
	private byte[] pic;		//첨부이미지파일의 바이트 배열
}
