package com.kh.portfolio.board.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BoardVO {
	
	private long bnum;		//	BNUM	NUMBER(10,0)	No		1	게시글번호

	@Valid
	private BoardCategoryVO boardCategoryVO;//	BCATEGORY	NUMBER(10,0)	Yes		2	분류카테고리
	
	//@NotNull : null(X)
	//@NotEmpty: null(X) ,""(X) ,대상타입:문자열,Collection,Map,Array의 요소가 반드이 있어야함. 
	//@NotBlank: null(X) ,""(X)," "(X)
	@NotBlank(message = "제목은 4~50자 까지 입력가능합니다!" )
	@Size(min = 4,max = 50, message = "제목은 4~50자 까지 입력가능합니다!" )
	private	String btitle;//	BTITLE	VARCHAR2(150 BYTE)	Yes		3	제목
	
	@NotBlank(message = "이메일 형식 이어야합니다! ex)aaa@bbb.com")
	@Email(message = "이메일 형식 이어야합니다! ex)aaa@bbb.com")
	private String bid;		//	BID	VARCHAR2(40 BYTE)	Yes		4	작성자ID
	private String bnickname;//	BNICKNAME	VARCHAR2(30 BYTE)	Yes		5	별칭

	private Timestamp bcdate;//	BCDATE	TIMESTAMP(6)	Yes	systimestamp	6	작성일
	private Timestamp budate;//	BUDATE	TIMESTAMP(6)	Yes	systimestamp	7	수정일
	private int bhit;				 //	BHIT	NUMBER(5,0)	Yes	0	8	조회수
	
	@NotBlank(message = "내용을 입력해주세요!")
	private String bcontent; //	BCONTENT	CLOB	Yes		9	본문내용
	private int bgroup;			 //	BGROUP	NUMBER(5,0)	Yes		10	답글그룹
	private int bstep;			 //	BSTEP	NUMBER(5,0)	Yes		11	답변글의 단계
	private int bindent;		 //	BINDENT	NUMBER(5,0)	Yes		12	답변글의 들여쓰기
	
	//첨부파일
	private List<MultipartFile> files;
	
}
