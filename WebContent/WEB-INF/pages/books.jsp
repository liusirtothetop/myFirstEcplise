<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/commons/common.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"  src="script1/jquery-1.7.2.js"></script>
<script type="text/javascript">
 //记住路径中script是放在外面的，细节，，还有 el中的对象参数，是第一个字母小写。 
  window.onload=function(){
	 //1.val()的是表单里面的值
	 //2.parseInt("${bookpage.totalPageNumber}")可以这样解析
	 //3.var href="bookServlet?method=getBooks&pageNo="+pageNo+ "&"+$(":hidden").serialize();
	 //  window.location.href=href;用来保存参数信息url;
	 //4. $(":hidden").serialize()来取得隐藏于中的序列的值。
	  $(function(){
		  $("#pageNo").change(function(){
			  var val=$(this).val();
			  //去后面的空格操作。
			  val =$.trim(val);
			  var reg=/^\d+$/g;
			  
			  var flag=false;
			  var pageNo=0;
			  if(reg.test(val))
			  {
				  pageNo=parseInt(val);
				  if(pageNo>=1 && pageNo <=parseInt("${bookpage.totalPageNumber}"))
				  {
					  flag=true;
				  }
			  }
			  if(!flag)
			 {
			   alert("输入的页数不合法");   
			   $(this).val("");
			   return ;
		     }
			  var href="bookServlet?method=getBooks&pageNo="+pageNo+ "&"+$(":hidden").serialize();
			  window.location.href=href;
		  })
	  })
  }
</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
  
   <c:if test="${ param.title != null }">
     您已将 ${param.title }加入到了购物车中
   </c:if>  
   <br>
   <!-- 这里需要转发到cart.jsp -->
   <c:if test="${!empty sessionScope.ShoppingCart.books}">
     购物车中有${sessionScope.ShoppingCart.bookNumber}本书，<a href="bookServlet?method=toForwordPage&page=cart&pageNo=${bookpage.pageNo }&id=${book.id}"> 查看购物车</a>
   </c:if>
     
  <form action="bookServlet?method=getBooks" method="post">
			Price: 
			<input type="text" size="1" name="minPrice"/> - 
			<input type="text" size="1" name="maxPrice"/>
			
			<input type="submit" value="Submit"/>
		</form>
		
	<table cellpadding="10">
		<c:forEach items="${bookpage.list }" var="book">
				<tr>
					<td>
						<a href="bookServlet?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id}">${book.title }</a>
						<br>
						${book.author }
					</td>
					<td>${book.price }</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${bookpage.pageNo }&id=${book.id}&title=${book.title}">加入购物车</a></td>
				</tr>
			</c:forEach>
	</table>		

		<br><br>
  
  		<br><br>
		共 ${bookpage.totalPageNumber } 页
		&nbsp;&nbsp;
		当前第 ${bookpage.pageNo } 页		
		&nbsp;&nbsp;
  

<c:if test="${bookpage.hasPrev}">
<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
</c:if>
<c:if test="${bookpage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">末页</a>
</c:if>
 转到 <input type="text" size="1" id="pageNo"/> 页	


 


</body>
</html>