<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/common.jsp"%>    
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="${contextPath }/css/member/findPWForm.css" />
<script defer type="module" src="${contextPath }/js/common/common.mjs"></script>
<script defer src="${contextPath }/js/common/ajax.js"></script>
<script defer type="module" src="${contextPath }/js/member/findPW.js"></script>
</head>
<body>
	<main>
		<div class='wrap-findpw'>
			<h3>비밀번호 찾기</h3>
			<ul>
				<li>
					<label for="member_id">아이디</label>
					<input type="text" name="member_id" id="member_id" />
				</li>
				<li>
					<label for="tel">연락처</label>
					<input type="text" name="tel" id="tel" />
				</li>
				<li>
					<label for="birth">생년월일</label>
					<input type="date" name="birth" id="birth" />
				</li>
				<li>
					<span id="findedPw"></span>					
				</li>
				<li>					
					<button id="findPwBtn" class="btn btn-primary" type="button">
					  <span class="visually-hidden spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
					  비밀번호찾기
					</button>
				</li>
			</ul>
		</div>
	</main>
</body>
</html>