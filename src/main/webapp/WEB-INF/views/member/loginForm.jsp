<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
  <link rel="stylesheet" href="${contextPath }/css/member/login.css">
  <script defer src="${contextPath }/js/member/login.js"></script>
</head>
<body>
  <div class="container">
    <div class="content">
      <form id="loginForm" action="${contextPath }/login" method="post">
        <div class="item">
          <a href="${contextPath }">
            <img src="https://cdn.pixabay.com/photo/2015/07/12/14/26/coffee-842020_960_720.jpg" alt="" />
          </a>
        </div>
        <div class="item">
          <input type="text" name="member_id" id="member_id" placeholder="아이디" /> <i class="far fa-envelope"></i>
        </div>
        <div class="item">
          <span class="errmsg" id="errmsg_member_id"></span>
        </div>
        <div class="item">
          <input type="password" name="pw" id="pw" placeholder="비밀번호" /> <i class="fas fa-key"></i>
        </div>
        <div class="item">
          <span class="errmsg" id="errmsg_pw"></span>
        </div>
        <div class="item">
          <span class="svr_msg" id="svr_smg">${svr_msg }</span>
        </div>        
        <div class="item">
          <button id="loginBtn">로그인</button>
        </div>
        <div class="item">
          <input type="checkbox" name="login_chk" id="login_chk" /> 
          <label for="login_chk" id="login_chk">로그인상태유지</label>
        </div>
        <div class="item find_info">
          <a href="${contextPath }/member/findMemberForm" id="findID">아이디 찾기</a> <span>|</span>
          <a href="${contextPath }/member/findPwForm" id="findPW">비밀번호 찾기</a> <span>|</span>
          <a href="${contextPath }/member/joinForm">회원 가입</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>