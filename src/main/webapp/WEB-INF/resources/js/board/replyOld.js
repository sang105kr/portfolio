'use strict';
const $rcontent   = document.querySelector('.comment.write .rcontent');
const $cancelBtn  = document.querySelector('.comment.write .cancelBtn');
const $writeBtn   = document.querySelector('.comment.write .writeBtn');

const $comments   = document.querySelector('.wrapper-comments .comments');
const $hiddenItem = document.querySelectorAll('.hiddenItem .icon');
const buttonNameMap= new Map([
	['cancel',   'cancelFn'],  //취소
	['write',    'writeFn'],   //댓글등록
	['modify',   'modifyFn'],  //댓글수정
	['delete',   'deleteFn'],  //댓글삭제
	['rgood',    'rgoodFn'],   //선호
	['rbad',     'rbadFn'],    //비선호
	['rereply',  'rereplyFn']  //대댓글
]);

//댓글목록영역에 클릭 이벤트 감지
$comments.addEventListener('click',evt=>{
	let l_target = evt.target;

	 if (l_target.tagName === 'I') l_target = l_target.parentElement;
	 if (l_target.tagName !== 'BUTTON') return; 

	 const findedFn = buttonNameMap.get(l_target.dataset.buttonName);
	 if(!findedFn) {
		 console.log('찾고자 하는 함수 없음');  return;
	 }
	 //클릭 버튼 처리 함수호출
	 eval(`${findedFn}(evt)`);
});

//숨김아이콘 클릭시 수정, 삭제메뉴 제어
[...$hiddenItem].forEach(ele=>ele.addEventListener('click',evt=>{
	const $menus = [...document.querySelectorAll('.hiddenItem .menu')];
	$menus.filter(ele=>ele === evt.currentTarget.nextElementSibling)
				.forEach(ele=>ele.classList.toggle('hidden'));
	$menus.filter(ele=>ele !== evt.currentTarget.nextElementSibling)
				.filter(ele=>!ele.classList.contains('hidden'))
				.forEach(ele=>ele.classList.add('hidden'));
}));

$rcontent.addEventListener('focus',evt=>{
		evt.target.closest('.comment.write').querySelector('.btngrp').style.display='block';
});

$rcontent.addEventListener('keyup',evt=>{
		//입력된 글자가 있으면
		if(evt.target.textContent.trim().length > 0){
			$writeBtn.style.backgroundColor = '#00f';
		}else{
			$writeBtn.style.backgroundColor = '#eee';
		}
  });

$cancelBtn.addEventListener('click',evt=>{
	evt.target.closest('.btngrp').style.display = 'none';
	$rcontent.textContent = '';
});

//댓글 작성
$writeBtn.addEventListener('click', evt=>{
	if($rcontent.textContent.trim().length === 0) return;
	
	const url = replyInfo.url;  //요청URL
	const jsonObj = {						//요청data
		"bnum": replyInfo.bnum,
    "rid" : replyInfo.rid,
    "rnickname": replyInfo.rnickname,
    "rcontent": $rcontent.textContent
	}
	ajaxCall.post(url, jsonObj, handler);
	
	replyList();
});

//후처리메소드
function handler(jsonObj){
	console.log('errcode:'+jsonObj.rtcd);
	
	switch(jsonObj.rtcd){
		case '00': //성공
			console.log(jsonObj.rtmsg);
			break;
		case '01': //실패
			jsonObj.rtmsg.forEach(item=>{
				console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
			});
			break;
	}
}

//목록가져오기
function replyList(){
	console.log('replyList!');
}
//취소
function cancelFn(evt){
	console.log('cancelFn!');
	const $parent = evt.target.closest('.comments');
	const $child = evt.target.closest('.comment.rewrite');
	$parent.removeChild($child);
}
//등록
function writeFn(evt){
	console.log('writeFn!:'+evt.target);
	replyList();
}
//수정
function modifyFn(evt){
	console.log('modifyFn!');
}
//삭제
function deleteFn(evt){
	console.log('deleteFn!');
}
//선호
function rgoodFn(evt){
	console.log('rgoodFn!');
}
//비선호
function rbadFn(evt){
	console.log('rbadFn!');
}
//대댓글
function rereplyFn(evt){
	console.log('rereplyFn!');

	let rereplyHtml = '';
	rereplyHtml += `<div class="comment rewrite">`;
	rereplyHtml += `  <div class="profileImg">`;
	rereplyHtml += `    <img src="https://picsum.photos/50" alt="프로파일">`;
	rereplyHtml += `  </div>`;
	rereplyHtml += `  <div class="rcontent" contenteditable data-placeholder="댓글 추가..."></div>`;
	rereplyHtml += `  <div class="btngrp">`;
	rereplyHtml += `    <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
	rereplyHtml += `    <button type="button" class="writeBtn"  data-button-name="write">댓글</button>`;
	rereplyHtml += `  </div>`;
	rereplyHtml += `</div>`;

	const $comment = evt.target.closest('div[data-rnum]');
	const $commentRewrite = $comment.nextElementSibling;

	if( !$commentRewrite.classList.contains('rewrite')) {
		$comment.insertAdjacentHTML('afterend',rereplyHtml);
		
		const $rcontent = $comment.nextElementSibling.querySelector('.rcontent');
		const $btngrp = $comment.nextElementSibling.querySelector('.btngrp');

		$rcontent.addEventListener('focus',evt=>{
			$btngrp.style.display='block';
		});

		$rcontent.addEventListener('keyup',evt=>{
				//입력된 글자가 있으면
				if(evt.target.textContent.trim().length > 0){
					$btngrp.querySelector('.writeBtn').style.backgroundColor = '#00f';
				}else{
					$btngrp.querySelector('.writeBtn').style.backgroundColor = '#eee';
				}
			});

		// $btngrp.querySelector('.writeBtn').addEventListener('click',evt=>{
		// 	console.log($rcontent.textContent.trim().length);
		// 	if($rcontent.textContent.trim().length == 0) return false;
		// });
	}else if($commentRewrite.classList.contains('rewrite')) {
		$commentRewrite.click();
	}
}




