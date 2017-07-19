<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
	<title>购物车</title>
</head>


<body>
<!-- 导航栏 -->
	<%@ include file="navi.jsp" %>
	
	
<!-- 购物车主体 -->

	<c:choose>
		<c:when test="${ empty cart.map }">
		<div class="container">
				<div class="row" style=" height: 400px">
					<h4 style="text-align: center; margin-top:200px">您的购物车空空如也,&nbsp;<a href="${ pageContext.request.contextPath }/">前往购物<img alt="" src="${pageContext.request.contextPath}/img/cart.png;" width="50px"></a>...</h4>
					
			</div>
		</div>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
		</c:when>
		<c:otherwise>
			<div class="container" style=" height: 500px">
				<div class="row" >
					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
						
						<table class="table table-bordered">
							<tbody>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${cart.map}" var="cartItem">
									<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${ pageContext.request.contextPath }/${cartItem.value.productBean.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank"> ${cartItem.value.productBean.pname}</a>
									</td>
									<td width="20%">
										￥${cartItem.value.productBean.shop_price}
									</td>
									<td width="10%">
										<!-- <input type="text" name="quantity" value="1" maxlength="4" size="10"> -->${cartItem.value.itemCount}
									</td>
									<td width="15%">
										<span class="subtotal">￥${cartItem.value.subtotal}</span>
									</td>
									<td>
										<a href="javascript:;" class="delete" id="delete" pid="${cartItem.key}">删除</a>
									</td>
								</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
				</div>
		
				<div style="margin-right:130px;">
				
					<div style="text-align:right;">
						<c:if test="${ empty existUser }">
							<em style="color:#ff6600;">
								<a href="${ pageContext.request.contextPath }/jsp/login.jsp">登录</a>后确认是否享有优惠&nbsp;&nbsp;
							</em> 
						</c:if>	
						赠送积分: <em style="color:#ff6600;">${cart.total/10}</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.total}元</strong>
					</div>
				
					<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
						<a href="${ pageContext.request.contextPath }/CartServlet?method=clearCart" id="clear" class="clear">清空购物车</a>
						
						<c:if test="${ empty existUser }">
								<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
								height:35px;width:100px;color:white;" disabled="disabled">
							<span>
							&emsp;(请先<a href="${ pageContext.request.contextPath }/jsp/login.jsp" >登录)</a>
							</span>
						</c:if>
						<c:if test="${ not empty existUser }">
							<a href="${ pageContext.request.contextPath }/OrderServlet?method=createOrder">
								<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
								height:35px;width:100px;color:white;">
							</a>
						</c:if>
						
					</div>
				</div>
			</div>
		</c:otherwise>				
	</c:choose>



<!-- 页脚栏 -->
	<%@ include file="footer.jsp" %>
</body>
</html>