'use strict';

//글쓰기 버튼 클릭시
writeBtn.addEventListener('click', evt => {
	location.href="/portfolio/board/writeForm";
});


//검색 버튼 클릭시
findBtn.addEventListener('click',evt=>{
	
	//검색어입력유무
	if(keyword.value.trim().length == 0){
		alert('검색어를 입력하세요');
		keyword.focus();
		keyword.select();
		return false;
	}
	
	location.href = `/portfolio/board/list/1/${searchType.value}/${keyword.value}`;
});

//검색입력창 엔터키 눌렀을때
keyword.addEventListener('keydown',evt=>{
	console.log(evt.key);
	if(evt.key == 'Enter'){ //엔터키
		evt.preventDefault();
		//검색어입력유무
		if(evt.target.value.trim().length == 0){
			alert('검색어를 입력하세요');
			evt.target.focus();
			evt.target.select();
			return false;
		}
	
		location.href = `/portfolio/board/list/1/${searchType.value}/${keyword.value}`;		

	}
});



