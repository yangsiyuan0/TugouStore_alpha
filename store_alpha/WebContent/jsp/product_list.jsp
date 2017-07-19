<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>商品列表</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 引入自定义css文件 style.css -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
	<style>
		body {
			margin-top: 20px;
			margin: 0 auto;
			width: 100%;
		}
		.carousel-inner .item img {
			width: 100%;
			height: 300px;
		}
	</style>
</head>

<body>
<!-- 导航栏  -->
	<%@ include file="navi.jsp" %>
<!-- 商品列表栏 -->
<div class="row" style="width:1210px;margin:0 auto;">
	<c:forEach items="${pageBean.list}" var="product">
		<div class="col-md-2">
			<a href="${pageContext.request.contextPath}/ProductServlet?method=findByPid&pid=${product.pid}&time=<%= new Date().getTime() %>">
				<img src="${pageContext.request.contextPath}/${product.pimage}" width="170" height="170" style="display: inline-block;">
			</a>
			<p><a href="${pageContext.request.contextPath}/ProductServlet?id=${product.pid}" style='color:green'>${fn:substring(product.pname,0,10)}</a></p>
			<p><font color="#FF0000">商城价：&yen;${product.shop_price} </font></p>
		</div>
	</c:forEach>
</div>
<!-- 分页栏 -->
	<div style="width:380px;margin:0 auto;margin-top:50px;">
		<ul class="pagination" style="text-align:center; margin-top:10px;">
		<!-- 上一页 -->
		<c:if test="${ pageBean.currentPage ==1 }">
			<li class='disabled'>
			<a href="javaScript:void(0)" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
			</li>
		</c:if>
		<c:if test="${ pageBean.currentPage !=1 }">
			<li>
			<a href="${ pageContext.request.contextPath }/ProductServlet?method=findPageByCid&currentPage=${pageBean.currentPage-1}&cid=${param.cid}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
			</li>
		</c:if>
			<c:forEach var="i" begin="1" end="${ pageBean.totalPage }">
				<c:choose>
					<c:when test="${ pageBean.currentPage == i }">
						<li class="active"><a href="#">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/ProductServlet?method=findPageByCid&currentPage=${i}&cid=${param.cid}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${ pageBean.currentPage == pageBean.totalPage }">
				<li class='disabled'>
				<a href="javaScript:void(0)" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a>
				</li>
			</c:if>
			<c:if test="${ pageBean.currentPage != pageBean.totalPage }">
				<li>
				<a href="${ pageContext.request.contextPath }/ProductServlet?method=findPageByCid&currentPage=${pageBean.currentPage+1}&cid=${param.cid}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
				</li>
			</c:if>
		</ul>
	</div>
	<!-- 分页结束=======================  -->
	
			<!--商品浏览记录:-->
       <div style="margin-left: 150px; margin-bottom: 10px ">
	       	<a href="${ pageContext.request.contextPath }/ProductServlet?method=clearHistory&cid=${param.cid}&currentPage=${pageBean.currentPage}" class="btn btn-primary btn-sm btn-primary active" role="button">
	       		点我清空浏览记录
	       	</a>
       	</div>
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

		<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
		<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden;">
			<ul style="list-style: none;">
			<c:choose>
				<c:when test="${empty cookie.historyLog}">
					<p>您还没有任何浏览记录</p> 
				</c:when>
				<c:otherwise>
					<c:forTokens items="${cookie.historyLog.value}" delims="-" var="im">
						<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${ pageContext.request.contextPath }/${fn:split(im,'###')[1]}" width="130px" height="130px" /></li>
					</c:forTokens>
				</c:otherwise>
			</c:choose>
			</ul>
		</div>
	</div>
	
<!-- 页脚栏 -->
	<%@ include file="footer.jsp" %>
	
</body>
</html>