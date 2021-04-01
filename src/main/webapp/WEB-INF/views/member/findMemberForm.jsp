<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<title>아이디 찾기</title>
<link rel="stylesheet" href="${contextPath }/css/member/findMemberId.css">
<script src="${contextPath }/js/common/ajax.js"></script>
<script defer src="${contextPath }/js/member/findMemberId.js"></script>
</head>
<body>
  <main>
		<div class='wrap-findId'>
			<h3>아이디 찾기</h3>
			<ul>
				<li>
				  <label for="tel" >전화번호</label>
				  <input type="tel" name="tel" id="tel" placeholder="ex)010-1234-5678">
				</li>
				<li>
				  <label for="birth">생년월일</label>
				  <input type="date" name="birth" id="birth" placeholder="ex)20000101">
				</li>
				<li id="findedId"></li>
				<li>				
					<button id="findIdBtn" class="btn btn-primary" type="button">
					  <span class="visually-hidden spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
					  아이디 찾기
					</button>					
				</li>
			</ul>
		</div>
	</main>	
</body>
</html>