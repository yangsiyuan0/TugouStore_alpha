<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="${pageContext.request.contextPath}/text/javascript" src="js/checkForm.js"></script>
		<!-- 引入form表单校验的插件bootstrapvalidator -->
		<script src="${pageContext.request.contextPath}/dist/js/bootstrapValidator.min.js"></script> 
		<link href="${pageContext.request.contextPath}/dist/css/bootstrapValidator.min.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
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
    color: #666;
    font-size: 22px;
    font-weight: normal;
    padding-right:17px;
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

	<%@ include file="navi.jsp" %>
	
	
	<div class="container"  style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat;">
	<div class="row"> 
		<div class="col-md-7">
			<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
		</div>
		
		<div class="col-md-5">
					<div style="width:440px;border:1px solid #E7E7E7;padding:20px 0 20px 30px;border-radius:5px;margin-top:60px;background:#fff;">
					<font>会员登录</font>USER LOGIN
					<small style="color: red">&emsp;${msg}</small>
					<div>&nbsp;</div>
	<form class="form-horizontal" action="${pageContext.request.contextPath}/UserServlet"   method="post" name="send" id="formSubmit">
	  <!-- 隐藏的表单项，用于触发注册 -->
	  <input type="hidden" name="method" value="login">
	 <div class="form-group">
	    <label for="username" class="col-sm-2 control-label">用户名</label>
	    <div class="col-sm-8">
	    <div class="input-group">
	      <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" value="${cookie.username.value }">
	      <div class="input-group-addon">
            <span class="glyphicon glyphicon-user"></span>
           </div>
	    </div>
	    </div>
	  </div>
	   <div class="form-group">
	    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
	    <div class="col-sm-8">
	    	<div class="input-group">
	      <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
	      <div class="input-group-addon">
            <span class="glyphicon glyphicon-lock"></span>
           </div>
	    </div>
	    </div>
	  </div>
	   <div class="form-group">
	        <label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
	    <div class="col-sm-5">
	      <input type="text" class="form-control" id="verifycode"  name="verifycode" placeholder="请输入验证码">
	    </div>
	    <div class="col-sm-5">
	      <img src="${pageContext.request.contextPath}/CheckImgServlet"  alt="验证码" id="verifyImg" onclick = "changeImg()"/>
	      <small style="color: red" id="verifymsg"></small>
	    </div>
	    
	  </div>
	   <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <div class="checkbox">
	        <label>
	          <input type="checkbox" name="autoLogin" value="true"> 自动登录
	        </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <label>
	          <input type="checkbox" name="remember" value="true"> 记住用户名
	        </label>
	      </div>
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	    <input type="submit"  id = "regButton" width="100" value="登录" 
	    style="background: url('${pageContext.request.contextPath}/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
	    height:35px;width:100px;color:white;">
	    </div>
	  </div>
	</form>
	</div>			
		</div>
	</div>
	</div>	
<!-- 页脚栏 -->
<div class="container-fluid" style="margin-top: 80px">
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>