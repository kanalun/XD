<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" type="text/css" rel="stylesheet">
<%
	String name = "邱聪聪";
%>
<title>查看所有简历</title>
</head>
<body>
<h3>所有简历</h3>
<hr></hr>
状态：
<label><input type="checkbox">未读</label>
<label><input type="checkbox">已读</label>
<label><input type="checkbox">删除</label>
<table border="1" cellpadding="2" cellspacing="0">
	<tbody>
		<tr align="left">
			<th><a
				title="Connections to the database, both internal and external.">编号</a></th>
			<th><a
				title="Connections to the database, both internal and external.">Name</a></th>
			<th>Phone</th>
			<th>Email</th>
			<th>Job Intention</th>
			<th>Date</th>
			<th>Status</th>
			<th>Memo</th>
			<th>操作</th>
		</tr>
		<tr>
			<td>1</td>
			<td>邱聪聪</td>
			<td>0527-2388108</td>
			<td><a href="mailto:kanalun@163.com">kailun@163.com</a></td>
			<td>软件工程师</td>
			<td>2011-04-01 12:30</td>
			<td>未读</td>
			<td>这里是求职者提交的一些备注</td>
			<td><a href="javascript:alert('read')">Read</a> | <a
				href="javascript:alert('read')">删除</a></td>
		</tr>
		<tr>
			<td>2</td>
			<td>雪梨</td>
			<td>13738248155</td>
			<td><a href="mailto:kanalun@163.com">kanalun@163.com</a></td>
			<td>软件工程师</td>
			<td>2011-04-01 12:30</td>
			<td>未读</td>
			<td>这里是求职者提交的一些备注</td>
			<td><a href="javascript:alert('read')">Read</a> | <a
				href="javascript:alert('read')">删除</a></td>
		</tr>
	</tbody>
</table>
</body>
</html>