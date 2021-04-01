'use strict';
import {ajaxCall, makeEle, debounce} from '../common/common.mjs';

let g_reqPage = 1; //요청페이지
const loginChk = (replyInfo.rid === '') ? false : true;	
const $comments   = document.querySelector('.wrapper-comments .comments');
const $paging 		= document.querySelector(".paging");
const buttonNameMap= new Map([
	['rereply',  'rereplyFn'], //대댓글
	['modify',   'modifyFn'],  //댓글수정
	['delete',   'deleteFn'],  //댓글삭제
	['rgood',    'rgoodFn'],   //선호
	['rbad',     'rbadFn'],    //비선호
	['cancel',   'cancelFn'],  //취소
	['charge',   'chargeFn']   //신고
]);

//댓글목록영역에 클릭 이벤트 감지
$comments.addEventListener('click',evt=>{
	let l_target = evt.target;

	  if (l_target.tagName === 'I') l_target = l_target.parentElement;
	  if (l_target.tagName !== 'BUTTON') return; 

		if(!loginChk){
			window.location.href = '/portfolio/loginForm';
			return;
		}
	  const findedFn = buttonNameMap.get(l_target.dataset.buttonName);
	  if(!findedFn) {
		  console.log('찾고자 하는 함수 없음');  return;
	  }
	  //클릭 버튼 처리 함수호출
	  eval(`${findedFn}(evt)`);	 
});

//페이징 이벤트:페이지 번호 클릭시 이벤트 처리
$paging.addEventListener("click",(e)=>{
  e.preventDefault();
  e.stopImmediatePropagation();

    let l_reqPage = e.target.closest('li')
                            .querySelector('a')
                            .getAttribute('href');	
	  g_reqPage = l_reqPage													
    replyList(g_reqPage);

});	

//댓글작성
addWriteHTML();
//댓글목록
replyList(g_reqPage);

//댓글작성
function addWriteHTML(){
	let $innerHTML = '';
  $innerHTML += `<div class="profileImg">`;
  $innerHTML += `	<img src="/portfolio/member/findProfileImg/${replyInfo.rid}/" alt="프로파일">`;
  $innerHTML += `</div>`;
  $innerHTML += `<div class="rcontent write" contenteditable data-placeholder="댓글 추가..."></div>`;
  $innerHTML += `<div class="btngrp write">`;
  $innerHTML += `  <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
  $innerHTML += `  <button type="button" class="writeBtn"  data-button-name="write">댓글</button>`;
  $innerHTML += `</div>`;

	const $commentWrite = document.querySelector('.wrapper-comments .comment.write');
	$commentWrite.innerHTML = $innerHTML;
	
	const $rcontent   = $commentWrite.querySelector('.comment.write .rcontent');
	const $cancelBtn  = $commentWrite.querySelector('.comment.write .cancelBtn');
	const $writeBtn   = $commentWrite.querySelector('.comment.write .writeBtn');	
	const $profileImg = $commentWrite.querySelector('.profileImg img');

	//이미지 부재시 디폴트이미지
	$profileImg.addEventListener('error',evt=>{
		evt.target.src = '/portfolio/img/board/profile.png';
	});
	
	//댓글내용 입력시 버튼 활성화
	$rcontent.addEventListener('focus',evt=>{
			evt.target.closest('.comment.write').querySelector('.btngrp').style.display='block';
	});
	//입력된 댓글내용이 있으면 댓글버튼 색변경
	$rcontent.addEventListener('keyup',evt=>{
			//입력된 글자가 있으면
			if(evt.target.textContent.trim().length > 0){
				$writeBtn.style.backgroundColor = '#00f';
			}else{
				$writeBtn.style.backgroundColor = '#eee';
			}
	  });
	//댓글입력 취소시 입력란 초기화
	$cancelBtn.addEventListener('click',evt=>{
		evt.target.closest('.btngrp').style.display = 'none';
		$rcontent.textContent = '';
	});
	
	//댓글등록
	$writeBtn.addEventListener('click', evt=>{
	
		if($rcontent.textContent.trim().length === 0) return;
	
		const url = replyInfo.url;  //요청URL
		const jsonObj = {						//요청data
			"bnum": replyInfo.bnum,
	    "rid" : replyInfo.rid,
	    "rnickname": replyInfo.rnickname,
	    "rcontent": $rcontent.textContent
		}
		ajaxCall.post(url, jsonObj, jsonObj=>{			
			switch(jsonObj.rtcd){
				case '00': //성공
					$cancelBtn.click();
					replyList(g_reqPage);
						
					break;
				case '01': //실패
					jsonObj.rtmsg.forEach(item=>{
						console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
					});
					break;
			}
		});		
	});	

}


//요청페이지 목록가져오기
function replyList(reqPage =  1){
	//console.log('replyList!');
	//목록가져오기 로직 추가  <===============================
	const url = `${replyInfo.url}/${replyInfo.bnum}/${reqPage}`;  //요청URL
	ajaxCall.get(url, jsonObj=>{
	
			switch(jsonObj.rtcd){
				case '00': //성공
					const list = jsonObj.rtmsg.list;
					const pc 	 = jsonObj.rtmsg.pc;
					//댓글목록
					listHandler(list);
					//페이징
					addPaging(pc);	
					break;
				case '01': //실패
					jsonObj.rtmsg.forEach(item=>{
						console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
					});
					break;
			}	
	});
}

//대댓글 등록
function rereplyFn(evt){
	console.log('rereplyFn!');

	let $innerHtml = '';
	$innerHtml += `<div class="comment rewrite">`;
  $innerHtml += `	 <div class="profileImg">`;
  $innerHtml += `		<img src="/portfolio/member/findProfileImg/${replyInfo.rid}/" alt="프로파일">`;
  $innerHtml += `	 </div>`;
	$innerHtml += `  <div class="rcontent" contenteditable data-placeholder="댓글 추가..."></div>`;
	$innerHtml += `  <div class="btngrp">`;
	$innerHtml += `    <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
	$innerHtml += `    <button type="button" class="writeBtn"  data-button-name="write">댓글</button>`;
	$innerHtml += `  </div>`;
	$innerHtml += `</div>`;
	
	const $comment = evt.target.closest('div[data-rnum]');
	const $commentRewrite = $comment.nextElementSibling;
		
	if( !$commentRewrite || !$commentRewrite.classList.contains('rewrite')) {
		$comment.insertAdjacentHTML('afterend',$innerHtml);		
		const $rcontent = $comment.nextElementSibling.querySelector('.rcontent');
		const $btngrp = $comment.nextElementSibling.querySelector('.btngrp');
		const $profileImg = $comment.querySelector('.profileImg img');
			
		//이미지 부재시 디폴트이미지
		$profileImg.addEventListener('error',evt=>{
			evt.target.src = '/portfolio/img/board/profile.png';
		});
			
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
			
			// 취소 버튼 클릭처리
			const $cancelBtn = $btngrp.querySelector('.cancelBtn');
			$cancelBtn.addEventListener('click',evt=>{
				evt.stopPropagation();
		
				const $parent = evt.target.closest('.comments');
				const $child = evt.target.closest('.comment.rewrite');
				$parent.removeChild($child);
			});

			//대댓댓글 버튼 클릭처리
			const $writeBtn = $btngrp.querySelector('.writeBtn');
			$writeBtn.addEventListener('click',evt=>{
				evt.stopPropagation();
				
				//대댓글 작성로직 추가<=============================== 
				if($rcontent.textContent.trim().length === 0) return;
		
				const url = `${replyInfo.url}/reply`;  //요청URL
				const prnum = evt.target.closest('.comment.rewrite')
												 .previousElementSibling.dataset.rnum;
				const jsonObj = {						//요청data
					"bnum": replyInfo.bnum,
			    "rid" : replyInfo.rid,
			    "rnickname": replyInfo.rnickname,
			    "rcontent": $rcontent.textContent,
					"prnum" : prnum
				}
				
				ajaxCall.post(url, jsonObj, jsonObj=>{			
					switch(jsonObj.rtcd){
						case '00': //성공			
							replyList(g_reqPage);
							break;
						case '01': //실패
							jsonObj.rtmsg.forEach(item=>{
								console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
							});
							break;
					}
				});
		});
					
	}else if($commentRewrite.classList.contains('rewrite')) {
		$commentRewrite.click();
	}
}

//수정
function modifyFn(evt){
	console.log('modifyFn!');

	let $innerHtml = '';
	$innerHtml += `<div class="content-right">`;
	$innerHtml += `	<div class="rcontent modify" contenteditable></div>`;
	$innerHtml += `	<div class="btngrp modify">`;
	$innerHtml += `		<button type="button" class="cancelBtn" data-button-name="cancel">취소</button>`;
	$innerHtml += `		<button type="button" class="modifyBtn"  data-button-name="modify">수정</button>`;
	$innerHtml += `	</div>`;
	$innerHtml += `</div>`;

	const $parent = evt.target.closest('div[data-rnum] .content')
	const $child = $parent.querySelector('.content-right');

	//현재 댓글내용을 숨긴다
	$child.classList.add('hidden');
	const $rcontentRead = $child.querySelector('.rcontent.read');

	//수정 모드로 변경
	$child.insertAdjacentHTML('afterEnd',$innerHtml);	
	const $contnetRight = $parent.querySelector('.content-right:not(.hidden)');
	const $rcontentModify = $contnetRight.querySelector('.rcontent.modify');
	$rcontentModify.focus();
	$rcontentModify.innerHTML = $rcontentRead.innerHTML;

	$rcontentModify.addEventListener('keyup',evt=>{
		//입력된 글자가 있으면
		if(evt.target.textContent.trim().length > 0){
			$modifyBtn.style.backgroundColor = '#00f';
		}else{
			$modifyBtn.style.backgroundColor = '#eee';
		}
  });

	// 취소,수정 버튼 클릭처리
	const $cancelBtn = $contnetRight.querySelector('.cancelBtn');
	const $modifyBtn = $contnetRight.querySelector('.modifyBtn');
	$cancelBtn.addEventListener('click',evt=>{
		evt.stopPropagation();
		$child.classList.remove('hidden');
		$parent.removeChild($contnetRight);
	});
	$modifyBtn.addEventListener('click',evt=>{
		evt.stopPropagation();
		//수정로직 추가  <===============================	
		const $comment = evt.target.closest('div[data-rnum]');
		const rnum = $comment.dataset.rnum;
		const $rcontent = $comment.querySelector('.rcontent.modify');
		const url = replyInfo.url;  //요청URL
		const jsonObj = {
			"rnum" : rnum,
			"rid" : replyInfo.rid,
			"rcontent" : $rcontent.textContent
		}
		ajaxCall.patch(url, jsonObj, jsonObj=>{
			
			switch(jsonObj.rtcd){
				case '00': //성공
					console.log('댓글수정');		
					replyList(g_reqPage);				
					break;
				case '01': //실패
					jsonObj.rtmsg.forEach(item=>{
						console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
					});
					break;
			}					
		});
	});
}

//삭제
function deleteFn(evt){
	console.log('deleteFn!');
	//document.body.style.overflow='hidden';
	$myModal.classList.remove('hidden');
	window.myCurrEvent = ()=>{
		//삭제로직 추가  <===============================
		const $comment = evt.target.closest('div[data-rnum]');
		const $rnum = $comment.dataset.rnum;
		const url = `${replyInfo.url}/${$rnum}`;  //요청URL
		ajaxCall.del(url, jsonObj=>{
			switch(jsonObj.rtcd){
				case '00': //성공
					evt.target.closest('.comments').removeChild($comment);
					delete window.myCurrEvent;			
					//replyList(g_reqPage);		
					break;
				case '01': //실패
					jsonObj.rtmsg.forEach(item=>{
						console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
					});
					break;
			}					
		});
	}
};
//선호
function rgoodFn(evt){
	console.log('rgoodFn!');
	
	//선호도 추가  <===============================	
	const $comment = evt.target.closest('div[data-rnum]');
	const rnum = $comment.dataset.rnum;
	const $rcontent = $comment.querySelector('.rcontent.modify');
	const url = `${replyInfo.url}/vote`;  //요청URLL
	const jsonObj = {
		"rnum" : rnum,
		"bnum" : replyInfo.bnum,
		"rid" : replyInfo.rid,
		"vote" : "GOOD"
	}
	ajaxCall.put(url, jsonObj, jsonObj => {
		switch(jsonObj.rtcd){
			case '00': //성공
				$comment.querySelector('.btngrp span.rgood').textContent = jsonObj.rtmsg.rgood;
				$comment.querySelector('.btngrp span.rbad').textContent = jsonObj.rtmsg.rbad;
				break;
			case '01': //실패
				jsonObj.rtmsg.forEach(item=>{
					console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
				});
				break;
		}		
	});	
}
//비선호
function rbadFn(evt){
	console.log('rbadFn!');
	//선호도 추가  <===============================	
	const $comment = evt.target.closest('div[data-rnum]');
	const rnum = $comment.dataset.rnum;
	const $rcontent = $comment.querySelector('.rcontent.modify');
	const url = `${replyInfo.url}/vote`;  //요청URL
	const jsonObj = {
		"rnum" : rnum,
		"bnum" : replyInfo.bnum,
		"rid" : replyInfo.rid,
		"vote" : "BAD"
	}
	ajaxCall.put(url, jsonObj, jsonObj => {
		
		switch(jsonObj.rtcd){
			case '00': //성공
				$comment.querySelector('.btngrp span.rgood').textContent = jsonObj.rtmsg.rgood;
				$comment.querySelector('.btngrp span.rbad').textContent = jsonObj.rtmsg.rbad;
				break;
			case '01': //실패
				jsonObj.rtmsg.forEach(item=>{
					console.log(`errmsg:${item.fieldName},${item.requestValue},${item.errMsg}`);
				});
				break;
		}					
	});		
}

//목록가져오기
function listHandler(list){
	let $innerHTML = '';
	list.forEach(item=>{
		if(item.rindent == 0){
			$innerHTML += `<div class="comment-parent" data-rnum="${item.rnum}" data-rid="${item.rid}" >`;
		}else{
			$innerHTML += `<div class="comment-child" data-rnum="${item.rnum}" data-rid="${item.rid}" data-prnum='${item.prnum}'>`;
		}
		
		$innerHTML += `  <div class="content">`;
		$innerHTML += `    <div class="content-left">`;		
	  $innerHTML += `			 <div class="profileImg">`;
	  $innerHTML += `				<img src="/portfolio/member/findProfileImg/${item.rid}/" alt="프로파일">`;
	  $innerHTML += `			 </div>`;
		$innerHTML += `    </div>`;
		$innerHTML += `    <div class="content-right">`;
		$innerHTML += `      <div class="writer">`;
		$innerHTML += `				 <span class="nickname">${item.rnickname}</span>`;
		
		//원글작성자와 댓글작성자가 동일한경우
		if(item.rid == replyInfo.bid){
		$innerHTML += `        <span class="bnumWriter"><i class="fas fa-crown"></i></span>`;
		}		
		//대댓글의 댓글의경우 대상표시
		if(item.rindent > 1){
		$innerHTML += `        <span class="replyTo">TO:${item.prnickname}</span>`;
		}
		
		$innerHTML += `			   <span class="cdate">${item.rcdate}</span>`;		
		$innerHTML += `      </div>`;
		$innerHTML += `      <div class="rcontent read">${item.rcontent}</div>`;
		$innerHTML += `      <div class="btngrp read">`;
		$innerHTML += `        <button class="prefer rgood" data-title="좋아요" data-button-name="rgood"><i class="fas fa-thumbs-up" ></i></button><span class="rgood">${item.rgood}</span>`;
		$innerHTML += `        <button class="prefer rbad"  data-title="싫어요" data-button-name="rbad"><i class="fas fa-thumbs-down" ></i></button><span class="rbad">${item.rbad}</span>`;
		$innerHTML += `        <button type="button" class="rereplyBtn" data-button-name="rereply">답글</button>`;
		$innerHTML += `      </div>`;
		$innerHTML += `      <div class="hiddenItem hidden">`;
		$innerHTML += `        <button class="icon"><img src="/portfolio/img/board/icon_more_vert.svg" alt=""></button>`;
		$innerHTML += `        <ul class="menu hidden">`;
		$innerHTML += `          <li><button class="modifyBtn" data-button-name="modify"><i class="fas fa-eraser"></i>수정</button></li>`;
		$innerHTML += `          <li><button class="deleteBtn" data-button-name="delete"><i class="fas fa-trash-alt"></i>삭제</button></li>`;
		$innerHTML += `       </ul>`;
		$innerHTML += `       <ul class="menu2 hidden">`;
		$innerHTML += `        <li><button class="chargeBtn" data-button-name="charge"><i class="fas fa-bullhorn"></i>신고</button></li>`;
		$innerHTML += `       </ul>             `;
		$innerHTML += `      </div>`;
		$innerHTML += `    </div>`;
		$innerHTML += `  </div>`;
		$innerHTML += `</div>`;		
	});
	
	//const $comments = document.querySelector('.wrapper-comments .comments')
	$comments.innerHTML = $innerHTML;
	
	const $commentParents = $comments.querySelectorAll('.comment-parent');
	const $commentchilds = $comments.querySelectorAll('.comment-child');
	const $hiddenItemIcons = $comments.querySelectorAll('.hiddenItem .icon');	
	
	const $profileImgs = $comments.querySelectorAll('.profileImg img');

	//이미지 부재시 디폴트이미지
	[...$profileImgs].forEach(item=>item.addEventListener('error',evt=>{
		evt.target.src = '/portfolio/img/board/profile.png';
	}));
		
	/* 로그인 했을때만 메뉴 보여줌 */
	if(loginChk){
		//숨김메뉴 마우스오버시 표시
		[...$commentParents, ...$commentchilds].forEach(ele=>ele.addEventListener('mouseover',evt=>{
			evt.currentTarget.querySelector('.hiddenItem').classList.remove('hidden');
		}));
		//숨김메뉴 마우스아웃시 숨김
		[...$commentParents, ...$commentchilds].forEach(ele=>ele.addEventListener('mouseout',evt=>{
			evt.currentTarget.querySelector('.hiddenItem').classList.add('hidden');
		}));
		
		//숨김아이콘 클릭시 수정, 삭제메뉴 제어
		[...$hiddenItemIcons].forEach(ele=>ele.addEventListener('click',evt=>{
			//로긴 id와 댁글작성자 id가 같으면 보여줌.
			// debugger;
			const rid = ele.closest('div[data-rnum]').dataset.rid
			if(replyInfo.rid == rid){
				const $menus = [...document.querySelectorAll('.hiddenItem .menu')];
				$menus.filter(ele=>ele === evt.currentTarget.nextElementSibling)
							.forEach(ele=>ele.classList.toggle('hidden'));
				$menus.filter(ele=>ele !== evt.currentTarget.nextElementSibling)
							.filter(ele=>!ele.classList.contains('hidden'))
							.forEach(ele=>ele.classList.add('hidden'));
			}else{
				const $menus = [...document.querySelectorAll('.hiddenItem .menu2')];
				$menus.filter(ele=>ele === evt.currentTarget.nextElementSibling.nextElementSibling)
							.forEach(ele=>ele.classList.toggle('hidden'));
				$menus.filter(ele=>ele !== evt.currentTarget.nextElementSibling.nextElementSibling)
							.filter(ele=>!ele.classList.contains('hidden'))
							.forEach(ele=>ele.classList.add('hidden'));		
			}
		}));	
	}
}
//취소
function cancelFn(evt){
	console.log('cancelFn!');
	const $parent = evt.target.closest('.comments');
	const $child = evt.target.closest('.comment.rewrite');
	$parent.removeChild($child);
}
//신고
function chargeFn(evt){
	console.log('신고기능 미구현!!');
}

//페이징
function addPaging(pc){
	let str = "";
	str += `<ul>`;
	//이전페이지 여부
	if(pc.prev){
		str += `  <li><a href="1"><i class="fas fa-angle-double-left"></i></a></li>`;
		str += `  <li><a href="${pc.startPage-1}"><i class="fas fa-angle-left"></i></a></li>`;
	}
	
	//페이지 1~10
	for(let start=pc.startPage , end=pc.endPage; start <= end; start++){
		//현재 페이지와 요청페이지가 같으면 배경색  구분토록한다.
		if(pc.rc.reqPage == start){
		  str += `  <li><a href="${start}" class="active">${start}</a></li>`;
		}else{
			str += `  <li><a href="${start}">${start}</a></li>`;
		}
	}

	//다음페이지 여부
	if(pc.next){
	str += `  <li><a href="${pc.endPage+1}"><i class="fas fa-angle-right"></i></a></li>`;
	str += `  <li><a href="${pc.finalEndPage}"><i class="fas fa-angle-double-right"></i></a></li>`;
	}
	str += `</ul>`;
	$paging.innerHTML = str;
	
}
