<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="console_element/top.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function check() {
		var ntitle = document.getElementById("ntitle");
		var nauthor = document.getElementById("nauthor");
		var nsummary = document.getElementById("nsummary");
		var ncontent = document.getElementById("ncontent");

		if (ntitle.value == "") {
			alert("标题不能为空！！");
			ntitle.focus();
			return false;
		} else if (nauthor.value == "") {
			alert("作者不能为空！！");
			nauthor.focus();
			return false;
		} else if (nsummary.value == "") {
			alert("摘要不能为空！！");
			nsummary.focus();
			return false;
		} else if (ncontent.value == "") {
			alert("内容不能为空！！");
			ncontent.focus();
			return false;
		}
		return true;
	}
</script>


<div id="main">
	<jsp:include page="console_element/left.jsp" />
	<div id="opt_area">
		<h1 id="opt_type">添加新闻：</h1>
		<form action="${pageContext.request.contextPath}/AdminServlet?action=doUpdateSubmit" method="post"
			enctype="multipart/form-data" onsubmit="return check()">
			<p>
				<label> 主题 </label> 
				<select name="ntid">
					 <!-- 迭代输出主题 -->
						<!-- 当前项判断 -->
						<c:forEach var="topic" items="${topicList}">
							<option value="${topic.tid}"<c:if test="${news.ntid==topic.tid}">selected</c:if>>${topic.tname}</option>
						</c:forEach>
						<!-- 当前项判断 -->
					 <!-- 迭代输出主题 -->
				</select> 
				<input type="hidden" name="nid" value="${news.nid}" />
			</p>
			<p>
				<label> 标题 </label> <input name="ntitle" type="text"
					class="opt_input" value="${news.ntitle}" />
			</p>
			<p>
				<label> 作者 </label> <input name="nauthor" type="text"
					class="opt_input" value="${news.nauthor}" />
			</p>
			<p>
				<label> 摘要 </label>
				<textarea name="nsummary" cols="40" rows="3">${news.nsummary}</textarea>
			</p>
			<p>
				<label> 内容 </label>
				<textarea name="ncontent" cols="70" rows="10">${news.ncontent}</textarea>
			</p>
			<p>
				<input type="hidden" name="npicpath" value="${news.npicpath}"/>
				<label> 上传图片 </label> <input name="file" type="file" class="opt_input" />
			</p>
			<input type="submit" value="提交" class="opt_sub" /> <input
				type="reset" value="重置" class="opt_sub" />
		</form>
		<h1 id="opt_type">修改新闻评论：</h1>
		<table width="80%" align="left" id="commentTable">
			
			<!-- 判断： 无评论 -->
			<c:if test="${fn:length(commentList)==0}">
				<td colspan="6">暂无评论！</td>
				<tr>
					<td colspan="6"><hr /></td>
				</tr>
			</c:if>
			<!-- 判断： 无评论 -->
			
			<!-- 判断： 有评论 -->
			<c:if test="${fn:length(commentList)!=0}">
			<c:forEach var="comment" items="${commentList}">
				<!-- 循环输出评论 -->
					<tr>
						<td>留言人：</td>
						<td>${comment.cauthor}</td>
						<td>IP：</td>
						<td>${comment.cip}</td>
						<td>留言时间：</td>
						<td>${comment.cdate}</td>
						<td>
							<a href="javascript:deleteComment(${comment.cid},${comment.cnid})">删除</a>
						</td>
					</tr>
					<tr>
						<td colspan="6">${comment.ccontent}</td>
					</tr>
					<tr>
						<td colspan="6"><hr /></td>
					</tr>
				</c:forEach>
				<!-- 循环输出评论 -->
		   <!-- 判断： 有评论 -->
		   	</c:if>
		</table>
	</div>
</div>
<script type="text/javascript">
function deleteComment(commentId,newsId){
	var url="${pageContext.request.contextPath}/AdminServlet?action=doDelComment";
	var params={
			cid:commentId,
			nid:newsId
	};
	$.getJSON(url,params,function(result){
		if(result.msg==0){
			alert("删除失败！");
			return;
		}
		if(result.datas.length==0){
			var htmlString = $("<tr><td colspan='6'>暂无评论！</td></tr><tr><td colspan='6'><hr /></td></tr>");
			$("#commentTable").append($(htmlString));
			return;
		}
		$("#commentTable").empty();
		$.each(result.datas,function(i,comment){
				var htmlString
				=$("<tr><td>留言人：</td><td>"+comment.cauthor+"</td>"+
				"<td>IP：</td><td>"+comment.cip+"</td><td>留言时间：</td>"+
				"<td>"+comment.cdate+"</td>"+
				"<td><a href='javascript:deleteComment("+comment.cid+","+comment.cnid+")'>删除</a></td>"+
				"</tr><tr><td colspan='6'>"+comment.ccontent+"</td></tr>"+
				"<tr><td colspan='6'><hr /></td></tr>");
				
				$("#commentTable").append($(htmlString));
			
		});
	});
}

</script>

<jsp:include page="console_element/bottom.html" />
