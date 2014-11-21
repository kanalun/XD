<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>示例表单数据注入</title>
</head>
<body>
USERNAME = 	${class}
<form action="a/test!p.do" method="post">
	<input type="text" name="u.name" value="name">
	<input type="text" name="ss" value="s2">
	<input type="checkbox" name="ss" value="c1">
	<input type="checkbox" name="ss" value="c2">
	<input type="checkbox" name="ss" value="c1">
	<input type="checkbox" name="ss" value="c2">
	<input type="checkbox" name="ss" value="c1">
	<input type="checkbox" name="ss" value="c2">
	<input type="checkbox" name="ii" value="1">
	<input type="checkbox" name="ii" value="115">
	<input type="submit" value="提交">
</form>
</body>
</html>