<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
  <!--common -->
	<%@ include file="/WEB-INF/views/include/common.jsp"%>  
  <title>회원 탈퇴</title>
  <link rel="stylesheet" href="${contextPath }/css/layout.css">
  <link rel="stylesheet" href="${contextPath }/css/member/member.css">
	<link rel="stylesheet" href="${contextPath }/css/member/outMemberForm.css" />
	<script defer src="${contextPath }/js/member/outMemberForm.js"></script>
</head>
<body>
  <!--upper most-->
	<%@ include file="/WEB-INF/views/include/uppermost.jsp"%>
  <!--header-->
<%-- 	<%@ include file="/WEB-INF/views/include/header.jsp"%> --%>
  <!--nav-->
	<%@ include file="/WEB-INF/views/member/menu.jsp"%>
  <!--main-->
  <main>
    <div class="container container-m">
      <section class="s1">  
			  <form id="outMemberForm" action="/portfolio/member/outMember" method="post">
			  	<input type="hidden" name="id" value="${sessionScope.member.member_id }" />
					<ul>	
						<li><h3 class="title">회원 탈퇴</h3></li>					
						<li><input type="password" name="currentpw" id="currentpw" placeholder="현재 비밀번호" /></li>					
						<li><span class="svr_msg">${svr_msg }</span></li>
						<li><button id="confirmBtn">탈퇴</button></li>
						<li><button id="cancelBtn">취소</button></li>
					</ul>
			  </form>      
      </section>
    </div>
  </main>
  <!--footer menu-->
<!--   <div class="footermenu">
    <div class="container container-fm">footermenu</div>
  </div> -->
  <!--footer-->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>






