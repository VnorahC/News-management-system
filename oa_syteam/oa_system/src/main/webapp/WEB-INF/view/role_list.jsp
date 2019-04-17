<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>角色管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray"> <span class="l"> <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a class="btn btn-primary radius" href="javascript:;" onclick="admin_role_add()"><i class="Hui-iconfont">&#xe600;</i> 添加角色</a> </span> <span class="r">共有数据：<strong id="total"></strong> 条</span> </div>
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="6">角色管理</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" value="" name=""></th>
				<th width="40">ID</th>
				<th width="200">角色名</th>
				<th width="300">描述</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody id="roleTbody">
			
		</tbody>
	</table>
	<div style="margin-top: 15px" id="rolePage"></div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${ctx}/lib/laypage/1.2/laypage.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">



//整理分页的数据
//users ：用户数据，是一个数组
function showData(roles){
	
	var html = "";
	
	for(var i = 0;i<roles.length;i++){
		//分别取出没一行数据
		var role = roles[i];
		html +="<tr class='text-c'>";
		html +="<td><input type='checkbox' value='"+role.roleId+"' name='check'></td>";
		html +="<td>"+role.roleId+"</td>";
		html +="<td>"+role.name+"</td>";
		html +="<td>"+role.remark+"</td>";
		html +="<td class='f-14'>";
		html +="<a title='编辑' href='javascript:;' onclick='admin_role_edit("+role.roleId+")' style='text-decoration:none'><i class='Hui-iconfont'>&#xe6df;</i></a>";
		html +="<a title='删除' href='javascript:;' onclick='admin_role_del(this,"+role.roleId+")' class='ml-5' style='text-decoration:none'><i class='Hui-iconfont'>&#xe6e2;</i></a>";
		html +="</td>";
		html +="</tr>";
		
	}
	//添加表格中
	$("#roleTbody").html(html);
}
	
	
//异步分页
//url :数据请求地址
//curr :当前页
function menulists(url,curr){
  var pageNum = curr || 1; //向服务端传的参数
  var keyword = $("")
  $.ajax({
		method:"get",
		url:url,
		data:{pageNum:pageNum},
		success:function(data){
			//将数据整理添加的表格中
			showData(data.list);
			//设置总数
			$("#total").text(data.total);
			
			console.log(data.list);
			//显示分页
	        laypage({
	          cont: 'rolePage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
	          pages: data.pages, //通过后台拿到的总页数
	          skip: true, //是否开启跳页
	          skin: '#05D400',
	          curr: data.pageNum || 1, //当前页
	          jump: function(obj, first){ //触发分页后的回调
	        	 //console.log(obj,first);
	            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
	            	menulists("${ctx}/role/list.do",obj.curr);
	            }
	          }
			})
		}
	})
}

//在文档加载完毕以后在执行对应代码
$(function(){
	//初始化 执行分页操作
	menulists("${ctx}/role/list.do",1);
	
});






/*管理员-角色-添加*/
function admin_role_add(){
	layer_show("添加角色","${ctx}/role/add.do","600","325");
}
/*管理员-角色-编辑*/
function admin_role_edit(roleId){
	layer_show("修改角色","${ctx}/role/edit.do?roleId="+roleId,"800","500");
}

function datadel(){
	var ids =[]; 
    $('input[type="checkbox"]:checked').each(function(){ 
    	if(this.value!=""){
    		ids.push(this.value); //push 进数组
    	}
    });
    layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'get',
			url: '${ctx}/role/deleteAll.do',
			dataType: 'json',
			data:{
				ids:ids
			},
			success: function(data){
				window.location.reload();
				layer.msg('已删除!',{icon:1,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

/*管理员-角色-删除*/
function admin_role_del(obj,id){
	layer.confirm('角色删除须谨慎，确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '${ctx}/role/delete.do?id='+id,
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").remove();
				layer.msg('已删除!',{icon:1,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}
</script>
</body>
</html>