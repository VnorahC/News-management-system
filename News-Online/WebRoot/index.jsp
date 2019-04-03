<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="index-elements/index_top.jsp"></jsp:include>
<div id="container">
	<jsp:include page="index-elements/index_sidebar.jsp" />
	<div class="main">
		<div class="class_type">
			<img src="Images/class_type.gif" alt="新闻中心" />
		</div>
		<div class="content">
			<ul class="class_date">
				<!--遍历 显示topic list -->
				
				<li id='class_month'>
				<c:forEach var="topic" items="${requestScope.topicList}">
				<a href="${pageContext.request.contextPath}/NewsServlet?action=listNews&var=true&tid=${topic.tid }"><b>${topic.tname}</b> </a>
				</c:forEach> <!--遍历 显示topic list -->
				
			</ul>
			<ul class="classlist"> 	
				<!-- 遍历显示 news list  -->
				<c:forEach var="news" items="${requestScope.newslist }">
					<li><a href="${pageContext.request.contextPath}/NewsServlet?action=detail&nid=${news.nid}">${news.ntitle} </a> <span>${news.ncreatedate}</span></li>
				</c:forEach>
				
				<!-- 遍历显示 news list  -->

				<!-- 分页显示 -->
				<p align="right">
					当前页数:[${pageNo}/${allPages }  ]&nbsp;&nbsp; <a
						href="${pageContext.request.contextPath}/NewsServlet?action=listNews&var=true&pageNo=1">首页</a><a
						href="${pageContext.request.contextPath}/NewsServlet?action=listNews&var=true&pageNo=${prev}">&nbsp;&nbsp;上一页</a><a
						href="${pageContext.request.contextPath}/NewsServlet?action=listNews&var=true&pageNo=${next}">&nbsp;&nbsp;下一页</a> <a
						href="${pageContext.request.contextPath}/NewsServlet?action=listNews&var=true&pageNo=${allPages}">&nbsp;&nbsp;末页</a>
				</p>

			</ul>
		</div>
		<jsp:include page="index-elements/index_rightbar.html" />
	</div>
</div>

<jsp:include page="index-elements/index_bottom.html" />
