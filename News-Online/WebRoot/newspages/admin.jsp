<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="console_element/top.jsp" />
<script language="javascript">
	function clickdel() {
		return confirm("删除请点击确认");
	}
</script>


<div id="main">
	<jsp:include page="console_element/left.jsp" />
	<div id="opt_area">
		<ul class="classlist">
		
			<!-- 遍历   top list -->
			<c:forEach var="topic" items="${requestScope.topicList}">
			<li class='space'><strong>${topic.tname}</strong></li>
				<c:forEach var="news" items="${topic.newsList}">
				<li>${news.ntitle}
				<span> 作者：${news.nauthor}&#160;&#160;&#160;&#160; 
				<a  href='${pageContext.request.contextPath}/AdminServlet?action=toNewsUpdate&nid=${news.nid}'>修改</a> &#160;&#160;&#160;&#160;
				 <a href='${pageContext.request.contextPath}/AdminServlet?action=doNewsDelete&nid=${news.nid}'
					onclick='return clickdel()'>删除</a>
				</span>
				</li>
				</c:forEach>
			</c:forEach>
			<!-- 遍历   news list -->

			<!-- 分页处理 -->
			<p align="right">
				当前页数:[${page.pageNo}/${page.allPages} ]&nbsp;&nbsp; 
				<a href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&pageNo=1">首页</a><a
					href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&pageNo=${page.prev}">&nbsp;&nbsp;上一页</a><a
					href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&pageNo=${page.next}">&nbsp;&nbsp;下一页</a> <a
					href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&pageNo=${page.allPages}">&nbsp;&nbsp;末页</a>
			</p>
		</ul>
	</div>
</div>

<jsp:include page="console_element/bottom.html" />
