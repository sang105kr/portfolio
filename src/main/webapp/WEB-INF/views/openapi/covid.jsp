<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/common.jsp"%>
	<title>코비드 현황</title>
  <link rel="stylesheet" href="${contextPath}/css/layout.css">	
  <script src="${contextPath}/js/common/ajax.js"></script>
  <script defer src="${contextPath}/js/covid.js"></script>
  <style>
  .container-m section.s1{
  	background-color: transparent;
  }
  .container-m section.s1 .wrapper-search{
  	display:grid;
  	grid-template-columns: repeat(5,minmax(10%,auto));
  	justify-items:end;
  }
/*   .container-m section.s1 .wrapper-search #searchBtn{
  	padding: 0px 5px;
  } */
  </style>
</head>
<body>
  <!--upper most-->
	<%@ include file="/WEB-INF/views/include/uppermost.jsp"%>
  <!--nav-->
	<%@ include file="/WEB-INF/views/include/menu.jsp"%>
  <!--main-->
	<main>
  <div class="table-responsive-sm">
    <div class="container container-m">
      <section class="s1">
    <h3>코비드 현황</h3>
    <div class="wrapper-search">  
	    <label for="startDt">시작일</label>
	    <input type="date" name="startDt" id="startDt"/>     
	    <label for="endDt">종료일</label>
	    <input type="date" name="endDt" id="endDt"/>
	    <button class="btn btn-warning" id="searchBtn">조회</button>
    </div>     
    <table class="table">
      <caption>List of users</caption>
      <thead>
        <tr>
          <th scope="col">#</th>          
          <th scope="col">기준일</th>
          <th scope="col">확진자 수:</th>
          <th scope="col">격리해제 수</th>
          <th scope="col">검사진행 수</th>
          <th scope="col">사망자 수</th>
          <th scope="col">치료중 환자수</th>
          <th scope="col">결과음성 수</th>
          <th scope="col">누적 검사 수</th>
          <th scope="col">누적 검사 완료 수</th>
          <th scope="col">누적확진율</th>
        </tr>
      </thead>
      <tbody id="result">
        <!-- <tr>
          <th scope="row">1</th>
          <td>Mark</td>
          <td>Otto</td>
          <td>@mdo</td>
          <td>Mark</td>
          <td>Otto</td>
          <td>@mdo</td>
          <td>Mark</td>
          <td>Otto</td>
          <td>@mdo</td>
          <td>@mdo</td>
        </tr> -->
      </tbody>      
    </table>
    </section>
    </div>
  </div>
  </main>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>