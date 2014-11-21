<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.xindian.mvc.i18n.SafeHtml"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>non-SafeHtml:<p>THIS IS <br/> NON-SAFE HTML</p></p>
	<p>SafeHtml:<%=new SafeHtml("<p>THIS IS <br/> SAFE HTML</p>")%></p>
</body>
</html>