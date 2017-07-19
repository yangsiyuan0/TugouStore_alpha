<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
			function addCategory(){
				window.location.href = "${pageContext.request.contextPath}/AdminCategoryServlet?method=addCategory";
			}
		</script>
	</HEAD>
	<body>
	<table border="1px" class="table table-hover table-striped">
		<thead>
			<tr>
				<td colspan="4" style="text-align: center;">
					<strong>分类列表</strong>
				</td>	
			</tr>
			<tr>
				<td colspan="4">
					<!-- 多重删除、添加按钮 -->
					<input type="button" class="btn btn-danger btn-sm navbar-right" role="button" id="deleteAll" onclick="addCategory()" style="margin-right: 10px" value="添加">
				</td>	
			</tr>
			<tr>
				<td align="center" width="18%">
					序号
				</td>
				<td align="center" width="17%">
					分类名称
				</td>
				<td width="7%" align="center">
					编辑
				</td>
				<td width="7%" align="center">
					删除
				</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="c" items="${ list }" varStatus="status">
				<tr onmouseover="this.style.backgroundColor = 'white'"
					onmouseout="this.style.backgroundColor = '#F5FAFE';">
					<td style="CURSOR: hand; HEIGHT: 22px" align="center"
						width="18%">
						${ status.count }
					</td>
					<td style="CURSOR: hand; HEIGHT: 22px" align="center"
						width="17%">
						${ c.cname }
					</td>
					<td align="center" style="HEIGHT: 22px">
						<a href="${ pageContext.request.contextPath }/AdminCategoryServlet?method=editCategory&cid=${c.cid}">
							<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
						</a>
					</td>
			
					<td align="center" style="HEIGHT: 22px">
						<a href="${ pageContext.request.contextPath }/AdminCategoryServlet?method=deleteCategory&cid=${c.cid}">
							<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</body>
</HTML>

