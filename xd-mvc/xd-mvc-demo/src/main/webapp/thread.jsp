<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Threads in oschina</title>
<style>
body {font-size:12pt;}
ol {line-height:14px;}
</style>
</head>
<body>
<strong>java.io.tmpdir:</strong>
<ul>
<li><%=System.getProperty("java.io.tmpdir")%></li>
</ul>
<br/>
<strong>Memory:</strong>
<ol>
<li>freeMemory=<%=Runtime.getRuntime().freeMemory()/(1024*1024)%>M</li>
    <li>totalMemory=<%=Runtime.getRuntime().totalMemory()/(1024*1024)%>M</li>
    <li>maxMemory=<%=Runtime.getRuntime().maxMemory()/(1024*1024)%>M</li>
</ol>
<br/>
<strong>Thread:</strong>
<ol>
<%for(Thread t : list_threads()){%>
<li><%=t.getName()%>(<b><%=t.getState()%></b>) : <%=t.getClass().getName()%></li>
<%}%>
</ol>
<%!
public static java.util.List<Thread> list_threads(){
	int tc = Thread.activeCount();
	Thread[] ts = new Thread[tc];
	Thread.enumerate(ts);
	return java.util.Arrays.asList(ts);
}
%>
</body>
</html>