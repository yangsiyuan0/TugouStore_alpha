<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript">
			function addProduct(){
				window.location.href = "${pageContext.request.contextPath}/adminProduct_addPage.action";
			}
			/* 全选、全不选 */
			$(function(){
				$("#selectAll").click(function(){
					$("input[id='ids']").prop("checked",this.checked);
				});
			});
			
			/* 删除单个 */
			/* function delProduct(pid){
				var result = window.confirm("您确信要删除这条记录吗？");
				if(result == true){
					location.href = "${ pageContext.request.contextPath }/ProductDeleteServlet?pid="+pid;
				}
			} */
			
			/* 多重删除 */
			$(function(){
				$("#deleteAll").click(function(){
					$("#form").submit();
				})
			})
			/* 模糊查询 */
			$(function(){
				$("#search").click(function(){
					/* 获取文本框的值，并更改form提交的链接 */
					// var keyWord = $("#keyWord").val();
					$("#target").prop("value","search");
					var $cid = $("#cid1").val();
					$("#form").prop("action","${ pageContext.request.contextPath }/AdminProductServlet?cid="+$cid);
					$("#form").submit();
				})
			})
		</script>
	</HEAD>
	<body>
		<br>
<form action="${ pageContext.request.contextPath }/AdminProductServlet" method="post" id="form">
<input type="hidden" name="method" value="deleteAll" id="target">
	<table border="1px" class="table table-hover table-striped">
		<thead>
			<tr>
				<td colspan="9" style="text-align: center;">
					<strong>商品列表</strong>
				</td>	
			</tr>
			<tr>
				<td colspan="9">
				<!-- 商品分类展示按钮 -->
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="cid1">
					  <c:if test="${empty cname}"> 
					   	 全部 
				   	 </c:if> 
				   	  <c:if test="${not empty cname}"> 
					   	 ${cname}
				   	 </c:if>
					  <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu">
					  <c:if test="${not empty cname}">
						  <li>
						  <a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findPageByCid&currentPage=1" id="cid">全部</a>
						  </li>
					  </c:if>
					  <c:forEach items="${list}" var="category">
					    <li>
					    	<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findPageByCid&cid=${category.cid}&currentPage=1&cname=${category.cname}" id="cid" >${category.cname}</a>
				    	</li>
					  </c:forEach>
					  </ul>
					</div>
					
					<!-- 查询按钮 -->
					 <div class="col-lg-2">
					 	<c:if test="${param.keyword==null}">
					 		<input type="text" class="form-control" placeholder="查询：请输入关键字" id="keyword" name="keyword">
					 	</c:if>
					    <c:if test="${param.keyword!=null}">
					 		<input type="text" class="form-control" placeholder="${param.keyword}" id="keyword" name="keyword">
					 	</c:if>
					  </div>
					  <button type="button" class="btn btn-default" id="search">查询</button>
					  
					<!-- 多重删除、添加按钮 -->
					<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=addUI" class="btn btn-primary btn-sm navbar-right" role="button" style="margin-right: 30px">添加</a>
					<input type="button" class="btn btn-danger btn-sm navbar-right" role="button" id="deleteAll" style="margin-right: 10px" value="删除">
					<!-- <a href="#" class="btn btn-danger btn-sm navbar-right" role="button" id="deleteAll" style="margin-right: 10px">删除</a> -->
				</td>	
			</tr>
			<tr>
				<td><input type="checkbox" id="selectAll" onclick="chooseAll()"/></td>
				<td>序号</td>
				<td>商品图片</td>
				<td>商品名称</td>
				<td>市场价格</td>
				<td>商城价格</td>
				<td>是否热门</td>
				<td>编辑</td>
				<td>删除</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="p" items="${ pageBean.list }" varStatus="status">
				<tr>
					<td>
						<input type="checkbox" id="ids" name="ids" value="${ p.pid }"/></td>
					<td>
						${ status.count }
					</td>
					<td>
						<img src="${pageContext.request.contextPath}/${p.pimage}" width="40" height="45">
					</td>
					<td>
						${ p.pname }
					</td>
					<td>
						${ p.market_price }
					</td>
					<td>
						${ p.shop_price }
					</td>
					<td>
						<c:if test="${ p.is_hot == 1 }">
							是
						</c:if>
						<c:if test="${ p.is_hot == 0 }">
							否
						</c:if>
					</td>
					<td align="center" style="HEIGHT: 22px">
						<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=editUI&pid=${p.pid}">
							<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
						</a>
					</td>
					<td align="center" style="HEIGHT: 22px">
						<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=deleteProduct&pid=${p.pid}">
							<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
						</a>
					</td>
				</tr>
			</c:forEach>
			
			
			<!-- 自定义下面的页面选择信息 -->
			<tr>
				<td colspan="9" align="center">
					第${ pageBean.currentPage }/${ pageBean.totalPage }页&emsp;
					总记录数:${ pageBean.totalItem }&emsp;&emsp;本页记录数:${pageBean.pageSize} &emsp; 
					<c:if test="${ pageBean.currentPage != 1 }">
					<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findProductByPage&currentPage=1">[首页]</a>
					<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findProductByPage&currentPage=${ pageBean.currentPage - 1 }">[上一页]</a>
					</c:if>
					&emsp;
					
					<c:forEach var="i" begin="1" end="${ pageBean.totalPage }">
						<c:if test="${ pageBean.currentPage != i }">
							<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findProductByPage&currentPage=${i}">${ i }</a>
						</c:if>
						<c:if test="${ pageBean.currentPage == i }">
							${ i }
						</c:if>
					</c:forEach>
					
					&emsp;
					<c:if test="${ pageBean.currentPage != pageBean.totalPage }">
					<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findProductByPage&currentPage=${ pageBean.currentPage + 1}">[下一页]</a>
					<a href="${ pageContext.request.contextPath }/AdminProductServlet?method=findProductByPage&currentPage=${ pageBean.totalPage }">[尾页]</a>
					</c:if>
				</td>	
			</tr>
		</tbody>
	</table>
</form>
	</body>
</HTML>

