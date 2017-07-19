<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
</head>
<body>

 <div class="form-group">
    <label for="date" class="col-sm-2 control-label">出生日期</label>
   	<div class="input-append date form_datetime">
    <input size="16" type="text" value="" readonly>
    <span class="add-on"><i class="icon-th"></i></span>
</div>
</body>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii"
    });
</script> 
</html>