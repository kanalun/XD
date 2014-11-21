<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>示例返回结果:</title>
</head>
<body>
<h2>示例返回结果</h2>
<h3>index.jsp</h3>
<div class="desc">这是一小段描述描述<div>
<a href="rs!forward0.do">forward0</a>
<h3>index.jsp</h3>
<a href="rs!forward1.do">forward1</a>
<h3>index.jsp</h3>
<a href="rs!forward2.do">forward2</a>
<h3>使用协议</h3>
<div class="desc">forward://index.jsp<div>
<a href="rs!forward3.do">forward3</a>
<div class="desc">这是一小段描述描述<div>

<h3>Freemarker</h3>
<div class="desc">使用'ftl://D://freemarker/temp.txt'返回文件系统中的模板<div>
<a href="rs!ftl1.do">ftl://file://D://freemarker/temp.txt</a>
<div class="desc">这是一小段描述描述<div>
<a href="rs!ftl2.do">ftl://class://com/kan/temp/temp.txt</a>
<div class="desc">这是一小段描述描述<div>
<a href="rs!ftl3.do">ftl://class://temp.txt</a>
<div class="desc">这是一小段描述描述<div>
<a href="rs!ftl5.do">test.ftl</a>
<div class="desc">这是一小段描述描述<div>
</body>
</html>