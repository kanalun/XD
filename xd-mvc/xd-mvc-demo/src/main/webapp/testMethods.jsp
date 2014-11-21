<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>测试方法可见性</title>
</head>
<body>
<h2>测试Action中方法"可见性"</h2>
<h3>可使用的方法</h3>
<a href="md!method.do" target="_blank">什么也不声明,使用方法名作为Action的methodName</a><br/>
<a href="md!m.do" target="_blank"> 使用m作为Action的methodName</a><br/>
<a href="md!methodALL0.do" target="_blank">GET/POST方法都能访问</a><br/>

<h3>仅GET方法</h3>
<a href="md!m.do" target="_blank"> 使用m作为Action的methodName</a><br/>
<h4>methodGet-methodGet()@GET方法能访问,而POST不能访问</h4>
<form action="md!methodGet.do" method="get">
	<input type="submit" value="GET"/>GET方法能访问
</form>
<form action="md!methodGet.do" method="post">
	<input type="submit" value="Post"/>POST不能访问
</form>

<h3>仅POST方法</h3>
<h4>methodPost-methodPost()@GET方法不能访问,而POST能访问</h4>
<form action="md!methodPost.do" method="get">
	<input type="submit" value="GET"/>GET方法不能访问
</form>
<form action="md!methodPost.do" method="post">
	<input type="submit" value="Post"/>POST能访问
</form>

<h3>被禁止的方法</h3>
<a href="md!forbidden0.do" target="_blank">被禁止的方法0,使用默认的403错误页面</a><br/>
<a href="md!forbidden1.do" target="_blank">被禁止的方法1,自定义状态代码(1234)</a><br/>
<a href="md!forbidden2.do" target="_blank">被禁止的方法2,自定义代码,且设定错误消息</a><br/>
<a href="md!forbidden3.do" target="_blank">被禁止的方法3,自定义状态代码,设定错误消息,选择异常类型</a><br/>
<h3>"私有"/"默认"/"保护"的方法</h3>
<a href="md!privateMeatod.do" target="_blank">私有的方法.默认不能访问.系统不做任何处理</a><br/>
<a href="md!defaultMeatod.do" target="_blank">默认的方法,默认不能访问.系统不做任何处理</a><br/>
<a href="md!protectedMeatod.do" target="_blank">受保护的方法,默认不能访问.系统不做任何处理</a><br/>

<h3>"GETTER"/"SETTER"方法</h3>
<a href="md!getUserName.do" target="_blank">GETTER:getUserName.默认不能访问.系统不做任何处理</a><br/>
<a href="md!isGender.do" target="_blank">GETTER:isGender.默认不能访问.系统不做任何处理</a><br/>
<a href="md!setUserName.do" target="_blank">SETTER方法:setUserName,默认不能访问.系统不做任何处理</a><br/>
</body>
</html>