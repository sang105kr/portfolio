<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
  <!--common -->
	<%@ include file="/WEB-INF/views/include/common.jsp"%>  
  <title>Document</title>
  <link rel="stylesheet" href="/portfolio/css/layout.css">
  <link rel="stylesheet" href="/portfolio/css/member.css">
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
			  <form action="/portfolio/member/modify" method="post">
			    <ul>
			      <li><label>아이디</label></li>
			      <li><input type="text" name="member_id" id="" value="${memberVO.member_id }" readonly></li>
<!-- 			      <li><label>비밀번호</label></li>
			      <li><input type="password" name="pw" id="" ></li> -->
			      <li><label>전화번호</label></li>
			      <li><input type="tel" name="tel" id="" value="${memberVO.tel }"></li>
			      <li><label>별칭</label></li>
			      <li><input type="text" name="nickname" id="" value="${memberVO.nickname }"></li>
			      <li><label>지역</label></li>
			      <li>
			        <select name="region" id="">
			          <option value="">선택</option>
			          <option value="서울" ${(memberVO.region=="서울") ? "selected":""}>서울</option>
			          <option value="부산" ${(memberVO.region=="부산") ? "selected":""}>부산</option>
			          <option value="울산" ${(memberVO.region=="울산") ? "selected":""}>울산</option>
			        </select>
			      </li>
			      <li><label>성별</label></li>
			      <li>
			        <input type="radio" name="gender" id="man" value="남" ${(memberVO.gender=="남") ? "checked":""}><label for="man">남</label>
			        <input type="radio" name="gender" id="woman" value="여" ${(memberVO.gender=="여") ? "checked":""}><label for="woman">여</label>
			      </li>
			      <li><label>생년월일</label></li>
			      <li><input type="date" name="birth" id="" value="${memberVO.birth }"></li>
			      <li><input type="submit" value="회원수정"></li>
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