package com.kh.portfolio.member.svc;


import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.portfolio.member.dao.MemberDAO;
import com.kh.portfolio.member.vo.MemberVO;

@Service
@Transactional(readOnly = true)
public class MemberSVCImpl implements MemberSVC {

	private static final Logger logger
		= LoggerFactory.getLogger(MemberSVCImpl.class);
	
	private final MemberDAO memberDAO;
	
	@Autowired
	public MemberSVCImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	//회원등록
	@Transactional
	@Override
	public int joinMember(MemberVO memberVO) {
		logger.info("int joinMember(MemberVO memberVO) 호출됨!" + memberVO);
		int result = 0;
		result = memberDAO.joinMember(memberVO);
		return result;
	}

	//회원수정
	@Transactional
	@Override
	public int modifyMember(MemberVO memberVO) {
		int result = 0;
		//첨부 파일 존재시(회원프로파일사진) multipartfile에서 첨부파일관련 정보 추출
		if(memberVO.getFile().getSize() > 0) {

			try {
				//파일사이즈
				memberVO.setFsize(String.valueOf(memberVO.getFile().getSize()));
				//파일타입
				memberVO.setFtype(memberVO.getFile().getContentType());
				//파일이름
				memberVO.setFname(memberVO.getFile().getOriginalFilename());
				//첨부파일
				memberVO.setPic(memberVO.getFile().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		result = memberDAO.modifyMember(memberVO);
		return  result;
	}

	//회원 개별조회
	@Override
	public MemberVO listOneMember(String member_id) {
		MemberVO memberVO = memberDAO.listOneMember(member_id);
		
		//프로파일사진이 있으면 이미지파일 -> base64변환
		if(memberVO.getPic() != null) {
			memberVO.setPicBase64(conversionToBase64(memberVO.getPic()));
		}
		
		return memberVO;
	}

	//이미지 base64로 변환후 img태그에 적용하기위함
	//Base64의 핵심은 바이너리 데이터를 ASCII코드로 변경하는 인코딩방식
	private String conversionToBase64(byte[] pic) {
		byte[] encoded = Base64.encodeBase64(pic);
		return new String(encoded);
	}

	//회원 탈퇴
	@Override
	public int outMember(String member_id, String currentpw) {
		int result = 0;
		result = memberDAO.outMember(member_id, currentpw);
		return result;
	}

	//회원 존재
	@Override
	public boolean existMember(String member_id) {
		boolean result = false;
		result = memberDAO.existMember(member_id);
		return result;
	}

	//회원 인증
	@Override
	public boolean isMember(String member_id, String pw) {
		boolean result = false;
		result = memberDAO.isMember(member_id, pw);
		return result;
	}

	//회원 비밀번호 변경
	@Override
	public int changePw(String member_id, String currentpw, String nextpw) {
		int result = 0;
		result = memberDAO.changePw(member_id, currentpw, nextpw);
		return result;
	}

	//회원 아이디 찾기 by(전화번호,생년월일)
	@Override
	public String findMemberId(String tel, String birth) {

		return memberDAO.findMemberId(tel, birth);
	}

	@Override
	public String findPW(String member_id, String tel, String birth) {

		return memberDAO.findPW(member_id, tel, birth);
	}

	@Override
	public byte[] findProfileImg(String member_id) throws Exception {

		return memberDAO.findProfileImg(member_id);
	}


}
