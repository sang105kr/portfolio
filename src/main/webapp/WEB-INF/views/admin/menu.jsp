<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <style>   
	nav .container-n {
	  justify-content:flex-start;
	}    
	</style>
  <nav>
    <div class="container container-n">
      <ul class="depth1">
        <li>
          <a href="#">회원정보</a>
          <ul class="depth2">
            <li><a href="#">회원목록</a></li>
            <li><a href="#">임시</a></li>
            <li><a href="#">임시</a></li>
          </ul>
        </li>
        <li>
          <a href="#">상담</a>
          <ul class="depth2">
            <li><a href="${contextPath }/admin/consulting">1:1문의</a></li>
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
      </ul>
    </div>
  </nav>