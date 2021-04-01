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
	let   toID;
	let   toNickname;
	
	window.addEventListener('load',init);	
	function init(){

    messageinput.addEventListener("keyup", evt => { 
        console.log(evt.key);
        if (evt.key === 'Enter') send(); 
    });    
	}
	function send(){
 	  const jsObject = { 
 			fromID,											//송신자 아이디
 			fromNickname,								//송신자 별칭
 			toID,												//수신자 아이디
 			message:messageinput.value  //송신 메세지
 		}
		console.log('송신')
		console.log(jsObject);
		window.opener.send(jsObject);
		messageinput.value = '';
	}
    
  function receive(jsObject){
		console.log('수신')
		console.log(jsObject);
		if(!toID){
	  	toID = jsObject.fromID;
	  	toNickname = jsObject.fromNickname;
		}
	  const messages=document.getElementById("messages");	  
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