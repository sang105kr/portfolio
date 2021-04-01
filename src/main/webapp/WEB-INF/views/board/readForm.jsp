<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--common -->
<%@ include file="/WEB-INF/views/include/common.jsp"%>      
<title>게시글 작성</title>
<link rel="stylesheet" href="${contextPath }/css/layout.css">
<link rel="stylesheet" href="${contextPath }/css/board/readForm.css" />
<!-- ck editor -->
<!--script defer src="https://cdn.ckeditor.com/ckeditor5/25.0.0/classic/ckeditor.js"></script-->
<script defer src="${contextPath }/js/common/ckeditor.js"></script>
<script defer src="${contextPath }/js/board/readForm.js"></script>
</head>
<body>
  <!--upper most-->
	<%@ include file="/WEB-INF/views/include/uppermost.jsp"%>
  <!--nav-->
	<%@ include file="/WEB-INF/views/include/menu.jsp"%>
	<main>
		<div class="container">
			<div class="content">
				<form id='writeForm'
							action="${contextPath }/board/modify" 
							method="post"
							enctype="multipart/form-data">	
					<input type="hidden" name="bid" value="${boardVO.bid }"/>
					<input type="hidden" name="bnickname" value="${boardVO.bnickname }"/>
					<input type="hidden" name="bnum" value="${boardVO.bnum}">						
					<legend id="title">게시글 보기</legend>
					<ul>
						<li><label for="cid">분류</label></li>
						<li>
							<select name="boardCategoryVO.cid" id="cid" disabled="disabled">
								<option value=""></option>
								<c:forEach var="rec" items="${boardCategoryVO}">
								<option value="${rec.cid }" ${rec.cid == boardVO.boardCategoryVO.cid ? 'selected="selected"' : ''}>${rec.cname }</option>
								</c:forEach>							
							</select>
						</li>
						<li>
							<span class="clt_msg" id='cid_errmsg'></span>
							<span class="svr_msg">${svr_msg["valid_boardCategoryVO.cid"] }</span>
						</li>
						<li><label for="btitle">제목</label></li>
						<li><input type="text" name="btitle" id="btitle" value="${boardVO.btitle}" readonly="readonly"/></li>
						<li>
							<span class="clt_msg" id='btitle_errmsg'></span>
							<span class="svr_msg">${svr_msg["valid_btitle"] }</span>
						</li>
						<li><label for="bid">작성자</label></li>
						<li><input type="text" id="bid" 
											 value="${boardVO.bnickname }(${boardVO.bid })"
											 readonly="readonly"/>
						</li>
						<li>
							<span class="clt_msg" id='bid_errmsg'></span>
							<span class="svr_msg">${svr_msg.valid_bid }</span>
						</li>						
						<li><label for="bcontent">내용</label></li>
						<li>
							<textarea name="bcontent" id="bcontent" readonly="readonly" style="display:none">${boardVO.bcontent }</textarea>
							<div id="word-count"></div>
						</li>
						<li>
							<span class="clt_msg" id='bcontent_errmsg'></span>
							<span class="svr_msg">${svr_msg.valid_bcontent }</span>
						</li>						
						<li class="umode"><label for="files">첨부</label></li>
						<li class="umode"><input type="file" name="files" id="files" class="form-control" multiple="multiple"/></li>
						<li>
							<!-- 읽기모드 버튼 시작-->
							<button id="replyBtn" type="button" class="btn rmode btn-outline-success btn-sm">답글</button>
						
							<!-- 작성자만 수정,삭제 가능 -->
							<c:if test="${sessionScope.member.member_id eq boardVO.bid }">
							<button id="modifyBtn"  type="button" class="btn rmode btn-outline-danger btn-sm">수정</button>
							<button type="button" 
							        class="btn rmode btn-outline-dark btn-sm" 
							        data-bs-toggle="modal" data-bs-target="#popUp">삭제</button>
							</c:if>
							<!-- 작성자만 수정,삭제 가능 끝 -->
							<!-- 읽기모드 버튼 시작 끝-->	
							
							<!-- 수정모드 버튼 시작-->
							<button id="cancelBtn"  type="button" class="btn umode btn-outline-danger btn-sm">취소</button>
							<button id="saveBtn"  type="button" class="btn umode btn-outline-dark btn-sm">저장</button>
							<!-- 수정모드 버튼 끝-->
							
							<!-- 공통버튼 -->
							<button id="listBtn"  type="button" class="btn btn-outline-dark btn-sm">목록</button>
						</li>
						<!-- 첨부목록 -->
						<li><label>첨부목록</label></li>
						<li>
							<c:if test="${!empty files }"> 
							<div id="fileList">
								<c:forEach var="file" items="${files}">
									<p>
 										<a href="${contextPath }/board/file/${file.fid }">
<%-- 										<a href="javascript:void(window.open('${contextPath }/board/file/${file.fid }','pic','width=300,height=300,top=300,left=200'))"> --%>
<%-- 										<img src="${contextPath }/board/file/${file.fid }" alt="" /> --%>
										<span>${file.fname }(${file.fsize/1000 } kb)</span>
										</a>
										<span class="umode">
											<a  href="#">
												<i class="fas fa-trash-alt" 
													 data-url="${contextPath }/board/file/${file.fid}"></i>
											</a>
										</span>
									</p>
								</c:forEach>
							</div>
							</c:if>
							<c:if test="${empty files }"> 
							<div>
								<p>첨부파일 없음</p>
							</div>
							</c:if>
						</li>
					</ul>				
				</form>
			</div>
			<!-- 댓글 -->
			<%@include file="/WEB-INF/views/board/rereply.jsp"%>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="popUp" data-bs-backdrop="static" data-bs-keyboard="false" 
				 tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered modal-sm">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">
		          <span class="badge bg-danger"></span> 
		        </h5>
		        <!-- <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> -->
		      </div>
		      <div class="modal-body"></div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary btn-sm" 
		        				data-bs-dismiss="modal">취소</button>
		        <button type="button" class="btn btn-primary btn-sm" 
		        				data-bs-dismiss="modal" id="deleteBtn" 
		        				data-bnum="${boardVO.bnum }">삭제</button>
		      </div>
		    </div>
		  </div>
		</div>	
	</main>
  <!--footer-->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>	
</body>
</html>