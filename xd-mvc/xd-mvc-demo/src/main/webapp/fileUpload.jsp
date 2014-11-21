<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文件上传</title>
</head>
<body>
描述:这个示例是这样工作的:你选择一个文件,然后上传,文件被Action捕获,直接返回你上传的文件!<br/>
<form action="up!uploadImage.do" enctype="multipart/form-data" method="post">
	选择一个图片文件:<input type="file" name="file"/><br/>
	文件名:<input type="text" value="fileName" name="fileName"/><br/>
	<input type="submit" value="上传文件"/>
</form>
</body>
</html>