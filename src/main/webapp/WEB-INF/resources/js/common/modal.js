let $appendmyModalHTML = '';
$appendmyModalHTML += `<div class="myModal hidden">`;
$appendmyModalHTML += `  <div class="myModal__overlay"></div>`;
$appendmyModalHTML += `  <div class="myModal__content">`;
$appendmyModalHTML += `    <div class="myModal__header">`;
$appendmyModalHTML += `      <div class="myModal__title">`;
$appendmyModalHTML += `        <i class="fas fa-trash-alt"></i>`;
$appendmyModalHTML += `        <span>댓글 삭제</span>`;
$appendmyModalHTML += `      </div>`;
$appendmyModalHTML += `    </div>`;
$appendmyModalHTML += `    <div class="myModal__body">댓글을 완전히 삭제할까요?</div>`;
$appendmyModalHTML += `    <div class="myModal__footer">`;
$appendmyModalHTML += `      <button class="mybtn myModal__cancel">취소</button>`;
$appendmyModalHTML += `      <button class="mybtn myModal__delete">삭제</button>`;
$appendmyModalHTML += `    </div>`;
$appendmyModalHTML += `  </div>`;
$appendmyModalHTML += `</div>  `;

//현재문서 하단에 modal요소 추가  
document.body.insertAdjacentHTML('beforeend',$appendmyModalHTML)
//모달
const $myModal = document.querySelector('.myModal');
const $myModalCancelBtn = $myModal.querySelector('.myModal__cancel');
const $myModalDeletBtn = $myModal.querySelector('.myModal__delete');
const $myModalOveray = $myModal.querySelector('.myModal__overlay');
//-- 모달 이벤트 등록 시작
$myModalOveray.addEventListener("click",(e)=>{
  // $myModal.classList.add('hidden');
});
//취소
$myModalCancelBtn.addEventListener("click",(e)=>{
	document.body.style.overflow='initial';
  $myModal.classList.add('hidden');
});
//삭제
$myModalDeletBtn.addEventListener("click",(e)=>{
  window.myCurrEvent(); //모달을 사용하고자하는 페이지에서 window.myCurrEvent함수구현
  document.body.style.overflow='initial';
  $myModal.classList.add('hidden');	
}) ;    
	//-- 모달 이벤트 등록 종료