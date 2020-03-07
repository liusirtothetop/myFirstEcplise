<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 是通过include 来进行插入到页面中，且相对应的页面包含此部分的内容 -->
<script type="text/javascript">
	
	$(function(){
		$("a").each(function(){
			this.onclick = function(){
				var serializeVal = $(":hidden").serialize();
				var href = this.href + "&" + serializeVal;
				window.location.href = href;			
				return false;
			};
		});
	});	
	
</script>

<input type="hidden" name="minPrice" value="${param.minPrice }"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>