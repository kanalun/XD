<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
userName = <%=request.getParameter("userName") %>
<form action="a/test!list.do" method="post">
	ss[0]<input type="text" value="TEST" name="ss[0]">
	ss[1]<input type="text" value="TEST" name="ss[1]">
	ss[2]<input type="text" value="TEST" name="ss[2]">
	ss[3]<input type="text" value="TEST" name="ss[3]">
	<input type="submit" value="提交">
</form>
</body>
</html>