<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath }/css/modal.css">
<link rel="stylesheet" href="${contextPath }/css/board/reply.css">
<script defer src="${contextPath }/js/common/modal.js"></script>
<script defer type="module" src="${contextPath }/js/board/reply.js"></script>
<script>
	const replyInfo = {
		bnum : '${boardVO.bnum }', //최초원글 번호
		bid  : '${boardVO.bid}',   //게시원글 id
	  rid  : '${sessionScope.member.member_id}', //댓글작성자 id
		rnickname : '${sessionScope.member.nickname}', //댓글작성자 별칭
		url  :  '${contextPath}/rboard' //공통url	 
	};
</script>
<!--댓글 영역-->
<div class="wrapper-comments">
  <!--댓글 작성-->
	<div class="comment write"></div>
  <!--댓글 목록-->
  <div class="comments"></div>
  <!--페이징-->
  <div class="paging"></div>
</div>
 