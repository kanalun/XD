<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户登录</title>
</head>
<body>
<h3>用户登录</h3>
<form action="UserAction!login.do" method="post">
	<label>Name:</label><input type="text" name="user.name"/><br/>
	<label>password:</label><input type="text" name="user.password"/><br/>
	<input  type="submit" value="Login"/>
	${errorMessage}
</form>
</body>
</html>