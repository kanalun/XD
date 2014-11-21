<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名认证</title>
</head>
<body>
	<form action="NameAuthTestServlet" method="post">
		姓名：<input type="text" name="userName" value="申亚男"/><br>
		身份证号：<input type="text" name="certId" value="142627199210140236"/><br>
		商户号：<input type="text" name="merchantId" value="888110047220006"/><br>
		<input type="submit" value="提交"/>
		<!-- <input type="reset" value="重置"/> -->
	</form>
</body>
</html>