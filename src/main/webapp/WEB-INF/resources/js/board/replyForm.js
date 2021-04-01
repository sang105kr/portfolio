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
				const wordCountPlugin = editor.plugins.get( 'WordCount' );
        const wordCountWrapper = document.getElementById( 'word-count' );
        wordCountWrapper.appendChild( wordCountPlugin.wordCountContainer );
			} )
			.catch( error => {
				console.error( error );
			} );

//등록버튼 클릭시
writeBtn.addEventListener('click',(evt)=>{
	console.log('등록버튼 클릭됨!');
	// 1) form요소의 submit()기본 이벤트 막기
	evt.preventDefault();
	// 2) 유효성체크
	//if(validChk()){ 
		// 3) form요소의 submit();실행
		writeForm.submit();
	//}
});

//취소버튼 클릭시
cancelBtn.addEventListener('click',(evt)=>{
	console.log('취소버튼 클릭됨!');
	evt.preventDefault();
	writeForm.reset();
});

//목록버튼 클릭시
listBtn.addEventListener('click',(evt)=>{
	console.log('목록버튼 클릭됨!');
	evt.preventDefault();
	location.href = '/portfolio/board/list';
});

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









