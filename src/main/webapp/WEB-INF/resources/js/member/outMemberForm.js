'use strict';
const $confirmBtn = document.getElementById('confirmBtn');
const $cancelBtn = document.getElementById('cancelBtn');		
const $currentpw =document.getElementById('currentpw');

const confirmBtnHandler = evt => {
	evt.preventDefault();
	console.log('탈퇴버튼클릭');
	//1) 모든필드에 값을 채웠는지 여부	
	if($currentpw.value.trim().length == 0) {
		$currentpw.placeholder='비밀번호를 입력하세요!';
		$currentpw.classList.add('errmsg');
		$currentpw.focus();
		return;
	}
	//2) 탈퇴 확인
	if(!confirm("정말로 탈퇴하겠습니까?")) return;
	
	//3) form전송
	document.getElementById('outMemberForm').submit();
};
const cancelBtnHandler = evt => {
	evt.preventDefault();
	console.log('취소버튼클릭');
	//모든 필드 clear
	document.getElementById('outMemberForm').reset();
	//현재비밀번호로 focus이동
	document.getElementById('currentpw').focus();
};
$confirmBtn.addEventListener("click",confirmBtnHandler);
$cancelBtn.addEventListener("click",cancelBtnHandler);










