'use strict'

joinBtn.addEventListener('click',joinHandler);

function joinHandler(evt){
	evt.preventDefault(); // submit 기본 이벤트 취소
	console.log('회원가입클릭됨')
	
	//유효성 체크
	if(!validationChk(evt)) return;
	
	//유효성 통과시
	joinForm.submit();
}

function validationChk(){
	//아이디 체크
	if(!isEmail(member_id.value)) {
		//오류메세지
		errmsg_id.textContent = '아이디를 잘못입력하였습니다.';
		//포커스이동
		member_id.focus(); member_id.select();
		return false;
	}else{
		errmsg_member_id.textContent = '';
	}
	//비밀번호 유효성 체크
	if(!isPasswd(pw.value)){
		errmsg_pw.textContent = '4~10자리 영문자 숫자만 가능합니다.';
		pw.focus(); pw.select();
		return false;
	}else{
		errmsg_pw.textContent = '';
	}
	//비밀번호 , 비밀번호 확인 일치여부 체크
	if(pw.value !== pwchk.value){
		errmsg_pwchk.textContent = '비밀번호가 일치하지 않습니다.';
		pwchk.focus(); pwchk.select();		
		return false;
	}else {
		errmsg_pwchk.textContent = '';
	}
	//전화번호 체크
	if(!isTel(tel.value)){
		errmsg_tel.textContent = '전화번호 형식에 맞지 않습니다. ex)010-123-456';
		tel.focus(); tel.select();
		return false;
	}else {
		errmsg_tel.textContent = '';
	}
	//별칭 체크
	if(!isNickname(nickname.value)){
		errmsg_nickname.textContent = '4~10자리 문자만 가능합니다.';
		nickname.focus(); nickname.select();
		return false;
	}else {
		errmsg_nickname.textContent = '';
	}	
	//지역 체크
	if(region.selectedIndex == 0){
		errmsg_region.textContent = '지역을 선택하세요!';
		region.focus();
		region.style.outline = '1px solid red';
		return false;
	}else{
		errmsg_region.textContent = '';
	}
	//성별 체크
	const $gender = document.querySelectorAll('input[name=gender]');
	let status = false;
	for(const ele of $gender){
		if(ele.checked){
			status = true;
			break;
		}
	}
	if(!status){
		errmsg_gender.textContent = '성별을 선택해주세요!';
		return false;
	}else{
		errmsg_gender.textContent = '';
	}
	//생년월일 체크
	if(birth.value === ''){
		errmsg_birth.textContent = '생년월일을 입력해주세요!';
		birth.focus();
		return false;
	}else{
		errmsg_birth.textContent = '';
	}
	
	return true;
}


//별칭 유효성체크
//한글 알파벳 대소문자 또는 숫자로 시작하고 끝나며 4 ~10자리인지 검사한다.
function isNickname(nickname){
  const nicknamePattern = /^[가-힣ㄱ-ㅎㅏ-ㅣA-Za-z0-9]{4,10}$/;
  return nicknamePattern.test(nickname); 
}

//이메일 체크
function isEmail(email){
  const emailPattern = /^[\w]([-_\.]?[\w])*@[\w]([-_\.]?[\w])*\.[a-zA-Z]{2,3}$/;
  return emailPattern.test(email);
}

//전화번호체크
function isTel(tel){
  const telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
  return telPattern.test(tel); 
}

//숫자인지 체크
function isNum(num){
  const numPattern = /^[\d]*$/;
  return numPattern.test(num);
}

//4~10자리수의 숫자인지 체크
function isNum2(num){
  const numPattern = /^[\d]{4,10}$/;  ///^[0-9]{4,10}$/
  return numPattern.test(num);
}

//비밀번호 체크
//대소문자 또는 숫자로 시작하고 끝나며 4 ~10자리인지 검사한다.
function isPasswd(passwd){
  const passwdPattern = /^[\w]{4,10}$/; // /^[A-Za-z0-9]{4,10}$/
  return passwdPattern.test(passwd); 
}
