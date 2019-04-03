<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>新闻发布系统管理后台</title>
<link href="${pageContext.request.contextPath}/CSS/admin.css" rel="stylesheet" type="text/css" />
<div id="header">
  <div id="welcome">欢迎使用新闻管理系统！</div>
  <div id="nav">
    <div id="logo"><img src="${pageContext.request.contextPath}/Images/logo.jpg" alt="新闻中国" /></div>
    <div id="a_b01"><img src="${pageContext.request.contextPath}/Images/a_b01.gif" alt="" /></div>
  </div>
</div>
<div id="admin_bar">
  <div id="status">管理员:${sessionScope.loginUser.uname}&#160;&#160;&#160;&#160; <a href="${pageContext.request.contextPath}/UserServlet?action=logout">退出</a></div>
  <div id="channel">
  <c:if test="${requestScope.row>0}"><h4 style="color:red;">添加成功！</h4></c:if>
   <c:if test="${requestScope.addError==0}"><h4 style="color:red;">标题不能重复！</h4></c:if>
  </div>
</div>
</head>
<body>
