<%@page import="com.xindian.p2p.entity.NameAuthTestServlet"%>
<%@page import="java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>INDEX</title>
<style>
body {
	font-family: "microsoft YaHei";
}
</style>
</head>

<body>
	<h2>Hello World!</h2>
	<%=new Date()%><br>
	<div>
		java.class.path:
		<%=System.getProperty("java.class.path")%>
	</div>
	<div>
		<%=NameAuthTestServlet.class.getClassLoader()%><br>
	</div>
</body>
</html>
