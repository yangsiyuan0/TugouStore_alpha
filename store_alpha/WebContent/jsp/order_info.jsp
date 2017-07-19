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
	<div class="container">
		<div class="row">

			<div style="margin:0 auto;margin-top:10px;width:950px;">
				<strong>订单详情</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th colspan="5">订单编号:${orderBean.oid}</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${orderBean.orderItems}" var="orderItem">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${orderItem.productBean.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${orderItem.productBean.pname}</a>
								</td>
								<td width="20%">
									￥${orderItem.productBean.shop_price}
								</td>
								<td width="10%">
									${orderItem.count}
								</td>
								<td width="15%">
									<span class="subtotal">￥${orderItem.subtotal}</span>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div style="text-align:right;margin-right:120px;">
				商品金额: <strong style="color:#ff6600;">￥${orderBean.total}元</strong>
			</div>

		</div>

		<div>
			<hr/>
			<form class="form-horizontal" style="margin-top:5px;margin-left:150px;" id="orderForm" method="post" action="${ pageContext.request.contextPath }/OrderServlet">
				<input type="hidden" name="method" value="payOrder"/>
				<input type="hidden" name="oid" value="${ orderBean.oid }"/>
				<div class="form-group" >
					<label for="username" class="col-sm-1 control-label">地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="address"    placeholder="请输入收货地址">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="inputPassword3" name="name" placeholder="请输收货人">
					</div>
				</div>
				<div class="form-group">
					<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="confirmpwd" name="telephone" placeholder="请输入联系方式">
					</div>
				</div>
			

			<hr/>

			<div style="margin-top:5px;margin-left:150px;">
				<strong>选择银行：</strong>
				<p>
					<br/>
					<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />工商银行
					<img src="${pageContext.request.contextPath}/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
					<img src="${pageContext.request.contextPath}/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
					<img src="${pageContext.request.contextPath}/bank_img/abc.bmp" align="middle" />
					<br/>
					<br/>
					<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
					<img src="${pageContext.request.contextPath}/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
					<img src="${pageContext.request.contextPath}/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行
					<img src="${pageContext.request.contextPath}/bank_img/ccb.bmp" align="middle" />
					<br/>
					<br/>
					<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
					<img src="${pageContext.request.contextPath}/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
					<img src="${pageContext.request.contextPath}/bank_img/cmb.bmp" align="middle" />

				</p>
				<hr/>
				<p style="text-align:right;margin-right:100px;">
					<a href="javascript:document.getElementById('orderForm').submit();">
						<img src="${pageContext.request.contextPath}/images/finalbutton.gif" width="204" height="51" border="0" />
					</a>
				</p>
				<hr/>
			</form>
			</div>
		</div>

	</div>

<!-- 页脚栏 -->
	<%@ include file="footer.jsp" %>
</body>
</html>