package com.kh.portfolio.admin.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.portfolio.admin.dao.AdminMemberDAO;
import com.kh.portfolio.member.vo.MemberVO;

@Service
public class AdminMemberSVCImpl implements AdminMemberSVC{
	
	private static final Logger logger 
		= LoggerFactory.getLogger(AdminMemberSVCImpl.class);
	
	@Autowired
	AdminMemberDAO memberDAO;
	
	//회원목록
	@Override
	public List<MemberVO> memberList() {
		
		List<MemberVO> list = memberDAO.memberList();
		
		return list;
	}
}







