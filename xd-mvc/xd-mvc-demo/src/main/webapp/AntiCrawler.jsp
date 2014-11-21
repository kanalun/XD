<?xml version="1.0" encoding="UTF-8" ?>
<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="com.xindian.anticrawler.IpBlacklist"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
 添加IP进黑名单:
	<form action="AntiCrawlerAction!addIptoBlackList.do" method="post">
		<label>IP</label><input type="text" name="ip2BlackList"/>
		<input type="submit" />
	提示:	<%=request.getParameter("msg")%>
	<%=request.getAttribute("qcc")%>
	</form>
所有黑名单中的IP:
	<%
	IpBlacklist ipBlacklist = (IpBlacklist) application.getAttribute("balckList");
	Iterator ips = ipBlacklist.iterator();
	while(ips.hasNext())
	{
		%>
		<div><%=ips.next() %></div>
		<%
	}
	%>
</body>
</html>