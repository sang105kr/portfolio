<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <div class="uppermost">
    <div class="container container-um">
      <ul class="logo">
        <li><a href="/portfolio"><i class="fas fa-coffee"></i></a></li>
        <li id="logo-txt">아리에떼[[Ariete]]</li>
      </ul>     
      <!-- 로그인전 -->
      <c:if test="${empty sessionScope.member}">
      <ul class="menu">
        <li><a href="/portfolio/loginForm">로그인</a></li>
        <li>|</li>
        <li><a href="/portfolio/member/joinForm">회원가입</a></li>
      </ul>
      </c:if>
      <!-- 로그인후 -->
      <c:if test="${!empty sessionScope.member}">
      <ul class="menu">
        <li><a href="/portfolio/member/mypage">${sessionScope.member.nickname}</a></li>
        <li>|</li>
        <li><a href="/portfolio/logout">로그아웃</a></li>
      </ul>   
			<script>	      
				window.addEventListener('load',()=>{
					const toastElList = [].slice.call(document.querySelectorAll('.toast'))
					const toastList = toastElList.map(function (toastEl) {
					  return new bootstrap.Toast(toastEl, {
								'animation':true,
								'autohide':true,
								'delay':3000
						  });
					})
										
          const url = 'ws://localhost:9080/portfolio/loginout-socket';
          const ws = new WebSocket(url);
          ws.addEventListener("open",() => {
             console.log('연결 성공!!');
          });
          ws.addEventListener("message",evt => {
        	  const $toast = document.querySelector('#liveToast');
        	  $toast.querySelector('.toast-header .me-auto').textContent = '알림';
        	  $toast.querySelector('.toast-header small').textContent = '방금';
        	  $toast.querySelector('.toast-body').textContent = evt.data;
						$toast.classList.replace('hide','show');
					});			
				});

  		</script>
      </c:if>
      <c:set var="consultant" value="consultant@test.com" />
      <c:if test="${!empty sessionScope.member && sessionScope.member.member_id eq consultant}">
      <script>
      	const CONSULTANT = 'consultant@test.com';
      	let ws2;
        openSocket();
        function openSocket(){
		      ws2=new WebSocket("ws://<%=request.getServerName()%>:<%=request.getServerPort()%>/portfolio/consult-socket");
		      
		      ws2.addEventListener("open",evt=>{
		          //if(evt.data === undefined) return;
		          console.log('연결:'+evt.data);
		          //writeResponse(evt.data);
		      },false);
		      
		      ws2.addEventListener("message",evt=>writeResponse(evt.data),false);
		      
		      ws2.addEventListener("close",evt=>{
		    	  writeResponse(evt.data);
		    	},false);
        }
	      function send(jsObject){
	    		console.log('message:'+jsObject);
	    		ws2.send(JSON.stringify(jsObject));
	      }
	      
	      function closeSocket(evt){
		      console.log('closeSocket호출됨!!');
	    	  //writeResponse("접속종료되었습니다.");
	    		ws2.close();
	      }
	      
	      const chatWindow = new Map();		//대화창 중복 팝업방지
	      function writeResponse(data){
	    	  const jsObject = JSON.parse(data);
					const url = '/portfolio/serverChat';
			    const options = 'top=10, left=10, width=500, height=320, location=no, status=no, menubar=no, toolbar=no, resizable=no, scrollbar=no';
			    
					let win;
			    if(!chatWindow.has(jsObject.fromID)){
			    	if( jsObject.fromID === CONSULTANT){
						  win = chatWindow.get(jsObject.toID);
							win.receive(jsObject);
			    	}else{
				    	win =  window.open(url, name, options);
				    	chatWindow.set(jsObject.fromID,win);	    	
							win.addEventListener('load',()=>{
								win.receive(jsObject);
							});
			    	}					    	
				  }else{
					  win = chatWindow.get(jsObject.fromID);
						if(!win.closed){			
							win.receive(jsObject);
						}else{
					    	win =  window.open(url, name, options);
					    	chatWindow.set(jsObject.fromID,win);	    	
								win.addEventListener('load',()=>{
									win.receive(jsObject);
								});
						}
					} 
	      }	    	
      </script>
      </c:if>
    </div>
  </div>
	<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 5">
	  <div id="liveToast" class="toast fade hide" role="alert" aria-live="assertive" aria-atomic="true">
	    <div class="toast-header">
	      <i class="fab fa-twitter-square rounded me-2"></i>
	      <strong class="me-auto"></strong>
	      <small></small>
	      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
	    </div>
	    <div class="toast-body"></div>
	  </div>	  
	</div>
