<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- <%
	response.sendRedirect(request.getContextPath()+"/DemoServlet?method=test");
%> --%>
<jsp:forward page="/DemoServlet">
	<jsp:param value="test" name="method"/>
</jsp:forward>
</body>
</html>