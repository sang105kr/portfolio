<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="/portfolio/css/layout.css">
  <title>Document</title>
  <!--font awesome-->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"/>      
  <!--폰트-->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nanum+Gothic+Coding:wght@400;700&family=Nanum+Myeongjo:wght@400;700&family=Noto+Sans+KR:wght@100;400;900&display=swap"/>  
</head>
<body>
  <!--upper most-->
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
        <li><a href="/portfolio/member/mypage">내정보</a></li>
        <li>|</li>
        <li><a href="/portfolio/logout">로그아웃</a></li>
      </ul>      
      </c:if>
    </div>
  </div>
  <!--header-->
  <header>
    <div class="container container-h">
      <span class="copyright">그대의 커피향기속으로...</span>
    </div>
  </header>
  <!--nav-->
  <nav>
    <div class="container container-n">
      <ul class="depth1">
        <li>
          <a href="#">menu1</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu2</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu3</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu4</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu5</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu6</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
        <li>
          <a href="#">menu7</a>
          <ul class="depth2">
            <li><a href="#">menu2-1</a></li>
            <li><a href="#">menu2-2</a></li>
            <li><a href="#">menu2-3</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
  <!--main-->
  <main>
    <div class="container container-m">
      <section class="s1">section1</section>
      <section class="s2">section2</section>
      <section class="s3">section3</section>
    </div>
  </main>
  <!--footer menu-->
  <div class="footermenu">
    <div class="container container-fm">footermenu</div>
  </div>
  <!--footer-->
  <footer>
    <div class="container container-f">Cpyright by Gildong Hong. All Rights Reserved.</div>
  </footer>
</body>
</html>