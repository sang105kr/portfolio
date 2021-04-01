<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />     
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!--font awesome-->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"/>      
  <!--폰트-->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nanum+Gothic+Coding:wght@400;700&family=Nanum+Myeongjo:wght@400;700&family=Noto+Sans+KR:wght@100;400;900&display=swap"/>  
  <!-- bootstrap -->
  <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/5.0.0-beta2/css/bootstrap.css" />
  <!-- css reset -->
  <link rel="stylesheet" href="${contextPath}/css/reset.css" /> 
  
  <!-- Option 2: Separate Popper and Bootstrap JS -->
  <script defer src="${contextPath }/webjars/popper.js/2.5.4/umd/popper.js"></script>
  <script defer src="${contextPath }/webjars/bootstrap/5.0.0-beta2/js/bootstrap.js"></script>
    