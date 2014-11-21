<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
<style type="text/css">
body {
	font-family: helvetica, arial, san-serif
}

table {
	border-collapse: collapse;
	border-color: #999;
	margin-top: .5em
}

th {
	background-color: #bbb;
	color: #000
}

td,th {
	padding: .25em
}
</style>
</head>
<body>
<h2>后台管理</h2>
<h3>系统信息：</h3>
<hr></hr>
<table border="1" cellpadding="2" cellspacing="0">
	<tbody>
		<tr align="left">
			<th><a
				title="Connections to the database, both internal and external.">Client</a></th>
			<th><a
				href="http://www.mongodb.org/display/DOCS/Viewing+and+Terminating+Current+Operation">OpId</a></th>
			<th>Active</th>
			<th>LockType</th>
			<th>Waiting</th>
			<th>SecsRunning</th>
			<th>Op</th>
			<th><a
				href="http://www.mongodb.org/display/DOCS/Developer+FAQ#DeveloperFAQ-What%27sa%22namespace%22%3F">Namespace</a></th>
			<th>Query</th>
			<th>client</th>
			<th>msg</th>
			<th>progress</th>
		</tr>
		<tr>
			<td>initandlisten</td>
			<td>0</td>
			<td></td>
			<td>W</td>
			<td></td>
			<td></td>
			<td>2004</td>
			<td>mydb</td>
			<td>{ name: /^local.temp./ }</td>
			<td>0.0.0.0:0</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>snapshotthread</td>
			<td>0</td>
			<td></td>
			<td>0</td>
			<td></td>
			<td></td>
			<td>0</td>
			<td></td>
			<td></td>
			<td>(NONE)</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>websvr</td>
			<td>0</td>
			<td></td>
			<td>0</td>
			<td></td>
			<td></td>
			<td>0</td>
			<td></td>
			<td></td>
			<td>(NONE)</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>clientcursormon</td>
			<td>0</td>
			<td></td>
			<td>R</td>
			<td></td>
			<td></td>
			<td>0</td>
			<td></td>
			<td></td>
			<td>(NONE)</td>
			<td></td>
			<td></td>
		</tr>
	</tbody>
</table>

<h3>新闻管理：</h3>
<hr></hr>
<a href="javascript:void();">查看所有新闻</a>
<a href="javascript:void();">添加企业新闻</a>
<h3>简历管理：</h3>
<hr></hr>
<a href="resumes.jsp">查看所有简历（5）</a>
<h3>商务留言管理：</h3>
<hr></hr>
<a href="javascript:void();">查看所有商务留言（5）</a>
<h3>网站统计：</h3>
<hr></hr>
<a href="javascript:void();">查看日访问量</a>
</body>
</html>