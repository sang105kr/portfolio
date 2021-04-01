<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--common -->
<%@ include file="/WEB-INF/views/include/common.jsp"%>      
<title>게시글 목록</title>
<link rel="stylesheet" href="${contextPath }/css/layout.css">
<link rel="stylesheet" href="${contextPath }/css/board/list.css" />
<script defer src="${contextPath }/js/board/list.js"></script>
</head>
<body>
  <!--upper most-->
	<%@ include file="/WEB-INF/views/include/uppermost.jsp"%>
  <!--nav-->
	<%@ include file="/WEB-INF/views/include/menu.jsp"%>
	<main>
		<div class="container">
			<div class="content">
				<div class="table-responsive">
				  <table class="table table-sm table-striped table-hover 
				                table-bordered 
				                caption-top ">
					  <caption class="text-center h4">게시글 목록</caption>
					  <thead class="table-dark">
					    <tr>
					      <th scope="col">번호</th>
					      <th scope="col">분류</th>
					      <th scope="col">제목</th>
					      <th scope="col">작성자</th>
					      <th scope="col">작성일</th>
					      <th scope="col">조회수</th>
					    </tr>
					  </thead>
					  <tbody class="table-danger">
					  	<c:forEach var="rec" items="${boardList }">
					    <tr>
					      <td scope="col">${rec.bnum }</td>
					      <td scope="col">${rec.boardCategoryVO.cname }</td>
					      <td scope="col">
					      	<c:forEach begin="1" end="${rec.bindent }">&nbsp;&nbsp;</c:forEach>
					      	<c:if test="${rec.bindent > 0 }"><i class="far fa-comment-dots"></i></c:if>
					      	<a href="${contextPath }/board/view/${rec.bnum}">${rec.btitle }</a>
					      </td>
					      <td scope="col">${rec.bnickname }</td>
					      <td scope="col">
					      	<fmt:formatDate value="${rec.bcdate }" pattern="yy.MM.dd"/>
					      </td>
					      <td scope="col">${rec.bhit }</td>
					    </tr>
					    </c:forEach>
					  </tbody>
				  </table>
				  <!-- 버튼 -->
				  <div class="text-end">
				  	<button id="writeBtn" type="button" class="btn btn-warning btn-sm">글쓰기</button>
				  </div>
				  <!-- 페이징 -->
				  <div>
					  <ul class="pagination pagination-sm justify-content-center" >
					  	<!-- 보여줄 이전페이지가 있는 경우만 보이게  -->
					  	<c:if test="${pc.prev }">
					    <!-- 첫 페이지 -->
					    <li class="page-item">
					    	<a class="page-link" href="${contextPath }/board/list/1/">처음</a>
					    </li>	
					    <!-- 이전 페이지 -->		
					    <li class="page-item">
					    	<a class="page-link" href="${contextPath }/board/list/${pc.startPage-1}">이전</a>
					    </li>	
					    </c:if>
					  
					    <c:forEach var="pageNum" begin="${pc.startPage }" end="${pc.endPage }">
					    <!-- 현재페이지와 요청페이지가 같은경우 -->
					    <c:if test="${pageNum eq pc.rc.reqPage}">
 					    <li class="page-item active" aria-current="page">
								<a class="page-link" href="${contextPath }/board/list/${pageNum}">${pageNum }</a>
					    </li>
					    </c:if>
					    
					    <!-- 현재페이지와 요청페이지가 다른경우 -->
					    <c:if test="${pageNum ne pc.rc.reqPage}">
					    <li class="page-item">
					    	<a class="page-link" href="${contextPath }/board/list/${pageNum}">${pageNum }</a>
					    </li>
					    </c:if>			    
					    </c:forEach>
					    <!-- 보여줄 다음페이지가 있는 경우만 보이게 -->
					    <c:if test="${pc.next }">
					    <!-- 다음페이지 -->
					    <li class="page-item">
					    	<a class="page-link" href="${contextPath }/board/list/${pc.endPage + 1}">다음</a>
					    </li>	
					    <!-- 최종페이지 -->		
					    <li class="page-item">
					    	<a class="page-link" href="${contextPath }/board/list/${pc.finalEndPage}">최종</a>
					    </li>	
					    </c:if>
					  </ul>
					</div>
					<!-- // 페이징 -->
					<!-- 검색 -->
					<div class="find">
						<form action="#">
							<select name="searchType" id="searchType">
								<option value="TC">제목+내용</option>
								<option value="T">제목</option>
								<option value="C">내용</option>
								<option value="I">아이디</option>
								<option value="N">별칭</option>
								<option value="A">전체</option>
							</select>
							<input type="text" name="keyword" id="keyword" />
							<button id="findBtn" type="button" class="btn btn-outline-secondary btn-sm">검색</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</main>
  <!--footer-->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>	
</body>
</html>



