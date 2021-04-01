package com.kh.portfolio.member.svc;

import com.kh.portfolio.member.vo.MemberVO;

public interface MemberSVC {
	//회원등록
	int joinMember(MemberVO memberVO);
	//회원 수정
	int modifyMember(MemberVO memberVO);	
	
	//회원 전체 조회
	
	//회원 개별조회
	MemberVO listOneMember(String member_id);
	
	//회원 탈퇴
	int outMember(String member_id, String currentpw);	
	
	//회원 존재
	boolean existMember(String member_id);
	
	//회원 인증
	boolean isMember(String member_id, String pw);
	
	//회원 비밀번호변경
	int	changePw(String member_id, String currentpw, String nextpw);	
	
	//회원 아이디 찾기 by(전화번호,생년월일)
	String findMemberId(String tel,String birth);	
	
	//비밀번호찾기
	String findPW(String member_id,String tel,String birth);		

	//프로파일 이미지 조회
	byte[] findProfileImg(String member_id) throws Exception;
}


