<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" href="${ctx}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->

<title>新建网站角色 - 管理员管理 - H-ui.admin v3.1</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="roleForm">
		<!-- 角色id -->
		<input type="hidden" value="${role.roleId}" name="roleId">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${role.name}" placeholder="" id="roleName" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea style="text-align: left;" name="remark" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符" datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)">${role.remark}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">系统权限：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<!-- ztree是基于列表的，所以必须有一个基本的ul，并且有id和class -->
				<ul id="permissionTree" class="ztree"></ul>
				
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button  type="button" class="btn btn-success radius" id="admin-role-save" name="admin-role-save"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>


<script type="text/javascript">

	//1.zTree的设置对象，默认可以什么都不写
	var setting = {
			/* 是否支持复选框 */
			check:{enable:true},
			/* data ：ztree的数据的相关配置
				simpleData:是否支持简单格式的数据
			*/
			data:{
				simpleData: {
					enable: true
				}
			},
			/* 异步加载数据 */
			async: {
				enable: true,
				url:"${ctx}/role/getAllPermissions.do"
			},
			/* 异步加载成功以后的设置 */
			callback: {
				onAsyncSuccess: zTreeOnAsyncSuccess
			}
			
	};
	
	//异步加载成功以后回调的函数
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		/*checkbox 回显选中思路
		  1.在此方法中获取出当前角色在权限表中对应的所有的权限id
		  2.循环第一步得到的权限的数组，并根据当前权限id，获取zTree对应的节点
		 	调用zTree的获取指定id节点的方法
		  3.让对象的节点选中即可
		*/
		//角色id
		var rId = ${role.roleId};
		
		$.get("${ctx}/role/getPermissionIdsByRoleId.do",{roleId:rId},function(permissionIds){
			console.log(permissionIds);
			//获取zTree对象
			var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			
			for(var i = 0;i<permissionIds.length;i++){
				
				var permissionId = permissionIds[i];
				
				//获取指定id的节点，此节点其实就是当前角色用户的权限对应的那个节点
				var node = treeObj.getNodeByParam("id",permissionId);
				
				//让节点选中
				//默认有一个父子选中效果，如果父节点选中的话，那么对应的子节点会全部被选中
				//解决方案：让再没有子节点的节点选中即可
				if(node.children == null){
					treeObj.checkNode(node, true, true);
				}
				
			}
		
		});
	};
	
	
	//文档加载完毕以后执行代码
	$(function(){
		
		
		//2.初始化ztree
		$(function(){
			$.fn.zTree.init($("#permissionTree"), setting);
		});	

		//修改角色信息
		$("#admin-role-save").click(function(){
			//序列化表单（自动把表单的所有数据都提取出来）
			var formData = $("#roleForm").serialize();
			
			console.log(formData);
			
			//获取zTree中选中节点对应的所有权限id
			
			//1.获取zTree对象（树对象包含当前所有节点的所有数据）
			var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			
			//2.获取选中的所有节点
			var nodes = treeObj.getCheckedNodes(true);
			
			
			//声明数组，封装权限id
			var permissionIds = [];
						
			//3.循环节点，并获取对应节点的相信信息
			for(var i = 0;i<nodes.length;i++){
				var node = nodes[i];
				
				var permissionId = node.id;
				//var name = node.name;
				//console.log(id,name);
				//将权限id添加到数组中
				permissionIds.push(permissionId);
				
			}
			
			//将权限id拼接到formData中
			formData = formData+"&permissionIds="+permissionIds;
			
			console.log(formData);
			
			//为表单提交按钮绑定js事件
			$.post("${ctx}/role/update.do",formData,function(result){
				if(result.code==1){
					//弹框
					layer.msg(result.message,{icon:1,time:1000});
					//获取当前弹出层索引
					var index = parent.layer.getFrameIndex(window.name);
					//让父层页面重新刷新一下（重新加载一下）
					window.parent.location.reload();
					//关闭当前弹出层
					parent.layer.close(index);
				}
			});
			
		});
		
		
	});
	
	
	
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>