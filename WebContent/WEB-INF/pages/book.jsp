<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/commons/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"  src="script1/jquery-1.7.2.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
<center>		
		<br><br>
		Title: ${book.title }
		<br><br>
		Author: ${book.author }
		<br><br>
		Price: ${book.price }
		<br><br>
		PublishingDate: ${book.publishingDate }
		<br><br>
		Remark: ${book.remark }
		<br><br>
		
<!-- 这里要继续保持uri中的minPrice和maxPrice的数量。 -->
		<a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
	</center>
   


</body>
</html>