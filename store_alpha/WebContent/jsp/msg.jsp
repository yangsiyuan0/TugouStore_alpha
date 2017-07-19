<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>跳转...</title>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${ pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<!-- 导航栏 -->
<%@ include file="navi.jsp" %>

	<div class="container" style="text-align: center; height: 300px; margin-top: 200px">
		<h1>${ msg }</h1>
		<%-- <h3><a href="${ pageContext.request.contextPath }/index.jsp">首页</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=loginUI">登录</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=registUI">注册</a></h3> --%>
	</div>


<!-- 页脚栏 -->
<div class="container-fluid">
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>