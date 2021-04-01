<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
  <!--common -->
	<%@ include file="/WEB-INF/views/include/common.jsp"%>  
<title>채팅방</title>
<style>
.container .content #chatroom{
	margin:0 auto;
	margin-top:30px;
}

.container .content #messages {
	margin-bottom: 10px; border : 1px solid gray;
	overflow: auto;
	border: 1px solid gray;
	line-height:1.5rem;
	height:200px;
}

.container .content #messageinput {
	width:100%;
	margin-bottom:10px;
}
</style>

<script>
	const fromID = '${sessionScope.member.member_id }';
	const fromNickname = '${sessionScope.member.nickname }';
	const toID = 'consultant@test.com';
	window.addEventListener("load",init,false);
	window.addEventListener('beforeunload',(evt)=>{
		evt.preventDefault();
		console.log('창닫기');
		closeSocket();
		evt.returnValue = '';
		
	});
	
	let ws, messages;
	function init(){
    messages=document.getElementById("messages");
    openSocket();
    messageinput.addEventListener("keyup", evt => { 
        if (evt.key === 'Enter') send(evt); 
    });
	}
  
  function openSocket(){
    	//1)웹소켓 생성 유무 판단
      if(ws!==undefined && ws.readyState!==WebSocket.CLOSED){
          writeResponse("WebSocket is already opened.");
          return;
      }
      //2)웹소켓 객체 생성 
      ws=new WebSocket("ws://<%=request.getServerName()%>:<%=request.getServerPort()%>/portfolio/consult-socket");
      
      ws.addEventListener("open",evt=>{
          if(evt.data === undefined) return;
          console.log('연결:'+evt.data);
          //writeResponse(evt.data);
      },false);
      
      ws.addEventListener("message",evt=>writeResponse(evt.data),false);
      
      ws.addEventListener("close",evt=>{
    	  writeResponse(evt.data);
    	},false);
  }
  
  function send(evt){
	  const jsonObject = { 
			fromID,											//송신자 아이디
			fromNickname,								//송신자 별칭
			toID,												//수신자 아이디
			message:messageinput.value  //송신 메세지
		}
		ws.send(JSON.stringify(jsonObject));
	  messageinput.value = "";
  }
  
  function closeSocket(evt){
	  writeResponse("접속종료되었습니다.");
		ws.close();
  }
  
  function writeResponse(data){
	  const jsObject = JSON.parse(data);

	  messages.innerHTML+="<div>";
	  messages.innerHTML+="<span style='color:black'>"+jsObject.fromNickname;
	  messages.innerHTML+="("+jsObject.fromID+")</span>&nbsp";
		messages.innerHTML+="<span style='color:blue'>"+jsObject.message+"</span>";
		messages.innerHTML+="</div>";		  

	  //스크롤이동  
	  messages.scrollTop = messages.scrollHeight;	 
  }
</script>
</head>
<body>
	<main>
		<div class="container">
			<div class="content">
				<div id="chatroom">
					<div><i class="fas fa-comment-dots"></i><span>1:1문의</span></div>
					<div id="messages"></div>
					<div>
						<input type="text" id="messageinput">
					</div>
				</div>
			</div>
		</div>
	</main>
</body>
</html>