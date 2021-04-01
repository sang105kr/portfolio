'use strict'; 
 
const $loginBtn = document.getElementById('loginBtn');
const loginHandler = evt => {

  evt.preventDefault();//기본이벤트 막기

  console.log('로그인클릭됨!');
  /* 유효성체크 */
  //1)아이디체크
  //1-1)입력유무 
  const $id = document.getElementById('member_id');
  if($id.value.trim().length == 0){
    document.getElementById('errmsg_member_id').textContent = '아이디를 입력하세요';
    $member_id.focus();
    return;
  }
  //2)비밀번호체크
  const $pw = document.getElementById('pw');
  if($pw.value.trim().length == 0){
    document.getElementById('errmsg_member_id').textContent = '';
    document.getElementById('errmsg_pw').textContent = '비밀번호를 입력하세요';
    $pw.focus();
    return;
  }
 
  document.getElementById('loginForm').submit();
}
$loginBtn.addEventListener('click',loginHandler);
