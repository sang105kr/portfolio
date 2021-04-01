'use strict';

//textArea => ck_editor 대체
	ClassicEditor
			.create( document.querySelector( '#bcontent' ), {
				removePlugins: ['Title'],
				toolbar: {
					items: [
						'heading',
						'|',
						'underline',
						'bold',
						'italic',
						'link',
						'|',
						'bulletedList',
						'numberedList',
						'alignment',
						'|',
						'imageUpload',
						'blockQuote',
						'insertTable',
						'mediaEmbed',
						'undo',
						'redo',
						'|',
						'fontBackgroundColor',
						'fontColor',
						'fontSize',
						'fontFamily',
						'highlight',
					]
				},
				language: 'ko',
				image: {
					toolbar: [
						'imageTextAlternative',
						'imageStyle:full',
						'imageStyle:side'
					]
				},
				table: {
					contentToolbar: [
						'tableColumn',
						'tableRow',
						'mergeTableCells',
						'tableCellProperties',
						'tableProperties'
					]
				},
			} )
			.then( editor => {
				window.editor = editor;
				editor.isReadOnly = true;
				const wordCountPlugin = editor.plugins.get( 'WordCount' );
        const wordCountWrapper = document.getElementById( 'word-count' );
        wordCountWrapper.appendChild( wordCountPlugin.wordCountContainer );
			} )
			.catch( error => {
				console.error( error );
			} );

const modifyBtn = document.getElementById('modifyBtn');
const deleteBtn = document.getElementById('deleteBtn');
const $modal = document.querySelector('.modal');

$modal.addEventListener('show.bs.modal',evt=>{
  console.log('show');
  const word = evt.relatedTarget.textContent;
  document.querySelector('.modal .badge.bg-danger').textContent = word;
  document.querySelector('.modal .modal-body').textContent = 
    `${word} 하시겠습니까?`;
});

//답글
if(replyBtn){
  replyBtn.addEventListener('click',evt=>{
    console.log('답글 클릭!');
		const bnum = document.querySelector('#writeForm input[type=hidden][name=bnum]').value;
		location.href=`/portfolio/board/replyForm/${bnum}`;
  });
}  
//수정
if(modifyBtn){
  modifyBtn.addEventListener('click',evt=>{
    console.log('수정 클릭!');
		//0)제목 변경 => 게시글 수정
		title.textContent = '게시글 수정';
		title.style.textAlign='center';
		
    //1)분류,제목,내용 => disabled, readyOnly 속성제거
    cid.removeAttribute('disabled');
    btitle.removeAttribute('readonly');
    bcontent.removeAttribute('readonly');

    //2)수정모드 전환
		// 수정모드에서 필요한 요소를 보이도록 설정
		const umodes = document.querySelectorAll('.umode');
		Array.from(umodes).forEach(umode=>{
			umode.style.display='inline-block';
		});
		// 읽모드에서 필요한 요소를 숨김 설정
		const rmodes = document.querySelectorAll('.rmode');
		Array.from(rmodes).forEach(rmode=>{
			rmode.style.display='none';
		});
		
		//ck editor편집모드로 변환
		/*
		const domEditableElement=  document.querySelector( '.ck-editor__editable' );
		const editorInstance = domEditableElement.ckeditorInstance;
		*/
		editor.isReadOnly = false;
  });
}
if(deleteBtn){
  //삭제
  deleteBtn.addEventListener('click',evt=>{

  	const bnum = evt.target.getAttribute('data-bnum');		
		//location.href = '/portfolio/board/delete/'+bnum;  //<= es5
		location.href = `/portfolio/board/delete/${bnum}`;  //<= es6 문자열 템플릿
  });
}
//취소
if(cancelBtn){
  cancelBtn.addEventListener('click',evt=>{
    console.log('취소 클릭!');

		//0)제목 변경 => 게시글 보기
		title.textContent = '게시글 보기';
		title.style.textAlign='center';
		
    //1)분류,제목,내용 => disabled, readyOnly 속성추가
    cid.setAttribute('disabled','disabled');
    btitle.setAttribute('readonly','readonly');
    bcontent.setAttribute('readonly','readonly');

    //2)일기모드 전환
		// 수정모드에서 필요한 요소를 숨김 설정
		const umodes = document.querySelectorAll('.umode');
		Array.from(umodes).forEach(umode=>{
			umode.style.display='none';
		});
		// 읽모드에서 필요한 요소를 보이도록 설정
		const rmodes = document.querySelectorAll('.rmode');
		Array.from(rmodes).forEach(rmode=>{
			rmode.style.display='inline-block';
		});
		
		//ck editor읽모드로 변환
		/*
		const domEditableElement=  document.querySelector( '.ck-editor__editable' );
		const editorInstance = domEditableElement.ckeditorInstance;
		*/
		editor.isReadOnly = true;
  });
}
//저장
if(saveBtn){
  saveBtn.addEventListener('click',evt=>{
    console.log('저장 클릭!');
		evt.preventDefault();
		
		//유효성체크
		if(validChk()){
		
	    if(writeForm) {
	      writeForm.submit();
	    }
		}

  });
}
//목록
if(listBtn){
  listBtn.addEventListener('click',evt=>{
    console.log('목록 클릭!');
    location.href = '/portfolio/board/list';
  });
}
//첨부파일 개별 삭제
if(fileList){
	fileList.addEventListener('click',evt=>{
		console.log(evt.target); //이벤트가 실제 발생한요소
		console.log(evt.currentTarget); //addEventLister를 등록한 요소
		
		if(evt.target.tagName !== 'I') return;
		if(!confirm('삭제하시겠습니까?')) return;
		
		const url = evt.target.dataset.url;
		
		//AJAX call
    fetch(url, {
      method: 'DELETE'
    })
      .then(response => response.text())     
      .then(text => {console.log(text);handler(text,evt.target);})
      .catch(error => console.error(error));
		
	});
	function handler(text, target){
		if(text === 'ok'){
			console.log('삭제성공!');
			const $parent = target.closest('p');
			fileList.removeChild($parent);
		}else{
			console.log('삭제실패!');
		}
	}
}



//유효성체크
function validChk(){
	let result = true;
	//1) 카테고리 선택 유무
	if(cid.selectedIndex == 0) {
		cid_errmsg.textContent = '분류를 선택하세요!';
		cid.focus();
		result = false;
	}else{
		cid_errmsg.textContent = '';
	}
	//2) 제목 입력 여부
	if(btitle.value.trim().length == 0){
		btitle_errmsg.textContent = '제목을 입력하세요!';
		btitle.focus();
		btitle.select();
		result = false;
	}else{
		btitle_errmsg.textContent = '';
	}
	
	//3) 아이디 입력 여부
	if(bid.value.trim().length == 0){
		bid_errmsg.textContent = '아이디를 입력하세요!';
		bid.focus();
		bid.select();
		result = false;
	}else{
		bid_errmsg.textContent = '';
	}
		
	//4) 내용 입력 여부
	if(bcontent.value.trim().length == 0){
		bcontent_errmsg.textContent = '내용을 입려하세요!';
		bcontent.focus();
		bcontent.select();
		result = false;
	}else{
		bcontent_errmsg.textContent = '';
	}
		
	return result;
}