<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="${pageContext.request.contextPath}/text/javascript" src="js/checkForm.js"></script>
		<!-- 引入form表单校验的插件bootstrapvalidator -->
		<script src="${pageContext.request.contextPath}/dist/js/bootstrapValidator.min.js"></script> 
		<link href="${pageContext.request.contextPath}/dist/css/bootstrapValidator.min.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/register.js"></script>
		<!-- 引入BootStrap日期插件 -->
		<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script> 
		<link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
 <script type="text/javascript">

/* 验证码刷新 */
function changeImg(){
	document.getElementById("verifyImg").src="${pageContext.request.contextPath}/CheckImgServlet?time="+new Date().getTime();
} 
</script>
</head>
<body>
<!-- 令牌机制 -->
<%
	String token = UUID.randomUUID().toString();
	session.setAttribute("token", token);
%>
<!-- 引入导航栏 -->
<%@ include file="navi.jsp" %>	

<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">
<div class="row"> 
	<div class="col-md-2"></div>
	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER  
		<small id = "usermsg"></small>
		<form class="form-horizontal" style="margin-top:5px;" action="${pageContext.request.contextPath}/UserServlet"  method="post" id="formSubmit">
			<!-- 隐藏的表单项，用于触发注册 -->
			<input type="hidden" name="method" value="regist">
			<!-- 隐藏的表单项，用于实现令牌机制 -->
			<input type="hidden" name="token" value="${sessionScope.token}"/>
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			    	<div class="input-group">
			      		<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
		       	  	<div class="input-group-addon">
	               		<span class="glyphicon glyphicon-user"></span>
	               </div>
			    </div>
			  </div>
			 </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			   		 <div class="input-group">
			      <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                   	<div class="input-group-addon">
               			<span class="glyphicon glyphicon-lock"></span>
              		 </div>
            	 </div>
		    	</div>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			    	<div class="input-group">
			      <input type="password" class="form-control" id="repassword" name = "repassword" placeholder="请输入确认密码">
			      <div class="input-group-addon">
               			<span class="glyphicon glyphicon-lock"></span>
              		 </div>
            	 </div>
			    </div>
			  </div>
			  
			  
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			    	<div class="input-group">
			      <input type="email" class="form-control" id="email" name = "email" placeholder="Email">
			      <div class="input-group-addon">
               <span class="glyphicon glyphicon-envelope"></span>
               </div>
             </div>
			    </div>
			  </div>
			  
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			    <div class="input-group">
			      <input type="text" class="form-control" id="name" name="name"  placeholder="请输入姓名">
			      <div class="input-group-addon">
	               		<span class="glyphicon glyphicon-user"></span>
	               </div>
			    </div>
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">电话号码</label>
			    <div class="col-sm-6">
			    <div class="input-group">
			      <input type="text" class="form-control" id="telephone" name="telephone"  placeholder="请输入号码">
			      <div class="input-group-addon">
	               		<span class="glyphicon glyphicon-earphone"></span>
	               </div>
			    </div>
			    </div>
			  </div>
			  
			  <div class="form-group opt">  
			  <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
			  <div class="col-sm-6">
			    <label class="radio-inline">
			  <input type="radio"  id="sex" name="sex" value="male"> 男
			</label>
			<label class="radio-inline">
			  <input type="radio"  id="sex" name="sex" value="female"> 女
			</label>
			</div>
			  </div>	
			  
			  	
			 <!--  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			    <div class="input-group">
			      <input type="date" class="form-control" id="birthday" name="birthday" >	
			      <div class="input-group-addon">
	               		<span class="glyphicon glyphicon-calendar"></span>
	               </div>
			    </div>	      
			    </div>
			  </div> -->
			  
			  
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			    <div class="input-group date form_datetime">
			      <input type="text" class="form-control" id="birthday" name="birthday" readonly>	
			      <div class="input-group-addon">
	               		<span class="glyphicon glyphicon-calendar"></span>
	               </div>
			    </div>	      
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			      <input type="text" class="form-control" id="verifycode" name="verifycode" >
			    </div>
			    <div class="col-sm-4">
			    <img src="${pageContext.request.contextPath}/CheckImgServlet" alt="验证码" id="verifyImg" onclick = "changeImg()"/>
			    <small style="color: red" id="verifymsg"></small>
			    </div>
			  </div>
			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  id = "regButton" width="100" value="注册"  border="0"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
				  <input type="reset"  width="100" value="清空"  border="0"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white; margin-left: 120px">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	  
	
	<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>
</html>
