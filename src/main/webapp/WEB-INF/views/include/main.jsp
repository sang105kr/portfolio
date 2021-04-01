<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/css/main.css" />     
 <main>
   <div class="container container-m">
<!--       <section class="s1">section1</section>
      <section class="s2">section2</section>
      <section class="s3">section3</section> -->
     
	  <div class="mywrap">
	    <ul class="">
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/5e/2f/13/5e2f13977b568195f5ac6fc95b756a64.jpg" alt="후르츠아이스크림">
	          <h2 class="title">후르츠아이스크림</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/a3/c8/91/a3c891a1d19480606bbc6b3ac26425be.jpg" alt="카페모카">
	          <h2 class="title">카페모카</h2 class="title">
	            <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/28/5a/42/285a42a27dd3f85c70d692cb9768c1bb.jpg" alt="케익">
	          <h2 class="title">케익</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/5f/dc/85/5fdc85e50848246dd3dc4726adee9c63.jpg" alt="카페라떼">
	          <h2 class="title">카페라떼</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/88/ba/11/88ba11d27b36c2dd3d15e6956bd8733d.jpg" alt="모카 케익">
	          <h2 class="title">모카 케익</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/d7/6b/52/d76b52c9083487abdbfb96d9c91c9f8b.jpg" alt="쥬스">
	          <h2 class="title">파르페</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/9d/0b/e8/9d0be84f0d542f25c513101a496b09fd.jpg" alt="모지또">
	          <h2 class="title">모지또</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>      
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/db/6e/b1/db6eb1737b56095ef94e82a0301dd381.jpg" alt="티라미수">
	          <h2 class="title">티라미수</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	      <li>
	        <a href='#'>
	          <img src="https://i.pinimg.com/564x/6b/84/8f/6b848f43c2b7a8ca92b4eaa827bcd0ff.jpg" alt="음료">
	          <h2 class="title">음료</h2 class="title">
	          <p class="intro">Lorem ipsum dolor sit amet consectetur adipisicing elit. In quae optio, corrupti, quasi quis cum quod nisi ducimus incidunt, vero fuga aliquid doloremque! Illum minus dolorum sunt laborum qui rem.</p>
	        </a>
	      </li>
	    </ul>
    </div> 
    <!-- 1:1 문의 -->
    <div class="consulting">
   	  <i class="fas fa-comment-dots"></i><span>1:1문의</span>
    </div>
 	  </div>      
  </main>
  <script>
  	const loginOK = '${sessionScope.member.nickname }';
		const $consulting = document.querySelector('.consulting');

		if(loginOK.trim().length >  1 ){
			$consulting.addEventListener('click',()=>{
				openWindowPop('/portfolio/clientChat','상담');
			});
		}
		function openWindowPop(url, name){
		    var options = 'top=10, left=10, width=500, height=320, location=no, status=no, menubar=no, toolbar=no, resizable=no, scrollbar=no';
		    window.open(url, name, options);
		}		
  </script>