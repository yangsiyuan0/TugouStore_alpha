<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<meta charset="utf-8" />
		<title>商城主页</title>
	</head>
	<body>
	<!-- 一. 导航栏 -->
		<%@ include file="navi.jsp" %>
		
		<!-- 二 . 图片轮播栏:使用BootStrap的JS轮播插件-->
		<!-- 100%宽度 响应式设计 -->
		<div class="row container-fluid ">
			<div id="carousel-example-generic" class="carousel slide">
			  <!-- Indicators -->
			  <ol class="carousel-indicators">
			    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
			    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
			    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
			  </ol>
			
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner">
			    <div class="item active">
			      <img src="${pageContext.request.contextPath}/img/1.jpg">
			      <div class="carousel-caption">
			      	<!-- 图片说明文字-->
			      	<h3>imac</h3>
   					 <p>big than big,born for big</p>
			      </div>
			    </div>
				<div class="item">
			      <img src="${pageContext.request.contextPath}/img/2.jpg">
			      <div class="carousel-caption">
			      	<!-- 图片说明文字-->
			      	<h3>imac</h3>
   					 <p>big than big,born for big</p>
			      </div>
			    </div>
			    <div  class="item">
			      <img src="${pageContext.request.contextPath}/img/3.jpg">
			      <div class="carousel-caption">
			      	<!-- 图片说明文字-->
			      	<h3>imac</h3>
   					 <p>big than big,born for big</p>
			      </div>
			    </div>
			  </div>
			
			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left"></span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right"></span>
			  </a>
			</div>
		</div>
		<!-- 图片轮播部分结束 -->
		
		<!-- 三. 热门商品栏：利用BootStrap的栅格-->
	<div class="container-fluid">
		<div class="col-md-12">
			<h4>热门商品&emsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h4>
		</div>
		<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
			<img src="${pageContext.request.contextPath}/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
		</div>
			<div class="col-md-10">
				<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
					<a href="javaScript:void(0)">
						<img src="${pageContext.request.contextPath}/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
					</a>
				</div>
			
				<c:forEach items="${ hotList }" var="product">
					<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${ pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${product.pid}">
							<img src="${pageContext.request.contextPath}/${product.pimage}" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="${ pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${product.pid}" style='color:#666'>${fn:substring(product.pname,0,10)}</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen;${product.shop_price}</font></p>
					</div>
				</c:forEach>
			</div>
		</div>
		<!--
           	作者：ci2713@163.com
           	时间：2015-12-30
           	描述：广告部分
           -->
           <div class="container-fluid">
			<img src="${pageContext.request.contextPath}/products/hao/ad.jpg" width="100%"/>
		</div>
		<!--
           	作者：ci2713@163.com
           	时间：2015-12-30
           	描述：商品显示
           -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h4>最新商品&emsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h4>
			</div>
			<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
				<img src="${pageContext.request.contextPath}/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
			</div>
			<div class="col-md-10">
				<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
					<a href="javaScript:void(0)">
						<img src="${pageContext.request.contextPath}/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
					</a>
				</div>
			
			<c:forEach items="${ newList }" var="product">
				<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
					<a href="${ pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${product.pid}">
						<img src="${pageContext.request.contextPath}/${product.pimage}" width="130" height="130" style="display: inline-block;">
					</a>
					<p><a href="${ pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${product.pid}" style='color:#666'>${fn:substring(product.pname,0,10)}</a></p>
					<p><font color="#E4393C" style="font-size:16px">&yen;${product.shop_price}</font></p>
				</div>
			</c:forEach>
			</div>
		</div>
		
		<!-- 引入页脚  -->
		<%@ include file="footer.jsp" %>

	</body>
</html>
