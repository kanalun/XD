<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="WEB-INF/i18n.tld" prefix="i18n" %>
<%@page import="com.xindian.mvc.ActionContext" %>
<%@page import="com.xindian.mvc.test.TestConstants" %>
<%@page import="com.xindian.mvc.i18n.ConstantUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
</head>
<body>
<h2>你好:${user.name}</h2>
<a href="UserAction!logout.do">退出</a>
<h3>示例文件上传</h3>
<a href="fileUpload.jsp" target="_blank">示例文件上传</a>

<h3>示例返回结果</h3>
<a href="testResult.jsp" target="_blank">示例返回结果</a>

<h3>示例方法访问性</h3>
<a href="testMethods.jsp" target="_blank">示例方法访问性</a>
<%
	pageContext.setAttribute("qcc","888");
%>
<i18n:text key="age">
	<i18n:param value="qcc"></i18n:param>
	<i18n:param value="3333"></i18n:param>
</i18n:text>
<br/>
<jsp:include page="i18nTest.jsp"></jsp:include>

<i18n:setLocale value="jp_CC3" scope="page2"/>

<i18n:setLocale value="jp_AA" scope="request"/>

<jsp:useBean id="ad" class="com.xindian.mvc.test.Address"></jsp:useBean>

<jsp:setProperty property="address" name="ad"  param="dddd"/>
<%
	ad.getAddress();
%>
<%!//TestConstants testConstants = ConstantUtils.create(TestConstants.class);%>

	<%
	//testConstants.f();
	%>
<h3>示例AOP</h3>
<a href="TestAop!aop.do" target="_blank">示例AOP</a>
</body>
</html>