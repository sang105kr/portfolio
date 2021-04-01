<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${contextPath }/css/board/reply.css">
<script defer src="${contextPath }/js/common/ajax.js"></script>
<script defer src="${contextPath }/js/board/reply.js"></script>

<!--댓글 영역-->
<div class="wrapper-comments">
  <!--댓글 작성-->
  <div class="comment write">
    <div class="profileImg">
      <img src="https://picsum.photos/50" alt="프로파일">
    </div>
    <div class="rcontent" contenteditable data-placeholder="댓글 추가..."></div>
    <div class="btngrp">
      <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>
      <button type="button" class="writeBtn"  data-button-name="write">댓글</button>
    </div>
  </div>
  <!--댓글 목록-->
  <div class="comments">
    <!--depth1-->
    <div class="comment-parent" data-rnum="">
      <div class="profileImg"><img src="https://picsum.photos/50" alt="프로파일"></div>
      <div class="writer"><span class="nickname">별칭</span><span class="cdate">작성일시</span></div>
      <div class="rcontent">댓글1</div>
      <div class="btngrp">
        <button class="prefer rgood" data-title="좋아요" data-button-name="rgood"><i class="fas fa-thumbs-up" ></i></button><span class="rgood">3</span>
        <button class="prefer rbad"  data-title="싫어요" data-button-name="rbad"><i class="fas fa-thumbs-down" ></i></button><span class="rbad">2</span>
        <button type="button" class="rereplyBtn" data-button-name="rereply">답글</button>
      </div>
      <div class="hiddenItem">
        <button class="icon"><img src="${contextPath }/img/board/icon_more_vert.svg" alt=""></button>
        <ul class="menu">
          <li><button class="modifyBtn" data-button-name="modify"><i class="fas fa-eraser"></i>수정</button></li>
          <li><button class="deleteBtn" data-button-name="delete"><i class="fas fa-trash-alt"></i>삭제</button></li>            
       </ul>
      </div> 
    </div>
    <!-- <div class="comment">
      <div class="profileImg">
        <img src="https://picsum.photos/50" alt="프로파일">
      </div>
      <div class="rcontent" contenteditable data-placeholder="댓글 추가..."></div>
      <div class="btngrp">
        <button type="button" class="cancelBtn" data-button-name="cancel">취소</button>
        <button type="button" class="writeBtn"  data-button-name="write">댓글</button>
      </div>
    </div> -->

    <!--depth2-->
    <div class="comment-child" data-rnum="" data="prnum">
      <div class="profileImg"> <img src="https://picsum.photos/50" alt="프로파일"> </div>
      <div class="writer"><span class="nickname">별칭</span><span class="cdate">작성일시</span></div>
      <div class="rcontent">대댓글1</div>
      <div class="btngrp">
        <button class="prefer rgood" data-title="좋아요" data-button-name="rgood"><i class="fas fa-thumbs-up" ></i></button><span class="rgood">3</span>
        <button class="prefer rbad"  data-title="싫어요" data-button-name="rbad"><i class="fas fa-thumbs-down" ></i></button><span class="rbad">2</span>
        <button type="button" class="rereplyBtn" data-button-name="rereply">답글</button>
      </div>
      <div class="hiddenItem">
        <button class="icon"><img src="./icon_more_vert.svg" alt=""></button>
        <ul class="menu">
          <li><button class="modifyBtn" data-button-name="modify"><i class="fas fa-eraser"></i>수정</button></li>
          <li><button class="deleteBtn" data-button-name="delete"><i class="fas fa-trash-alt"></i>삭제</button></li>
        </ul>
      </div>        
    </div>
  </div>
  <!--페이징-->
  <div class="paging"></div>
</div>
 