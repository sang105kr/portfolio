<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
  <!--common -->
	<%@ include file="/WEB-INF/views/include/common.jsp"%>  
  <title>Document</title>
  <link rel="stylesheet" href="/portfolio/css/layout.css">
  <link rel="stylesheet" href="/portfolio/css/admin/admin.css">
</head>
<body>
  <!--upper most-->
	<%@ include file="/WEB-INF/views/include/uppermost.jsp"%>
  <!--header-->
<%-- 	<%@ include file="/WEB-INF/views/include/header.jsp"%> --%>
  <!--nav-->
	<%@ include file="/WEB-INF/views/admin/menu.jsp"%>
  <!--main-->
  <main>
    <div class="container container-m">
      <section class="s1">section1</section>
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