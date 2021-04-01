'use strict';
const $findIdBtn = document.getElementById('findIdBtn');

$findIdBtn.addEventListener("click",findId);

const $findedId =document.getElementById('findedId');
const url = "/portfolio/member/member_id";
const $tel = document.getElementById('tel');
const $birth = document.getElementById('birth');
	
function findId(evt){
	
	const birth = $birth.value.replaceAll('-','');
	const requestUrl = `${url}?tel=${$tel.value}&birth=${birth}`;
	console.log(requestUrl);
	//유효성체크
	if(validChk()){
		//ajax 호출
		ajaxCall.get(requestUrl,handler);
	}
}

function handler(jsonObj){
	if(jsonObj.rtcd === '00'){
		$findedId .textContent = jsonObj.result;
	}else{
		$findedId .textContent = jsonObj.errmsg;
	}
}

function validChk(){
	let result = true;

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



