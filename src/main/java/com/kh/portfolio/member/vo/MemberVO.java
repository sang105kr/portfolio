package com.kh.portfolio.member.vo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberVO {
	
	//@NotNull : null(x), ""(O) 
	//@NotEmpty: null(x), ""(x), " "(O)
	//@NotBlank: null(x), ""(x), " "(X)
	
	@NotBlank(message = "필수 항목입니다!")
	@Email(message = "이메일 형식에 맞지 않습니다 ex)aaa@bbb.com")
	//@Size(min=4,max=10,message = "아이디는 4~10자리로 입력바랍니다. ex)aaa@bbb.com")	
	private String member_id;				//	ID	VARCHAR2(40 BYTE)	No		1	ex)admin@google.com
	
	@NotBlank(message = "필수 항목입니다!")
	@Size(min=4,max=10,message = "비밀번호는 4~10자리로 입력바랍니다.")
	private String pw;				//	PW	VARCHAR2(10 BYTE)	Yes		2	8~10자리,특수문자포함
	
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 맞지 않습니다. ex)010-1234-5678")
	private String tel;				//	TEL	VARCHAR2(13 BYTE)	Yes		3	'-'포함
	
	private String nickname;	//	NICKNAME	VARCHAR2(30 BYTE)	Yes		4	
	private String gender;		//	GENDER	CHAR(3 BYTE)	Yes		5	성별('남','여')
	private String region;		//	REGION	VARCHAR2(30 BYTE)	Yes		6	지역
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate	birth;			//	BIRTH	DATE	Yes		7	생년월일('YYYYMMDD')
	private Timestamp cdate;	//	CDATE	TIMESTAMP(6)	Yes	"SYSTIMESTAMP	"	8	생성일시
	private Timestamp udate;	//	UDATE	TIMESTAMP(6)	Yes		9	변경일시
	private List<String> hobby;
	
	//회원 이미지 첨부용
	private MultipartFile file;   //view에서 첨부파일을 바인딩하기위해 필요
	private String fsize;
	private String ftype;
	private String fname;
	private byte[] pic;
	
	//view에 display용도
	private String picBase64;
	
//	PIC	BLOB	Yes		10	회원사진(GIF,JPEG,PNG)
//	FSIZE	VARCHAR2(45 BYTE)	Yes		11	파일크기
//	FTYPE	VARCHAR2(50 BYTE)	Yes		12	파일타입

}
