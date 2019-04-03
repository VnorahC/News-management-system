<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="console_element/top.jsp" />

<script type="text/javascript">
	function check(){
		var tname = document.getElementById("tname");

		if(tname.value == ""){
			alert("请输入主题名称！！");
			tname.focus();
			return false;
		}		
		return true;
	}
</script>

<div id="main">
  <jsp:include page="console_element/left.jsp" />
  <div id="opt_area">
    <h1 id="opt_type"> 添加主题： </h1>
    <form action="${pageContext.request.contextPath}/AdminServlet?action=addTopic" method="post" onsubmit="return check()">
      <p>
        <label> 主题名称 </label>
        <input id="tname" name="tname" type="text" class="opt_input" value="" />
      </p>
      <input  name="action" type="hidden" value="addtopic">
      <input type="submit" value="提交" class="opt_sub" />
      <input type="reset" value="重置" class="opt_sub" />
    </form>
  </div>
</div>
<jsp:include page="console_element/bottom.html" />
