<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript">
			function showDetail(oid){
				var $val = $("#but"+oid).val();
				if($val == "订单详情"){
					$.post("${pageContext.request.contextPath}/AdminOrderServlet",{"method":"showDetail","oid":oid},function(data){
						$(data).each(function(i,n){
							// 显示图片,名称,单价,数量
							$("#div"+oid).append("<hr/><img width='60' height='65' src='${pageContext.request.contextPath}/"+n.productBean.pimage+"'>&nbsp;"+n.productBean.pname+"&nbsp;"+n.productBean.shop_price+"<br/>");
						});
					},"json");
					
					$("#but"+oid).val("关闭");
				}else{
					$("#div"+oid).html("");
					$("#but"+oid).val("订单详情");
				}
			}
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</td>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
								<c:forEach var="o" items="${ list }" varStatus="status">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="12%">
												${ status.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="12%">
												${ o.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="12%">
												${ o.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="12%">
												${ o.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="12%">
												<c:if test="${ o.state==1 }">
													未付款
												</c:if>
												<c:if test="${ o.state==2 }">
													<a href="${ pageContext.request.contextPath }/AdminOrderServlet?method=send&oid=${o.oid}">发货</a>
												</c:if>
												<c:if test="${ o.state==3 }">
													已发货
												</c:if>
												<c:if test="${ o.state==4}">
													订单完成
												</c:if>
											</td>
											<td  style="HEIGHT: 22px" 
												width="40%">
												<input type="button" value="订单详情" id="but${o.oid}" onclick="showDetail('${o.oid}')"/>
												<div id="div${o.oid}">
												</div>
											</td>
							
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
</HTML>

