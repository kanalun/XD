<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错了</title>
</head>
<body>
	<%--
		<error-page>
			<!-- 返回或者发生TestException是定位到/testException.jsp页面 -->
			<exception-type>com.kan.mvc.test.TestException</exception-type>
			<location>/testException.jsp</location>
		</error-page>
	--%>
	<center>
	系统返回了一个TestException!,而这个异常被tomcat捕获处理了,请查看web.xml
	</center>
	<pre>
		  	&lt;error-page&gt;
	  			&lt;!-- 返回或者发生TestException是定位到/testException.jsp页面 --&gt;
	  			&lt;exception-type&gt;com.kan.mvc.test.TestException&lt;/exception-type&gt;
	  			&lt;location&gt;/testException.jsp&lt;/location&gt;
	  		&lt;/error-page&gt;
	</pre>
</body>
</html>