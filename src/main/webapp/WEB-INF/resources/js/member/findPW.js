'use strict';
import * as commonLib from '/portfolio/js/common/common.mjs';
const $findPwBtn = document.getElementById('findPwBtn');

$findPwBtn.addEventListener("click",commonLib.debounce(findPw,500));

const $findedPw =document.getElementById('findedPw');
const url = "/portfolio/member/pwmail";
const $member_id = document.getElementById('member_id');
const $tel = document.getElementById('tel');
const $birth = document.getElementById('birth');
	
function findPw(evt){
		//유효성체크
	if(validChk()){
		//ajax 호출
		//const birth = $birth.value.replaceAll('-','');
		const jsonObj = {
			member_id : $member_id.value,
			tel : $tel.value,
			birth : $birth.value
		}
		console.log(jsonObj);
		
		$findPwBtn.lastChild.textContent = '조회중....';
		$findPwBtn.disabled = true; //비활성화
		$findPwBtn.querySelector('span').classList.remove('visually-hidden');
				
		ajaxCall.post(url,jsonObj,handler);
	}
}

function handler(jsonObj){
	setTimeout(()=>{
		if(jsonObj.rtcd === '00'){
			$findedPw.textContent = jsonObj.result;
		}else{
			$findedPw.textContent = jsonObj.errmsg;
		}
		
		$findPwBtn.lastChild.textContent = '비밀번호찾기';
		$findPwBtn.disabled = false;  //버튼활성화
		$findPwBtn.querySelector('span').classList.add('visually-hidden');
	},1000);
}

function validChk(){
	let result = true;

	if($member_id.value.trim().length == 0 ){
		$findedPw.textContent = '아이디를 입력하세요!';
		$member_id.focus();
		$member_id.select();
		return false;
	}
	if($tel.value.trim().length == 0 ){
		$findedId.textContent = '전화번호를 입력하세요!';
		$tel.focus();
		$tel.select();
		return false;
	}
	if($birth.value.trim().length == 0 ){
		$findedId.textContent = '생년월일을 입력하세요!';
		$birth.focus();
		$birth.select();
		return false;
	}
	return result;
}



