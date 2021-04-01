<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--common -->
<%@ include file="/WEB-INF/views/include/common.jsp"%>
<title>회원가입</title>
<link rel="stylesheet" href="${contextPath }/css/member/joinForm.css" />
<script defer src='${contextPath }/js/member/joinForm.js'></script>
</head>
<body>
	<div class="container">
		<div class="content">
			<form:form id="joinForm"
								 modelAttribute="memberVO" 
								 action="${contextPath }/member/join" 
								 method="post">
				<ul>
					<li><h3 class="title">회원 가입</h3></li>
					<li><form:label path="member_id">아이디</form:label></li>
					<li><form:input type="text" path="member_id" /></li>
					<li><form:errors path="member_id" cssClass="errmsg"/></li>
					<li><span class="errmsg" id="errmsg_member_id"></span></li>
					<li><form:label path="pw">비밀번호</form:label></li>
					<li><form:input type="password" path="pw"/></li>
					<li><form:errors path="pw" cssClass="errmsg"/></li>
					<li><span class="errmsg" id="errmsg_pw"></span></li>
					<li><label>비밀번호 확인</label></li>
					<li><input type="password" id="pwchk"></li>
					<li><span class="errmsg" id="errmsg_pwchk"></span></li>
					<li><form:label path="tel">전화번호</form:label></li>
					<li><form:errors path="tel" cssClass="errmsg"/></li>
					<li><form:input type="tel" path="tel"/></li>
					<li><span class="errmsg" id="errmsg_tel"></span></li>
					<li><form:label path="nickname">별칭</form:label></li>
					<li><form:input type="text" path="nickname" /></li>
					<li><form:errors path="nickname" cssClass="errmsg"/></li>
					<li><span class="errmsg" id="errmsg_nickname"></span></li>
					<li><form:label path="region">지역</form:label></li>
					<li><form:select path="region">
							<option value="">선택</option>

							<form:options path="region" 
														items="${region }" 
														itemValue="code_id" 
														itemLabel="name" />

					</form:select></li>
					<li><form:errors path="region" cssClass="errmsg"/></li>
					<li><span class="errmsg" id="errmsg_region"></span></li>
					<li><form:label path="gender">성별</form:label></li>
					<li>
						<form:radiobuttons path="gender" 
															 items="${gender }"
															 itemValue="code_id"
															 itemLabel="name"/>
					</li>
					<li><form:errors path="gender" /></li>
					<li><span class="errmsg" id="errmsg_gender"></span></li>
					<li><form:label path="birth">생년월일</form:label></li>
					<li><form:input type="date" path="birth" /></li>
					<li><form:errors path="birth" /></li>					
					<li><form:label path="hobby">취미</form:label></li>
					<li>
						<div class="hobby">
							<form:checkboxes path="hobby"
															 items="${hobby }" 
															 itemValue="code_id" 
															 itemLabel="name"/>
						</div>
					</li>
					<li><form:errors path="hobby" /></li>		
					<li><span class="errmsg" id="errmsg_birth"></span></li>
					<li><input type="submit" value="회원가입" id="joinBtn"></li>
				</ul>
			</form:form>
		</div>
	</div>
</body>
</html>